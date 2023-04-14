package com.revature.EmployeeManagement.Repositoty;

import com.revature.EmployeeManagement.Model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
