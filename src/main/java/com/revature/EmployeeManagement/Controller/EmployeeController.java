package com.revature.EmployeeManagement.Controller;

import com.revature.EmployeeManagement.EmployeeManagementApplication;
import com.revature.EmployeeManagement.Exception.InvalidCredential;
import com.revature.EmployeeManagement.Exception.UserNotFoundException;
import com.revature.EmployeeManagement.Model.Employee;
import com.revature.EmployeeManagement.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/revWorkforce")
public class EmployeeController {

    EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }

    @PostMapping("/register")
//    public Employee postEmployee(@RequestBody Employee employee){
//        return employeeService.createEmployee(employee);
//    }
    public ResponseEntity<String> postEmployee(@RequestBody Employee employee){
        try {
            employeeService.createEmployee(employee);
            return ResponseEntity.ok("Employee registered successfully");
        }catch (InvalidCredential e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Employee employee ){
        try {
            Employee employeeLogin = employeeService.login(employee.getEmail(), employee.getPassword());
            return ResponseEntity.ok(employeeLogin);
        } catch (UserNotFoundException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
    }





}
