package com.revature.EmployeeManagement.Repositoty;

import com.revature.EmployeeManagement.Model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByManagerId(Long managerId);

    boolean findByEmail(String email);

    Employee findByEmailAndPassword(String email, String password);
}
