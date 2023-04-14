package com.revature.EmployeeManagement.Controller;

import com.revature.EmployeeManagement.Model.Employee;
import com.revature.EmployeeManagement.Model.PerformanceReview;
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
 /* This /request/{employeeId} does NOT create a new employee
    An employee must register to be able to use this functionality
    Once the employee is registered you can write multiple reviews for an individual employee
    The model class has a long variable called reviewNumber. This reviewNumber serves to keep track of the review number you are creating
  */

    @PostMapping("/request/{employeeId}")
    public ResponseEntity<PerformanceReview> performanceReviewRequest(@RequestBody PerformanceReview performanceReview, @PathVariable long employeeId) {
        Employee employee = new Employee();
        employee.setId(employeeId);
        performanceReview.setEmployee(employee);
        PerformanceReview savedPerformanceReview = performanceReviewService.savePerformanceReview(performanceReview);
        return new ResponseEntity<>(savedPerformanceReview, HttpStatus.CREATED);
    }

     /* This /get/{employeeId} retrieves the # of reviews for a particular employee
     for instance get/1 will retrieve the total # of reviews for employee #1.
     So you go through and access the employee reviews this way
  */

    @GetMapping("/get/{employeeId}")
    public ResponseEntity<List<PerformanceReview>> getPerformanceReview(@PathVariable long employeeId) {
        Employee employee = new Employee();
        employee.setId(employeeId);
        List<PerformanceReview> performanceReviews = performanceReviewService.getPerformanceReviewByEmployeeId(employee);
        return new ResponseEntity<>(performanceReviews, HttpStatus.OK);
    }
    /*
This method retrieves all reviews that have been written about every single employee
     */
    @GetMapping("/get/all")
    public ResponseEntity<List<PerformanceReview>> getAllPerformanceReviews() {
        List<PerformanceReview> performanceReviews = performanceReviewService.getAllPerformanceReviews();
        return new ResponseEntity<>(performanceReviews, HttpStatus.OK);
    }
/*
This method for update is not working correctly, although it is a start it is not properly updating
 */
    @PatchMapping("/update/{reviewId}")
    public ResponseEntity<PerformanceReview> updatePerformanceReview(@RequestBody PerformanceReview performanceReview, @PathVariable long reviewId) {
        performanceReview.setReviewNumber(reviewId);
        PerformanceReview updatedPerformanceReview = performanceReviewService.updatePerformanceReview(performanceReview);
        return new ResponseEntity<>(updatedPerformanceReview, HttpStatus.OK);
    }
    /*
    This method deletes 1 review at a time
     */
    @DeleteMapping("/delete/{reviewId}")
    public ResponseEntity<Void> deletePerformanceReview(@PathVariable long reviewId) {
        performanceReviewService.deletePerformanceReviewById(reviewId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    /*
      This method gets a review by its review id
       */
    @GetMapping("/get/review/{reviewId}")
    public ResponseEntity<PerformanceReview> getPerformanceReviewById(@PathVariable long reviewId) {
        PerformanceReview performanceReview = performanceReviewService.getPerformanceReviewById(reviewId);
        return new ResponseEntity<>(performanceReview, HttpStatus.OK);
    }
}

