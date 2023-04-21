package com.revature.EmployeeManagement.Service;

import com.revature.EmployeeManagement.Exception.InvalidCredential;
import com.revature.EmployeeManagement.Model.Employee;
import com.revature.EmployeeManagement.Model.Meeting;
import com.revature.EmployeeManagement.Repositoty.EmployeeRepository;
import com.revature.EmployeeManagement.Repositoty.MeetingRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class MeetingService {

    @Autowired
    private MeetingRepository meetingRepository;
    @Autowired
    private EmployeeRepository employeeRepository;



    /**
     * Request Meeting with employee
     * @param employeeId
     * @param meeting
     * @return
     */

    public Meeting requestMeeting(long employeeId, Meeting meeting) {
        Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);
        Employee employee = employeeOptional.get();
        if (employee == null) {
            throw new InvalidCredential("Employee not found");
        }

        meeting.setEmployee(employee);
        return meetingRepository.save(meeting);

    }

    /**
     * Find list of meeting by employee
     * @param employee
     * @return
     */
    public List<Meeting> getMeetingByEmployee(Employee employee) {

        return meetingRepository.findByEmployee(employee);
    }

    /**
     * get list of meeting to and by a manager
     * @param managerId
     * @return
     */

    public List<Meeting> getMeetingByManager(long managerId){
        //find the manager
       Optional<Employee> managerOptional = employeeRepository.findById(managerId);
       Employee manager = managerOptional.orElseThrow(() -> new InvalidCredential("Manager Not Found"));
        //find the list of employees reported to the manager
       List<Employee> employees = employeeRepository.findByManagerId(managerId);
       //add the manager itself to the list of employees
       employees.add(manager);

       List<Meeting> meetings = new ArrayList<>();
       for (Employee e: employees){
           meetings.addAll(meetingRepository.findByEmployee(e));
       }
       return meetings;



    }

    /**
     * find all meetings
     * @return
     */

    public List<Meeting> getAllMeeting(){
        return meetingRepository.findAll();
    }

    /**
     * Update meeting
     * @param meetingId
     * @param updatedMeeting
     * @return
     */

    public Meeting updateMeeting(Long meetingId, Meeting updatedMeeting){
        Optional<Meeting> meetingOptional = meetingRepository.findById(meetingId);
        Meeting meeting = meetingOptional.get();
        if (meeting == null){
            throw new InvalidCredential("Meeting not found");
        }
        LocalDate today = LocalDate.now();
        LocalDate startDate = meeting.getStartTime();
        if (meeting.getStartTime().isBefore(today)){
            throw new InvalidCredential("Meeting date and time already passed!");
        }

        meeting.setSubject(updatedMeeting.getSubject());
        meeting.setDescription(updatedMeeting.getDescription());
        meeting.setStartTime(updatedMeeting.getStartTime());
        meeting.setEndTime(updatedMeeting.getEndTime());
        meetingRepository.save(meeting);
        return meeting;

    }

    /**
     * Delete Meeting
     * @param meetingId
     * @return
     */

    public void cancelMeeting(long meetingId){
       Meeting meeting = meetingRepository.findById(meetingId)
               .orElseThrow(() -> new InvalidCredential("Meeting not found"));
        meetingRepository.delete(meeting);
    }



}
