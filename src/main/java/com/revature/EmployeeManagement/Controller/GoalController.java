package com.revature.EmployeeManagement.Controller;


import com.revature.EmployeeManagement.Exception.InvalidCredential;
import com.revature.EmployeeManagement.Model.Goal;
import com.revature.EmployeeManagement.Service.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/goals")
@CrossOrigin("*")
public class GoalController {


    private GoalService goalService;
    @Autowired
    public GoalController(GoalService goalService){
        this.goalService = goalService;
    }

    /**
     * EndPoint POST localhost:9000/goals/employees/{employeeId}
     * @param goal
     * @param employeeId
     * @return
     */

    @PostMapping("/employees/{employeeId}")
    public ResponseEntity<String> assignGoal(@RequestBody Goal goal, @PathVariable long employeeId){
        try {
            goalService.createGoal(goal, employeeId);
            return ResponseEntity.ok("Goal successfully assigned");
        }
        catch (InvalidCredential e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to assign goal. Please try again later");
        }
    }


    @PostMapping("/{goalId}/accept")
    public ResponseEntity<String> acceptGoal(@PathVariable long goalId){
        try {
            goalService.acceptGoal(goalId);
            return ResponseEntity.ok("Goal " + goalService.acceptGoal(goalId).getName() + " deadline: " +
                    goalService.acceptGoal(goalId).getDeadline() + " successfully accepted!");
        }
        catch (InvalidCredential e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to accept goal. Please try again later");
        }

    }

    @PutMapping("/{goalId}/negotiate")
    public ResponseEntity<String> negotiate(@PathVariable Long goalId, @RequestBody String comments){
        try {
            goalService.negotiateGoal(goalId, comments);
            return ResponseEntity.ok("Goal returned back to review");
        }
        catch (InvalidCredential e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed return goal. Please try again later");
        }
    }

    @PatchMapping("/{goalId}/update")
    public ResponseEntity<String> updateGoal(@PathVariable Long goalId, @RequestBody Goal goal){
        try {
            goalService.updateGoal(goalId, goal);
            return ResponseEntity.ok("Goal updated successfully");
        }
        catch (InvalidCredential e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update goal. Please try again later");
        }
    }
//    For Testing
    @GetMapping("")
    public List<Goal> getGoals() {
        return goalService.getGoals();
    }


    @ExceptionHandler(InvalidCredential.class)
    public ResponseEntity<String > handleResourceNotFoundExceptions(InvalidCredential e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The requested resource not Found");
    }

}
