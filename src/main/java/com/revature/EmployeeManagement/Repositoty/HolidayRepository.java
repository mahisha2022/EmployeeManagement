package com.revature.EmployeeManagement.Repositoty;

import com.revature.EmployeeManagement.Model.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HolidayRepository extends JpaRepository<Holiday, Long> {


    List<Holiday> findByDatesAfter(String toString);
}
