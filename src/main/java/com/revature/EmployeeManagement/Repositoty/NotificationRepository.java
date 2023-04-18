package com.revature.EmployeeManagement.Repositoty;

import com.revature.EmployeeManagement.Model.Employee;
import com.revature.EmployeeManagement.Model.Leave;
import com.revature.EmployeeManagement.Model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByEmployee(Employee employee);
    List<Notification> findByEmployee_ManagerId(Long managerId);
    List<Notification> findByManagerId(Long employeeId);

    List<Notification> findByEmployeeId(Long employeeId);
}
