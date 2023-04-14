package com.revature.EmployeeManagement.Service;

import com.revature.EmployeeManagement.Exception.InvalidCredential;
import com.revature.EmployeeManagement.Model.Employee;
import com.revature.EmployeeManagement.Model.Leave;
import com.revature.EmployeeManagement.Model.PerformanceReview;
import com.revature.EmployeeManagement.Repositoty.EmployeeRepository;
import com.revature.EmployeeManagement.Repositoty.LeaveRepository;
import com.revature.EmployeeManagement.Repositoty.PerformanceReviewRepository;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PerformanceReviewService {

    private EmployeeRepository employeeRepository;

    private PerformanceReviewRepository performanceReviewRepository;
    @Autowired
    public PerformanceReviewService(PerformanceReviewRepository performanceReviewRepository, EmployeeRepository employeeRepository,
                                    NotificationService notificationService){
        this.employeeRepository = employeeRepository;
        this.performanceReviewRepository = performanceReviewRepository;
    }


    /**
     * return all leaves
     * @return
     */
    public List<PerformanceReview> getAllPerformanceReviews(){
        return performanceReviewRepository.findAll();
    }
    public List<PerformanceReview> getPerformanceReviewByEmployeeId(Employee employee){
        return performanceReviewRepository.findByEmployee(employee);
    }



        // Other existing code

        public PerformanceReview savePerformanceReview(PerformanceReview performanceReview) {
            return performanceReviewRepository.save(performanceReview);
        }



    }
