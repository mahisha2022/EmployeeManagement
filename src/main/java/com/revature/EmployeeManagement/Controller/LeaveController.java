package com.revature.EmployeeManagement.Controller;

import com.revature.EmployeeManagement.Exception.InvalidCredential;
import com.revature.EmployeeManagement.Model.Employee;
import com.revature.EmployeeManagement.Model.Leave;
import com.revature.EmployeeManagement.Service.EmployeeService;
import com.revature.EmployeeManagement.Service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public LeaveController(LeaveService leaveService, EmployeeService employeeService) {
        this.leaveService = leaveService;
        this.employeeService = employeeService;
    }

    /**
     * Endpoint for  POST localhost:9000/leaves/request/1 adds a new leave request for an existed employee with id 1
     * {
     *       "startDate": "2023-12-13",
     *     "endDate": "2023-12-14",
     *     "leaveType": "annual",
     *      "notes": "n/a"
     * }
     * should add a new request for employeeid 1 and response with
     * {
     *     "id": 1,
     *     "startDate": "2023-12-13",
     *     "endDate": "2023-12-14",
     *     "leaveType": "annual",
     *     "status": "Submitted",
     *     "notes": "n/a",
     *     "employeeId": 1
     * }
     */

    @PostMapping("/request/{employeeId}")
    public Leave leaveRequest(@RequestBody Leave leave, @PathVariable long employeeId) {
        return leaveService.requestLeave(leave, employeeId);
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

        @DeleteMapping("/{leaveId}")
    public ResponseEntity<String> cancelLeave(@PathVariable long leaveId){
        try {
            leaveService.cancelLeave(leaveId);
            return ResponseEntity.ok("Leave with id " + leaveId + " has been cancelled");
        }catch (InvalidCredential e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }


        }


}
