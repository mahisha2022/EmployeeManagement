package com.revature.EmployeeManagement.Service;

import com.revature.EmployeeManagement.Exception.InvalidCredential;
import com.revature.EmployeeManagement.Model.Employee;
import com.revature.EmployeeManagement.Model.Leave;
import com.revature.EmployeeManagement.Model.Notification;
import com.revature.EmployeeManagement.Repositoty.EmployeeRepository;
import com.revature.EmployeeManagement.Repositoty.LeaveRepository;
import com.revature.EmployeeManagement.Repositoty.NotificationRepository;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.spec.ECPoint;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class LeaveService {

    private LeaveRepository leaveRepository;
    private EmployeeRepository employeeRepository;
    private NotificationService notificationService;
    private NotificationRepository notificationRepository;
    @Autowired
    public LeaveService(LeaveRepository leaveRepository, EmployeeRepository employeeRepository,
                        NotificationService notificationService, NotificationRepository notificationRepository){
        this.employeeRepository = employeeRepository;
        this.leaveRepository = leaveRepository;
        this.notificationService = notificationService;
        this.notificationRepository = notificationRepository;
    }


    /**
     * return all leaves
     * @return
     */
    public List<Leave> getAllLeaves(){
        return leaveRepository.findAll();
    }

    /**
     * get all leaves by Employee id
     * @param employee
     * @return
     */

    public List<Leave> getLeavesByEmployeeId(Employee employee){
        return leaveRepository.findByEmployee(employee);
    }

    /**
     * request leave by employee id
     * @param leave
     * @param employeeId
     * @return
     */

    public Leave requestLeave(Leave leave, long employeeId) {
        // get the employee
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new InvalidCredential("Employee Not found"));

        //create a variable to check if the start and end date is valid
        LocalDate today = LocalDate.now();
        LocalDate startDate = leave.getStartDate();
        LocalDate endDate = leave.getEndDate();
        if(startDate.isBefore(today)){
            throw new InvalidCredential("Start date cannot be before today!");
        }
        if(endDate.isBefore(startDate)){
            throw new InvalidCredential("Leave end date cannot be before leave start date!");
        }


       List<Leave> requestedLeaves = leaveRepository.findLeavesByEmployeeIdAndStartRange(employeeId, startDate, endDate);
        if(!requestedLeaves.isEmpty()){
            throw new InvalidCredential("You already requested a leave between these date range. ");
        }
        leave.setEmployee(employee);
        leave.setFirstname(employee.getFirstName());
        leave.setLastname(employee.getLastName());
        leave.setStatus("Submitted");
        Leave newLeave = leaveRepository.save(leave);
        newLeave.setEmployeeId(employeeId);


        //Notify Manager here

        Employee employee1 = newLeave.getEmployee();
        Long managerId = leave.getEmployee().getManagerId();
        Notification employeeNotification = new Notification();
        employeeNotification.setEmployee(employee);
        employeeNotification.setManagerId(managerId);
        employeeNotification.setMessage(employee1.getFirstName() + " "+ employee1.getLastName() +" have requested a leave from " + leave.getStartDate() +
                " to " + leave.getEndDate() + ". Please review the request!");
        notificationRepository.save(employeeNotification);


        return newLeave;


    }

    /**
     * Cancel/delete requested leave
     * @param id
     */


    public void cancelLeave(long id){
        Optional<Leave> leaveOptional = leaveRepository.findById(id);
        Leave leave = leaveOptional.get();
        if(leave.getStatus().equals("Submitted")){

            //Create a notification to send notification to employee and manager
            Employee employee = leave.getEmployee();
            Notification employeeNotification = new Notification();
            employeeNotification.setEmployee(employee);
            employeeNotification.setMessage("Your leave request from start date: " + leave.getStartDate()  + " to " + leave.getEndDate() + " is successfully canceled!");
            notificationRepository.saveAll(Arrays.asList(employeeNotification));
            leaveRepository.delete(leave);
        }else {
            throw new InvalidCredential("Leave cannot be deleted, because it's approved or rejected");
        }

    }

    /**
     * update requested leave only if its not approved or rejected
     * @param id
     * @param updatedLeave
     * @return
     */

    public Leave updateLeave(Long id, Leave updatedLeave){
        Leave leave = leaveRepository.findById(id).get();
       if(leave.getStatus() == "Submitted"){
           leave.setLeaveType(updatedLeave.getLeaveType());
           leave.setStartDate(updatedLeave.getStartDate());
           leave.setEndDate(updatedLeave.getEndDate());

           leaveRepository.save(leave);
       }else {
           throw new InvalidCredential("Leave cannot be updated. Because its already approved or Rejected");
       }
       return leave;
    }

    /**
     * Approve submitted leave request
     * @param id
     * @return submitted leave
     *
     */


    public Leave approveLeave(long id){
        Leave submittedLeave = leaveRepository.findById(id).get();
        if(submittedLeave.getStatus().equals("Submitted")){
            submittedLeave.setStatus("Approved");
            leaveRepository.save(submittedLeave);

            //Sent notification to employee

            Employee employee = submittedLeave.getEmployee();
            Notification employeeNotification = new Notification();
            employeeNotification.setEmployee(employee);
            employeeNotification.setMessage("Congratulations! your leave request from : " + submittedLeave.getStartDate()  + " to " + submittedLeave.getEndDate() + " is Approved!");
            notificationRepository.save(employeeNotification);
        }else {
            throw new InvalidCredential("It has been approved or rejected");
        }
        return submittedLeave;
    }

    /**
     * Reject submitted leave request
     * @param id, note
     * @return
     */

    public Leave rejectLeave(long id, String note){
        Leave submittedLeave = leaveRepository.findById(id).get();
        if(submittedLeave.getStatus().equals("Submitted")){
            submittedLeave.setStatus("Rejected");
            submittedLeave.setNotes(note);
            leaveRepository.save(submittedLeave);


            //Send Notification to employee

            Employee employee = submittedLeave.getEmployee();
            Notification employeeNotification = new Notification();
            employeeNotification.setEmployee(employee);
            employeeNotification.setMessage("Your leave request from : " + submittedLeave.getStartDate()  + " to " + submittedLeave.getEndDate() + " is not Approved");
            notificationRepository.save(employeeNotification);


        }
        return submittedLeave;
    }

    /**
     * get List of leaves by managerId
     * @param managerId
     * @return
     */

    public List<Leave> getLeaveRequestByManager(Long managerId){
        return leaveRepository.findByEmployee_ManagerIdOrderByStartDate(managerId);
    }

    /**
     * get employee's availability who report to a manager
     * @param managerId
     * @param leave
     * @return
     */

    public List<Employee> getEmployeeAvailability(Long managerId, Leave leave){
        List<Employee> availableEmployee = new ArrayList<>();

        //find all employees who report to the manager
        List<Employee> employees = employeeRepository.findByManagerId(managerId);

        //Iterate through employee and check their availability
        for (Employee e: employees){
            boolean isAvailable = true;
            //
            List<Leave> leaves = leaveRepository.findLeavesByEmployeeIdAndStartRange(e.getId(), leave.getStartDate(), leave.getEndDate());

            //check if the employee has any leaves during the given date range
            for (Leave l: leaves){
                if (!leave.getStatus().equalsIgnoreCase("Approved")){
                    isAvailable = false;
                    break;
                }
            }
            if (isAvailable){
                availableEmployee.add(e);
            }
        }
        return availableEmployee;
    }


}
