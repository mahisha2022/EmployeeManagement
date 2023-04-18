package com.revature.EmployeeManagement.Repositoty;

import com.revature.EmployeeManagement.Model.Employee;
import com.revature.EmployeeManagement.Model.Leave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface LeaveRepository extends JpaRepository<Leave, Long> {


    List<Leave> findByEmployee(Employee employee);

    List<Leave> findByEmployee_ManagerIdOrderByStartDate(Long managerId);

    @Query("SELECT l FROM Leave l WHERE l.employee.id = :employeeId AND (l.startDate <= :endDate AND l.endDate >= :startDate)")
    List<Leave> findLeavesByEmployeeIdAndStartRange(Long employeeId, LocalDate startDate, LocalDate endDate);
}
