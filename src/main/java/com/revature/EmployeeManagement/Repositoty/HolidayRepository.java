package com.revature.EmployeeManagement.Repositoty;

import com.revature.EmployeeManagement.Model.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HolidayRepository extends JpaRepository<Holiday, Long> {
}
