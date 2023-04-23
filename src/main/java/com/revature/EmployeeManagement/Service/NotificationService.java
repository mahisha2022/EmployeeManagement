package com.revature.EmployeeManagement.Service;

import com.revature.EmployeeManagement.Model.Employee;
import com.revature.EmployeeManagement.Model.Leave;
import com.revature.EmployeeManagement.Model.Notification;
import com.revature.EmployeeManagement.Repositoty.EmployeeRepository;
import com.revature.EmployeeManagement.Repositoty.NotificationRepository;
import com.revature.EmployeeManagement.Repositoty.LeaveRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private EmployeeRepository employeeRepository;


    /**
     * Send notification to employee
     * @param employeeId
     * @param message
     */

    public void submitNotificationToEmployee(Long employeeId, String message){

        Notification notification = new Notification();
        Employee employee = employeeRepository.findById(employeeId).get();
        notification.setEmployee(employee);
        notification.setMessage(message);
        notificationRepository.save(notification);

    }

    /**
     * Send notification to manager
     * @param managerId
     * @param message
     */
    public void submitNotificationToManager(Long managerId, String message){
        Employee manager = employeeRepository.findById(managerId).orElse(null);
        if (manager != null && manager.getIsManager() == 1){
            Notification notification = new Notification();
            notification.setMessage(message);
            notification.setEmployee(manager);
            notificationRepository.save(notification);
        }


    }

    /**
     * Get notification by manager
     * @param managerId
     * @return
     */

    public List<Notification> getNotificationByManager(Long managerId){
       return notificationRepository.findByEmployeeId(managerId);
    }

    /**
     * Get Notification by Employee
     * @param employee
     * @return
     */

    public List<Notification> getNotificationByEmployee(Employee employee){
        return notificationRepository.findByEmployee(employee);
    }

    /**
     * Get all notification
     * @return
     */

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }


}
