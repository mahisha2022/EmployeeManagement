package com.revature.EmployeeManagement.Controller;

import com.revature.EmployeeManagement.Exception.AdminNotFoundException;
import com.revature.EmployeeManagement.Exception.InvalidCredential;
import com.revature.EmployeeManagement.Model.Admin;
import com.revature.EmployeeManagement.Model.Employee;
import com.revature.EmployeeManagement.Model.Leave;
import com.revature.EmployeeManagement.Service.AdminService;
import com.revature.EmployeeManagement.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;
    private final EmployeeService employeeService;

    @Autowired
    public AdminController(AdminService adminService, EmployeeService employeeService) {
        this.adminService = adminService;
        this.employeeService = employeeService;
    }

    @PostMapping("/createAdmin")
    public ResponseEntity<Admin> createAdmin(@RequestBody Admin admin) {
        Admin newAdmin = adminService.createAdmin(admin);
        return ResponseEntity.status(HttpStatus.CREATED).body(newAdmin);
    }

    @GetMapping("/{username}")
    public ResponseEntity<Admin> getAdminByUsername(@PathVariable String username) {
        try {
            Admin admin = adminService.getAdminByUsername(username);
            return ResponseEntity.ok(admin);
        } catch (AdminNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/createProfile")
    public ResponseEntity<String> createProfile(@RequestBody Employee employee) {
        try {
            employeeService.createProfile(employee);
            return ResponseEntity.ok(employee.getIsManager() == 1 ? "Manager profile created successfully" : "Employee profile created successfully");
        } catch (InvalidCredential e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/leaves")
    public ResponseEntity<List<Leave>> getAllLeaves() {
        List<Leave> leaves = employeeService.getAllLeaves();
        return ResponseEntity.ok(leaves);
    }


    @DeleteMapping("/delete/employee/{employeeId}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable long employeeId) {
        employeeService.deleteEmployeeById(employeeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}


