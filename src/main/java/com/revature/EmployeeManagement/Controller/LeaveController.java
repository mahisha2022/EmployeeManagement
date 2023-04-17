package com.revature.EmployeeManagement.Controller;

import com.revature.EmployeeManagement.Exception.InvalidCredential;
import com.revature.EmployeeManagement.Model.Employee;
import com.revature.EmployeeManagement.Model.Leave;
import com.revature.EmployeeManagement.Service.EmployeeService;
import com.revature.EmployeeManagement.Service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/leaves")

public class LeaveController {

    LeaveService leaveService;
 
    EmployeeService employeeService;
@Autowired
  
    public LeaveController(LeaveService leaveService, EmployeeService employeeService) {
    
        this.leaveService = leaveService;
        this.employeeService = employeeService;
    }

    @PostMapping("/request/{employeeId}")
    public ResponseEntity<String> leaveRequest(@RequestBody Leave leave,  @PathVariable long employeeId) {
//
//        Leave savedLeave = leaveService.requestLeave(leave, employeeId);
//        return new ResponseEntity<>(savedLeave, HttpStatus.CREATED);

    try {
        leaveService.requestLeave(leave, employeeId);
        return ResponseEntity.ok("Leave Request Successfully Submitted!");
    }
        catch (InvalidCredential e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }
    }


    /**
     * Endpoint for GET localhost/leaves/1 returns the list of leaves for employee with id 1
     *
     * [
     *     {
     *         "id": 1,
     *         "startDate": "2023-12-13",
     *         "endDate": "2023-12-14",
     *         "leaveType": "annual",
     *         "status": "Submitted",
     *         "notes": "n/a",
     *         "employeeId": 1
     *     }
     * ]
     */

    @GetMapping("/{employeeId}")
    public ResponseEntity<List<Leave>> getLeaveByEmployee(@PathVariable long employeeId) {
        Optional<Employee> employeeOptional = employeeService.getEmployeeById(employeeId);
        if (employeeOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        List<Leave> leaves = leaveService.getLeavesByEmployeeId(employeeOptional.get());
        if(leaves.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        return ResponseEntity.ok().body(leaves);

    }

    /**
     * cancel leave by id
     * Endpoint DELETE localhost:9000/leaves/{leaveId}
     * @param leaveId
     * @return
     */

        @DeleteMapping("/{leaveId}")
    public ResponseEntity<String> cancelLeave(@PathVariable long leaveId){
        try {
            leaveService.cancelLeave(leaveId);
            return ResponseEntity.ok("Leave with id " + leaveId + " has been cancelled");
        }catch (InvalidCredential e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }


        }

    /**
     * get Leave by Manager
     * Endpoint GET localhost:9000/leaves/manager/{managerId}
     * @param managerId
     * @return
     */

    @GetMapping("/manager/{managerId}")
    public ResponseEntity<List<Leave>> getLeaveByManager(@PathVariable Long managerId){
        List<Leave> leaves = leaveService.getLeaveRequestByManager(managerId);
        if(leaves.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(leaves, HttpStatus.OK);
    }

    /**
     *
     * @param id
     * @return approved leave
     * Endpoint POST localhost:9000/leaves/approve/{id}
     */

    @PostMapping("/approve/{id}")
    public ResponseEntity<String> approveLeave(@PathVariable long id){
        try {
            leaveService.approveLeave(id);
            return ResponseEntity.ok().body("Leave Successfully Approved");
        }
        catch (InvalidCredential e){
            return ResponseEntity.badRequest().body("Leave already approved/rejected or not existed");
        }
    }

    @PostMapping("/cancel/{id}")
    public ResponseEntity<String> rejectLeave(@PathVariable long id, @RequestBody String notes) {
        try {
            leaveService.rejectLeave(id, notes);
            return ResponseEntity.ok().body("Leave Successfully rejected!");
        } catch (InvalidCredential e) {
            return ResponseEntity.badRequest().body("Leave already approved/rejected or not existed");

        }
    }






    @ExceptionHandler(InvalidCredential.class)
    public ResponseEntity<String > handleResourceNotFoundExceptions(InvalidCredential e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The requested resource not Found");
    }




}
