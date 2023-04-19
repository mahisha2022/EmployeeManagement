package com.revature.EmployeeManagement.Service;

import com.revature.EmployeeManagement.Exception.InvalidCredential;
import com.revature.EmployeeManagement.Model.Employee;
import com.revature.EmployeeManagement.Model.Goal;
import com.revature.EmployeeManagement.Model.Notification;
import com.revature.EmployeeManagement.Repositoty.EmployeeRepository;
import com.revature.EmployeeManagement.Repositoty.GoalRepository;
import com.revature.EmployeeManagement.Repositoty.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.PublicKey;
import java.util.List;
import java.util.Optional;

@Service
public class GoalService {

    private GoalRepository goalRepository;

    private EmployeeRepository employeeRepository;
    private NotificationService notificationService;
    private NotificationRepository notificationRepository;
    @Autowired
    public GoalService(GoalRepository goalRepository, EmployeeRepository employeeRepository,
                       NotificationRepository notificationRepository, NotificationService notificationService){
        this.goalRepository = goalRepository;
        this.employeeRepository = employeeRepository;
        this.notificationService = notificationService;
        this.notificationRepository = notificationRepository;
    }

    /**
     * Assign a new goal to an employee
     * @param goal
     * @param employeeId
     * @return
     */

    public Goal createGoal(Goal goal, long employeeId){
        Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);
        if (!employeeOptional.isPresent()){
            throw new InvalidCredential("Employee not found");
        }
        Employee employee = employeeOptional.get();
        //Check if goal with the same name and deadline already exist
        Optional<Goal> existingGoal = goalRepository.findByNameAndDeadlineAndEmployeeId(goal.getName(), goal.getDeadline(), employeeId);
        if(existingGoal.isPresent()){
            throw new InvalidCredential("Goal with the same name and deadline already assigned to "+ goal.getEmployees().getFirstName());
        }
        //create a new instance of a goal entity
        goal.setEmployees(employee);
        goal.setStatus("Pending");
        Goal savedGoal = goalRepository.save(goal);
        employee.getGoal().add(savedGoal);
        employeeRepository.save(employee);


        //send notification here

        Notification employeeNotification = new Notification();
        employeeNotification.setEmployee(employee);
        employeeNotification.setMessage(employee.getFirstName() + ", you have new goal assigned. Please review.");
        notificationRepository.save(employeeNotification);


        return savedGoal;

    }




    /**
     * Method to accept a goal
     * @param goalId
     * @return
     */

    public Goal acceptGoal(Long goalId){
        //retrieve the goal from the goal repository
        Goal goal = goalRepository.findById(goalId).get();
        //set the status to "accepted
        goal.setStatus("Accepted");

        //send notification here
        Notification employeeNotification = new Notification();
        employeeNotification.setEmployee(goal.getEmployees());
//        employeeNotification.setManagerId(goal.getEmployees().getManagerId());
        employeeNotification.setMessage(goal.getEmployees().getFirstName() + ", have accepted the assigned goal " + goal.getName() );
        notificationRepository.save(employeeNotification);


        return goalRepository.save(goal);

    }

    /**
     * Method to let employee negotiate about the assigned goal
     * @param goalId
     * @param comments
     * @return
     */

    public Goal negotiateGoal(Long goalId, String comments){
        //retrieve data from the goal repository
        Goal goal = goalRepository.findById(goalId).get();
        //set the comment
        goal.setComments(comments);
        goal.setStatus("Returned back for a review");


        //send notification here
        Notification employeeNotification = new Notification();
        employeeNotification.setEmployee(goal.getEmployees());
//        employeeNotification.setManagerId(goal.getEmployees().getManagerId());
        employeeNotification.setMessage(goal.getEmployees().getFirstName() + ", have returned the assigned goal " + goal.getName() +
                " back. Please review");
        notificationRepository.save(employeeNotification);

        //save the negotiated goal into repository
        return goalRepository.save(goal);
    }

    /**
     * Update a goal if only the status is not "Accepted"
     * @param goalId
     * @param goal
     * @return
     */

    public Goal updateGoal(Long goalId, Goal goal){
        //retrieve the goal from the repository
        Goal existedGoal = goalRepository.findById(goalId).get();

        //update the goal
        if(existedGoal.getStatus() != "Accepted") {
            existedGoal.setDeadline(goal.getDeadline());
            existedGoal.setName(goal.getName());
            existedGoal.setWeightage(goal.getWeightage());
            existedGoal.setDescription(goal.getDescription());
            existedGoal.setComments(goal.getComments());

            // save the updated goal
            return goalRepository.save(existedGoal);

            //send notification here
        }
        else {
            throw new InvalidCredential("Accepted Goal cannot be updated!");
        }
    }

    /**
     * Return goal by employee id
     *
     * @param employeeId
     * @return
     */

    public List<Goal> getGoalByEmployeeId(long employeeId){
      return goalRepository.findByEmployeeId(employeeId);
    }


    public List<Goal> getGoalByManagerId(long managerId){
        return goalRepository.findByEmployees_ManagerId(managerId);
    }
    public List<Goal> getAllGoal(){
        return goalRepository.findAll();
    }


}
