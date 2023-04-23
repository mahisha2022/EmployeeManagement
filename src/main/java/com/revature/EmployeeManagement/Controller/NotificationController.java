package com.revature.EmployeeManagement.Controller;

import com.revature.EmployeeManagement.Exception.InvalidCredential;
import com.revature.EmployeeManagement.Model.Employee;
import com.revature.EmployeeManagement.Model.Leave;
import com.revature.EmployeeManagement.Model.Notification;
import com.revature.EmployeeManagement.Repositoty.EmployeeRepository;
import com.revature.EmployeeManagement.Service.LeaveService;
import com.revature.EmployeeManagement.Service.NotificationService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/notifications")
@AllArgsConstructor
@NoArgsConstructor
public class NotificationController {

    @Autowired
    private LeaveService leaveService;

    @Autowired
    private NotificationService notificationService;
    @Autowired
    private EmployeeRepository employeeRepository;


    /**
     * Get list of all Notifications
     * ENDPOINT GET localhost:9000/notifications
     * @return
     */
    @GetMapping("")
    public List<Notification> getAllNotification(){
        return notificationService.getAllNotifications();
    }


    /**
     * Get list of notification by manager
     * ENDPOINT GET localhost:9000/notifications/manager/{managerId}
     * @param managerId
     * @return
     */
    @GetMapping("/manager/{managerId}")
    public List<Notification> getNotificationByManager(@PathVariable Long managerId){
        return notificationService.getNotificationByManager(managerId);
    }

    /**
     * Get list of notifications by employee
     * ENDPOINT  GET localhost:9000/notifications/employee/{employeeId}
     * @param employeeId
     * @return
     */
    @GetMapping("/employee/{employeeId}")
    public List<Notification> getNotificationByEmployee(@PathVariable Long employeeId ){
        Employee employee = employeeRepository.findById(employeeId).get();
        return notificationService.getNotificationByEmployee(employee);
    }



    @ExceptionHandler(InvalidCredential.class)
    public ResponseEntity<String > handleResourceNotFoundExceptions(InvalidCredential e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The requested resource not Found");
    }
}
