package com.revature.EmployeeManagement.Repositoty;

import com.revature.EmployeeManagement.Model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByManagerId(Long managerId);

    Employee findByEmail(String email);

    Employee findByEmailAndPassword(String email, String password);

    @Query("SELECT e FROM Employee e WHERE e.id = :employeeId AND e.isManager = 1")
    Optional<Employee> findManagerByEmployee(@Param("employeeId") Long employeeId);

    @Query("SELECT e FROM Employee e WHERE e.isManager = 1")
    Employee findByIsManager(long managerId);
}
