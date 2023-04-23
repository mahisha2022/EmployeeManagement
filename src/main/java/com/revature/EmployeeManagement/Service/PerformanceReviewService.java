package com.revature.EmployeeManagement.Service;

import com.revature.EmployeeManagement.Exception.InvalidCredential;
import com.revature.EmployeeManagement.Model.Employee;
import com.revature.EmployeeManagement.Model.Goal;
import com.revature.EmployeeManagement.Model.Leave;
import com.revature.EmployeeManagement.Model.PerformanceReview;
import com.revature.EmployeeManagement.Repositoty.EmployeeRepository;
import com.revature.EmployeeManagement.Repositoty.GoalRepository;
import com.revature.EmployeeManagement.Repositoty.LeaveRepository;
import com.revature.EmployeeManagement.Repositoty.PerformanceReviewRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class PerformanceReviewService {


    @Autowired
    private PerformanceReviewRepository performanceReviewRepository;
    @Autowired
    private GoalRepository goalRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private NotificationService notificationService;


    /**
     * Employee will request a performance review for after completing the assigned goal
     * Request performance Review for a goal
     * @param goalId
     * @param performanceReview
     * @return
     */
    public PerformanceReview requestPerformanceReview(Long goalId, PerformanceReview performanceReview ){
        //Get goal by goalId
        Goal goal = goalRepository.findById(goalId).orElseThrow(() -> new IndexOutOfBoundsException("Goal not found"));
        //set the Goal entity
        if (goal.getStatus().equalsIgnoreCase("Accepted")){
            performanceReview.setGoal(goal);

//        performanceReview.setEmployee(goal.getEmployees());
            performanceReview.setAchievements(performanceReview.getAchievements());
            performanceReview.setDeliverables(performanceReview.getDeliverables());


            //save the performance review
            goal.setPerformanceReviews(performanceReview);
            performanceReviewRepository.save(performanceReview);

            //send notification to employee here

            Long managerId = goal.getEmployees().getManagerId();
            String messageToManager = goal.getEmployees().getFirstName() + " " + goal.getEmployees().getLastName() +
                    " requested a performance review. Please review.";
            notificationService.submitNotificationToEmployee(managerId, messageToManager );

            goalRepository.save(goal);}
        else {
            throw new InvalidCredential("Goal is not accepted or on pending. Please check the status.");
        }

        return performanceReview;
    }

    /**
     * Update Performance
     * @param id
     * @param updatedPerformance
     * @return
     */


    public PerformanceReview updatePerformanceReview(Long id, PerformanceReview updatedPerformance) {
        //retrieve the performance from the database
        Optional<PerformanceReview> performanceReviewOptional = performanceReviewRepository.findById(id);
        PerformanceReview performance = performanceReviewOptional.get();
        if (performance != null){
            performance.setDeliverables(updatedPerformance.getDeliverables());
            performance.setAchievements(updatedPerformance.getAchievements());
            performance.setAreaOfImprovement(updatedPerformance.getAreaOfImprovement());
            performanceReviewRepository.save(performance);

        } else {
            throw new InvalidCredential("Performance not found");
        }
        return performance;
    }

    /**
     * Performance Review By manager after employee requested
     * @param id
     * @param updatedPerformance
     * @return
     */


    public PerformanceReview performanceReviewByManager(Long id, PerformanceReview updatedPerformance) {
        //retrieve the performance from the database
        Optional<PerformanceReview> performanceReviewOptional = performanceReviewRepository.findById(id);
        PerformanceReview performance = performanceReviewOptional.get();
        if (performance != null ){
            performance.setManagerFeedback(updatedPerformance.getManagerFeedback());
            performance.setScore(updatedPerformance.getScore());
            performanceReviewRepository.save(performance);

            //send notification to employee here

            Employee employee = performance.getGoal().getEmployees();
            Long employeeId = employee.getId();
            String messageToEmployee = "There is a new performance review for your completed goal" + performance.getGoal().getName();
            notificationService.submitNotificationToEmployee(employeeId, messageToEmployee);

        } else {
            throw new InvalidCredential("Performance not found");
        }
        return performance;
    }

    /**
     * Cancel Performance
     * @param id
     */

    public void cancelPerformanceReview(long id){
        PerformanceReview performanceReview = performanceReviewRepository.findById(id)
                .orElseThrow(() -> new InvalidCredential("Performance not found"));
    }

    /**
     * Get all Performance Reviews
     * @return
     */


    public List<PerformanceReview> getAllPerformanceReviews(){
        return performanceReviewRepository.findAll();
    }

    /**
     * Get Performance by Goal
     * @param goal
     * @return
     */


    public PerformanceReview getPerformanceByGoal(Goal goal){
        //find the goal
        Optional<Goal> goalOptional = goalRepository.findById(goal.getId());
        Goal existedGoal = goalOptional.orElseThrow(() -> new InvalidCredential("Goal not found"));
        return performanceReviewRepository.findByGoal(existedGoal);
    }

    /**
     * Get performance By Performance Id
     * @param id
     * @return
     */

    public PerformanceReview getPerformanceById(long id){
        return performanceReviewRepository.findById(id).get();
    }

    /**
     * Get Performance Review By Manager
     * @param managerId
     * @return
     */

    public List<PerformanceReview> getPerformanceReviewByManagerId(long managerId){
        List<PerformanceReview> performanceReviews = new ArrayList<>();
        List<Employee> employees = employeeRepository.findByManagerId(managerId);
        for (Employee e: employees){
            PerformanceReview performance = performanceReviewRepository.findByGoalEmployeeId(e.getId());
            if (performance != null){
                performanceReviews.add(performance);
            }
        }
        return performanceReviews;
    }

    /**
     * Get Performance By Employee
     * @param employeeId
     * @return
     */

    public List<PerformanceReview> getPerformanceReviewByEmployee(Long employeeId){
        return  performanceReviewRepository.findByGoalEmployeeIdList(employeeId);
    }



}

