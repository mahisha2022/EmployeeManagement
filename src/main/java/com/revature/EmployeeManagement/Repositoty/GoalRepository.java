package com.revature.EmployeeManagement.Repositoty;

import com.revature.EmployeeManagement.Model.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@Repository
public interface GoalRepository extends JpaRepository<Goal, Long> {
    Optional<Goal> findByNameAndDeadline(String name, LocalDate deadline);


    Optional<Goal> findByNameAndDeadlineAndEmployeeId(String name, LocalDate deadline, long employeeId);
    @Query("from Goal where employeeId = :employeeId and personal = :personal")
    List<Goal> findByEmployeeId(long employeeId, long personal);

    List<Goal> findByEmployeeId(long employeeId);

    //@Query("from Goal where employeeId = :employeeId and personal = :personal")
    List<Goal> findByEmployees_ManagerIdAndPersonal(long employeeId, long personal);

    void deleteByEmployeeId(Long employeeId);



}
