package com.revature.EmployeeManagement.Controller;

import com.revature.EmployeeManagement.Model.Employee;
import com.revature.EmployeeManagement.Model.Leave;
import com.revature.EmployeeManagement.Model.Notification;
import com.revature.EmployeeManagement.Service.LeaveService;
import com.revature.EmployeeManagement.Service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private LeaveService leaveService;

    @Autowired
    private NotificationService notificationService;

//    @GetMapping("/request/{id}")
//    public Notification getInventoryById(@PathVariable("id") long id) {
//        Leave leave = leaveService.getLeaveById(id);
//        Employee employee = leave.getEmployee();
//        return notificationService.leaveRequestNotification(employee, leave);
//    }
}
