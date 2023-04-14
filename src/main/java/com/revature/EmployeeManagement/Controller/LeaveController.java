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


//        @PatchMapping("/{leaveId")
//        public ResponseEntity<Leave>



    @ExceptionHandler(InvalidCredential.class)
    public ResponseEntity<String > handleResourceNotFoundExceptions(InvalidCredential e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The requested resource not Found");
    }




}
