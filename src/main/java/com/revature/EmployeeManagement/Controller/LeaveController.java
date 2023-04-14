package com.revature.EmployeeManagement.Controller;

import com.revature.EmployeeManagement.Exception.InvalidCredential;
import com.revature.EmployeeManagement.Model.Employee;
import com.revature.EmployeeManagement.Model.Leave;
import com.revature.EmployeeManagement.Service.EmployeeService;
import com.revature.EmployeeManagement.Service.LeaveService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/leaves")

public class LeaveController {

    LeaveService leaveService;
    EmployeeService employeeService;


    EmployeeService employeeService;

    public LeaveController(LeaveService leaveService) {
    
    public LeaveController(LeaveService leaveService, EmployeeService employeeService) {
    
        this.leaveService = leaveService;
        this.employeeService = employeeService;
    }

    @PostMapping("/request/{employeeId}")
    public ResponseEntity<Leave> leaveRequest(@RequestBody Leave leave,  @PathVariable long employeeId) {

        Leave savedLeave = leaveService.requestLeave(leave, employeeId);
        return new ResponseEntity<>(savedLeave, HttpStatus.CREATED);
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




    @ExceptionHandler(InvalidCredential.class)
    public ResponseEntity<String > handleResourceNotFoundExceptions(InvalidCredential e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The requested resource not Found");
    }




}
