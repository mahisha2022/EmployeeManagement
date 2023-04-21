package com.revature.EmployeeManagement.Repositoty;

import com.revature.EmployeeManagement.Model.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PerformanceReviewRepository extends JpaRepository<PerformanceReview, Long> {

    PerformanceReview findByGoal(Goal goal);

    PerformanceReview findByGoalEmployeeId(Long id);



}
