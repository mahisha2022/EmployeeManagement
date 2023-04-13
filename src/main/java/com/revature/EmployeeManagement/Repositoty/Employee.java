package com.revature.EmployeeManagement.Repositoty;

import org.springframework.data.jpa.repository.JpaRepository;

public interface Employee extends JpaRepository<Employee, Long> {
}
