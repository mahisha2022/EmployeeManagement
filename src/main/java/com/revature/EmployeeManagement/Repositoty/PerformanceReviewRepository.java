package com.revature.EmployeeManagement.Repositoty;

import com.revature.EmployeeManagement.Model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PerformanceReviewRepository extends JpaRepository<PerformanceReview, Long> {

    PerformanceReview findByGoal(Goal goal);

    PerformanceReview findByGoalEmployeeId(Long id);

    //@Query("FROM PerformanceReview INNER JOIN Goal ON employeeId = :id ")
    List<PerformanceReview> findByGoal_EmployeeId(Long id);

}
