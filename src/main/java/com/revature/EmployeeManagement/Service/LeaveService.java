package com.revature.EmployeeManagement.Service;

import com.revature.EmployeeManagement.Exception.InvalidCredential;
import com.revature.EmployeeManagement.Model.Employee;
import com.revature.EmployeeManagement.Model.Leave;
import com.revature.EmployeeManagement.Repositoty.EmployeeRepository;
import com.revature.EmployeeManagement.Repositoty.LeaveRepository;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LeaveService {

    private LeaveRepository leaveRepository;
    private EmployeeRepository employeeRepository;
    private NotificationService notificationService;
    @Autowired
    public LeaveService(LeaveRepository leaveRepository, EmployeeRepository employeeRepository,
                        NotificationService notificationService){
        this.employeeRepository = employeeRepository;
        this.leaveRepository = leaveRepository;
        this.notificationService = notificationService;
    }


    /**
     * return all leaves
     * @return
     */
    public List<Leave> getAllLeaves(){
        return leaveRepository.findAll();
    }

    public List<Leave> getLeavesByEmployeeId(Employee employee){
        return leaveRepository.findByEmployee(employee);
    }

    public Leave requestLeave(Leave leave, long employeeId){
        //Find employee making the leave request
        Optional<Employee> employee1 = employeeRepository.findById(employeeId);
            if(employee1 == null){
                throw new InvalidCredential("Employee not Found");
            }
            //Create new leave request
            Leave newLeave = new Leave();
//            newLeave.setEmployee(employeeId);
            newLeave.setStartDate(leave.getStartDate());
            newLeave.setEndDate(leave.getEndDate());
            newLeave.setStatus("Submitted");
            newLeave.setNotes(leave.getNotes());
            newLeave.setLeaveType(leave.getLeaveType());


            //Notify the manager about the employees requested leave
//       Optional<Employee> manager = employeeRepository.findManagerByEmployee(employee.getId());
//        if (manager != null){
//            notificationService.leaveRequestNotification(employee, employee , leave);
//        }
    return leave;
        }

        //set the leave status default value pending
//        leave.setStatus("Submitted");
//       return leaveRepository.save(leave);


    public Leave cancelLeave(Long id){
        Leave leave = leaveRepository.findById(id).get();
        if(leave.getStatus() == "Submitted"){
            leaveRepository.delete(leave);
        } else {
            throw new InvalidCredential("Leave cannot be cancelled, because it already approved or rejected");
        }
        return leave;
    }

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

    public Leave approveLeave(Leave leave){
        Leave submittedLeave = leaveRepository.findById(leave.getId()).get();
        if(leave.getStatus().equals("Submitted")){
            leave.setStatus("Approved");
            leaveRepository.save(leave);
        }else {
            throw new InvalidCredential("It has been approved or rejected");
        }
        return leave;
    }

    public Leave rejectLeave(Leave leave){
        Leave submittedLeave = leaveRepository.findById(leave.getId()).get();
        if(submittedLeave.getStatus().equals("Submitted")){
            submittedLeave.setStatus("Rejected");
            leaveRepository.save(leave);
        }
        return leave;
    }


}
