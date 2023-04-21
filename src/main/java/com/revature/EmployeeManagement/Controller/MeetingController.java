package com.revature.EmployeeManagement.Controller;

import com.revature.EmployeeManagement.Exception.InvalidCredential;
import com.revature.EmployeeManagement.Model.Employee;
import com.revature.EmployeeManagement.Model.Leave;
import com.revature.EmployeeManagement.Model.Meeting;
import com.revature.EmployeeManagement.Service.EmailSenderService;
import com.revature.EmployeeManagement.Service.EmployeeService;
import com.revature.EmployeeManagement.Service.MeetingService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/meetings")
@AllArgsConstructor
@NoArgsConstructor

public class MeetingController {

    @Autowired
    private MeetingService meetingService;
    @Autowired
    private EmployeeService employeeService;



    /**
     * ENDPOINT POST localhost:9000/meetings/employee/{employeeId}
     * @param meeting
     * @param employeeId
     * @return
     */

    @PostMapping("/employee/{employeeId}")
    public ResponseEntity<String> requestMeeting(@RequestBody Meeting meeting, @PathVariable long employeeId){
        try {
            meetingService.requestMeeting(employeeId, meeting);
            return ResponseEntity.ok("Meeting requested successfully");
        }
        catch (InvalidCredential e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * ENDPOINT PATCH localhost:9000/meetings/{meetingId}
     * @param meetingId
     * @param meeting
     * @return
     */

    @PatchMapping("/{meetingId}")
    public ResponseEntity<String> updateMeeting(@PathVariable long meetingId, @RequestBody Meeting meeting){
        try {
            meetingService.updateMeeting(meetingId, meeting);
            return ResponseEntity.ok("Meeting updated successfully");
        }
        catch (InvalidCredential e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/meetingId")
    public ResponseEntity<String> deleteMeeting(@PathVariable long meetingId){
        try {
            meetingService.cancelMeeting(meetingId);
            return ResponseEntity.ok("Meeting deleted successfully");
        }
        catch (InvalidCredential e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<Meeting>> getMeetingByEmployee(@PathVariable long employeeId){
        Optional<Employee> employeeOptional = employeeService.getEmployeeById(employeeId);
        if(employeeOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
        List<Meeting> meetings = meetingService.getMeetingByEmployee(employeeOptional.get());
        if (meetings.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            return ResponseEntity.ok().body(meetings);


}

    /**
     * Get meeting by manager
     * @param managerId
     * @return
     */
    @GetMapping("/manager/{managerId}")
    public List<Meeting> getMeetingByManager(@PathVariable Long managerId){
        return meetingService.getMeetingByManager(managerId);
    }

    /**
     * Get all Meetings
     * @return
     */

    @GetMapping("")
    public List<Meeting> getAllMeetings(){
        return meetingService.getAllMeeting();
    }


    @ExceptionHandler(InvalidCredential.class)
    public ResponseEntity<String > handleResourceNotFoundExceptions(InvalidCredential e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The requested resource not Found");
    }
}
