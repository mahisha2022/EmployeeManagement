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
@CrossOrigin("*")
@RequestMapping("/goals")
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

    @PatchMapping("/{goalId}/comment")
    public ResponseEntity<String> updateGoalComments(@PathVariable Long goalId, @RequestBody String comment){
        try {
            goalService.updateGoalComment(goalId, comment);
            return ResponseEntity.ok("Comment added successfully");
        }
        catch (InvalidCredential e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update goal. Please try again later");
        }
    }

    @PostMapping("/{goalId}/complete")
    public ResponseEntity<String> completeGoal(@PathVariable long goalId){
        try {
            goalService.completeGoal(goalId);
            return ResponseEntity.ok("Goal " + goalService.acceptGoal(goalId).getName() + " deadline: " +
                    goalService.completeGoal(goalId).getDeadline() + " successfully completed!");
        }
        catch (InvalidCredential e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to accept goal. Please try again later");
        }

    }
//    For Testing

    /**
     * Get all goals
     * ENDPOINT GET localhost:9000/goals
     * @return
     */

    @GetMapping("")
    public List<Goal> getAllGoal(){
        return goalService.getAllGoal();
    }

    @GetMapping("/employee/{employeeId}/{personal}")
    public ResponseEntity<List<Goal>> getGoalByEmployeeId(@PathVariable long employeeId, @PathVariable long personal){
       List<Goal> goals = goalService.getGoalByEmployeeId(employeeId, personal);
       if(goals != null){
           return ResponseEntity.ok(goals);
       }
       else {
           return ResponseEntity.notFound().build();
       }
    }

    @GetMapping("/manager/{managerId}")
    public ResponseEntity<List<Goal>> getLeaveByManager(@PathVariable Long managerId){
        List<Goal> goals = goalService.getGoalByManagerId(managerId);
        if(goals.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(goals, HttpStatus.OK);
    }

    @GetMapping("/employees/fellow/{managerId}")
    public List<Goal> getFellowEmployeesGoal(@PathVariable long managerId){
       return goalService.getAllGoalForFellowEmployees(managerId);
    }



    @ExceptionHandler(InvalidCredential.class)
    public ResponseEntity<String > handleResourceNotFoundExceptions(InvalidCredential e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The requested resource not Found");
    }

}
