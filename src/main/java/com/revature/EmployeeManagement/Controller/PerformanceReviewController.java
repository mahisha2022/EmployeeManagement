package com.revature.EmployeeManagement.Controller;

import com.revature.EmployeeManagement.Exception.InvalidCredential;
import com.revature.EmployeeManagement.Model.Employee;
import com.revature.EmployeeManagement.Model.Goal;
import com.revature.EmployeeManagement.Model.PerformanceReview;
import com.revature.EmployeeManagement.Service.GoalService;
import com.revature.EmployeeManagement.Service.PerformanceReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/performance")
public class PerformanceReviewController {

    @Autowired
    private PerformanceReviewService performanceReviewService;
    @Autowired
    private GoalService goalService;

    /**
     * ENDPOINT POST localhost:9000/performance/request/{goalId}
     * @param performanceReview
     * @param goalId
     * @return
     */
    @PostMapping("/request/{goalId}")
    public ResponseEntity<String> performanceReviewRequest(@RequestBody PerformanceReview performanceReview, @PathVariable long goalId) {
        try {
            performanceReviewService.requestPerformanceReview(goalId, performanceReview);
            return ResponseEntity.ok("Performance Review Request Sent");
        }catch (InvalidCredential e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * ENDPOINT PATHCH localhost:9000/performance/update/{reviewId}
     * @param performanceReview
     * @param reviewId
     * @return
     */

    @PatchMapping("/update/{reviewId}")
    public ResponseEntity<String> updatePerformanceReview(@RequestBody PerformanceReview performanceReview,
                                                          @PathVariable long reviewId) {
        try {
            performanceReviewService.updatePerformanceReview(reviewId, performanceReview);
            return ResponseEntity.ok("Performance updated successfully");
        }
        catch (InvalidCredential e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PutMapping("/review/{reviewId}")
    public ResponseEntity<String> reviewEmployeePerformance(@RequestBody PerformanceReview performanceReview,
                                                            @PathVariable long reviewId) {
        try {
            performanceReviewService.performanceReviewByManager(reviewId, performanceReview);
            return ResponseEntity.ok("Performance reviewed successfully");
        }
        catch (InvalidCredential e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    /**
     * ENDPOINT DELETE localhost:9000/performance/delete/{reviewId}
     * @param reviewId
     * @return
     */

    @DeleteMapping("/cancel/{reviewId}")
    public ResponseEntity<String> deletePerformanceReview(@PathVariable long reviewId) {
        try {
            performanceReviewService.cancelPerformanceReview(reviewId);
            return ResponseEntity.ok("Performance review deleted successfully");
        }
        catch (InvalidCredential e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("")
    public ResponseEntity<List<PerformanceReview>> getAllPerformanceReviews() {
        List<PerformanceReview> performanceReviews = performanceReviewService.getAllPerformanceReviews();
        return new ResponseEntity<>(performanceReviews, HttpStatus.OK);
    }


    /**
     * Get Performance By Goal Id
     * ENDPOINT GET localhost:9000/performance/goal/{goalId}
     * @param goalId
     * @return
     */

    @GetMapping("/goal/{goalId}")
    public PerformanceReview getPerformanceByGoal(@PathVariable long goalId) {
        Goal goalOptional = goalService.getGoalById(goalId);
        return performanceReviewService.getPerformanceByGoal(goalOptional);

    }

    /**
     * Get Performance review by Performance Id
     * ENDPOINT GET localhost:9000/performance/{reviewId}
     * @param reviewId
     * @return
     */

    @GetMapping("/{reviewId}")
    public PerformanceReview getPerformanceById(@PathVariable long reviewId){
        return performanceReviewService.getPerformanceById(reviewId);
    }

    /**
     * Get performance By Manager
     * ENDPOINT GET localhost:9000/performance/manager/{managerId}
     * @param managerId
     * @return
     */

    @GetMapping("/manager/{managerId}")
    public List<PerformanceReview> getPerformanceReviewByManager(@PathVariable long managerId){
        return performanceReviewService.getPerformanceReviewByManagerId(managerId);
    }

    /**
     * Get performanceByEmployee
     * ENDPOINT GET localhost:9000/performance/employee/{employeeId}
     * @param employeeId
     * @return
     */

    @GetMapping("employee/{employeeId}")
    public List<PerformanceReview> getPerformanceByEmployee(@PathVariable long employeeId){
        return performanceReviewService.getPerformanceReviewByEmployee(employeeId);
    }

    @ExceptionHandler(InvalidCredential.class)
    public ResponseEntity<String > handleResourceNotFoundExceptions(InvalidCredential e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The requested resource not Found");
    }
}

