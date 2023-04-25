package com.revature.EmployeeManagement.Controller;

import com.revature.EmployeeManagement.Exception.AdminNotFoundException;
import com.revature.EmployeeManagement.Exception.InvalidCredential;
import com.revature.EmployeeManagement.Exception.UserNotFoundException;
import com.revature.EmployeeManagement.Model.Admin;
import com.revature.EmployeeManagement.Model.Employee;
import com.revature.EmployeeManagement.Model.Leave;
import com.revature.EmployeeManagement.Service.AdminService;
import com.revature.EmployeeManagement.Service.EmployeeService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
@RequestMapping("/admin")
@CrossOrigin("*")
public class AdminController {
    private final AdminService adminService;
    private final EmployeeService employeeService;

    @Autowired
    public AdminController(AdminService adminService, EmployeeService employeeService) {
        this.adminService = adminService;
        this.employeeService = employeeService;
    }

    /**
     * Create admin
     * ENDPOINT POST localhost:9000/admin/createAdmin
     * @param admin
     * @return
     */

    @PostMapping("/createAdmin")
    public ResponseEntity<Admin> createAdmin(@RequestBody Admin admin) {
        Admin newAdmin = adminService.createAdmin(admin);
        return ResponseEntity.status(HttpStatus.CREATED).body(newAdmin);
    }

    /**
     * Admin login
     * ENDPOINT localhost:9000/admin/login
     * @param admin
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<?> adminLogin(@RequestBody Admin admin){
        try {
            Admin adminLogin = adminService.adminLogin(admin.getUsername(), admin.getPassword());
            return ResponseEntity.ok(adminLogin);
        }
        catch (InvalidCredential e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
    }

    /**
     * Admin Password Reset
     * ENDPOINT localhost:9000/admin/password-reset
     * @param admin
     * @return
     */
    @PostMapping("/password-reset")
    public ResponseEntity<String> adminPasswordReset(@RequestBody Admin admin){
        try {
            adminService.adminPasswordReset(admin);
            return ResponseEntity.ok("Temporary password sent");
        } catch (UserNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
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

    @ExceptionHandler(InvalidCredential.class)
    public ResponseEntity<String > handleResourceNotFoundExceptions(InvalidCredential e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The requested resource not Found");
    }

}


