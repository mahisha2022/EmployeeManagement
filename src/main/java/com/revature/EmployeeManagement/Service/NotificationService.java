package com.revature.EmployeeManagement.Service;

import com.revature.EmployeeManagement.Model.Employee;
import com.revature.EmployeeManagement.Model.Leave;
import com.revature.EmployeeManagement.Model.Notification;
import com.revature.EmployeeManagement.Repositoty.EmployeeRepository;
import com.revature.EmployeeManagement.Repositoty.NotificationRepository;
import com.revature.EmployeeManagement.Repositoty.LeaveRepository;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private EmployeeRepository employeeRepository;


    public List<Notification> getAllNotifications(){
        return notificationRepository.findAll();
    }

    public List<Notification> getNotificationByEmployee(Employee employee){
        return notificationRepository.findByEmployee(employee);
    }
    public Notification getNotificationById(long id){
        return notificationRepository.findById(id).get();
    }

    public Notification submitNotificationToManager(Notification notification, Employee employee){
        if (employee.getId() != null){
            Notification employeeNotification = new Notification();
            employeeNotification.setMessage(notification.getMessage());
            employeeNotification.setEmployee(employee);
//        employeeNotification.setEmployeeId(employee.getId());
            notificationRepository.save(employeeNotification);
            return employeeNotification;
        }

        if(employee.getManagerId() != null){
            Notification managerNotification = new Notification();
            managerNotification.setMessage(notification.getMessage());
            managerNotification.setManagerId(employee.getManagerId());
//            managerNotification.setManagerId(employee.getManager().getId());
            notificationRepository.save(managerNotification);
            return managerNotification;
        }
        return notification;

    }
    public List<Notification> getNotificationByManager(Long managerId){
        return  notificationRepository.findByManagerId(managerId);
    }

    public List<Notification> getNotificationByEmployee(Long employeeId){
        return notificationRepository.findByEmployeeId(employeeId);
    }


}
