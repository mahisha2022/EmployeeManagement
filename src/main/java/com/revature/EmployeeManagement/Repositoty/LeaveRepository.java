package com.revature.EmployeeManagement.Repositoty;

import com.revature.EmployeeManagement.Model.Employee;
import com.revature.EmployeeManagement.Model.Leave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LeaveRepository extends JpaRepository<Leave, Long> {


    List<Leave> findByEmployee(Employee employee);

    List<Leave> findByEmployee_ManagerId(Long managerId);
}
