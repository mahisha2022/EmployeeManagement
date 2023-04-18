package com.revature.EmployeeManagement.Controller;

import com.revature.EmployeeManagement.Exception.InvalidCredential;
import com.revature.EmployeeManagement.Model.Employee;
import com.revature.EmployeeManagement.Model.Leave;
import com.revature.EmployeeManagement.Model.Notification;
import com.revature.EmployeeManagement.Service.LeaveService;
import com.revature.EmployeeManagement.Service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private LeaveService leaveService;

    @Autowired
    private NotificationService notificationService;


    @GetMapping("")
    public List<Notification> getAllNotification(){
        return notificationService.getAllNotifications();
    }



    @GetMapping("/manager/{managerId}")
    public List<Notification> getNotificationByManager(@PathVariable Long managerId){
        return notificationService.getNotificationByManager(managerId);
    }

    @GetMapping("/employee/{employeeId}")
    public List<Notification> getNotificationByEmployee(@PathVariable Long employeeId){
        return notificationService.getNotificationByEmployee(employeeId);
    }

    @ExceptionHandler(InvalidCredential.class)
    public ResponseEntity<String > handleResourceNotFoundExceptions(InvalidCredential e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The requested resource not Found");
    }
}
