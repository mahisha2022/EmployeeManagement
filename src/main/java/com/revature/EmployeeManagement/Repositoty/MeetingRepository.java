package com.revature.EmployeeManagement.Repositoty;

import com.revature.EmployeeManagement.Model.Employee;
import com.revature.EmployeeManagement.Model.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {

    List<Meeting> findByEmployee(Employee employee);

    List<Meeting> findByEmployee_ManagerId(Long managerId);

    List<Meeting> findByEmployeeManagerId(Long id);

//    List<Meeting> findByEmployeeIdAndStartTimeBetween(long employeeId, LocalTime startTime, LocalTime endTime);

    List<Meeting> findByEmployeeIdAndStartDateAndStartTimeBetween(long employeeId, LocalDate startDate, LocalTime startTime, LocalTime endTime);
}
