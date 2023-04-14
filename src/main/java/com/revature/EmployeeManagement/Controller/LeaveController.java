package com.revature.EmployeeManagement.Controller;

import com.revature.EmployeeManagement.Exception.InvalidCredential;
import com.revature.EmployeeManagement.Model.Employee;
import com.revature.EmployeeManagement.Model.Leave;
import com.revature.EmployeeManagement.Service.LeaveService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/leaves")

public class LeaveController {

    LeaveService leaveService;

    public LeaveController(LeaveService leaveService) {
        this.leaveService = leaveService;
    }

    @PostMapping("/request/{employeeId}")
    public ResponseEntity<Leave> leaveRequest(@RequestBody Leave leave,  @PathVariable long employeeId) {
//        try {
//            Employee employee = new Employee();
//            employee.setId(employeeId);
//            Leave requestedLeave = leaveService.requestLeave(leave, employee);
//            return ResponseEntity.ok("Leave request submitted!");
//        } catch (InvalidCredential e) {
//            return ResponseEntity.badRequest().body("Employee not found!");
//        }
        Leave savedLeave = leaveService.requestLeave(leave, employeeId);
        return new ResponseEntity<>(savedLeave, HttpStatus.CREATED);
    }
}
