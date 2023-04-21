package com.revature.EmployeeManagement.Service;


import com.revature.EmployeeManagement.Exception.UserNotFoundException;
import com.revature.EmployeeManagement.Exception.InvalidCredential;
import com.revature.EmployeeManagement.Model.Employee;
import com.revature.EmployeeManagement.Model.Leave;
import com.revature.EmployeeManagement.Repositoty.*;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class EmployeeService {

    /**
     * as a user I want to create an account
     * as a user I want to login into my account
     * as a user I want update my account
     * as a user I want to request a leave
     * as a user I want to get detail about available leaves
     * as a user I want to get holiday data
     * as a user I want to get notified when leave is approved or rejected
     */

    private EmployeeRepository employeeRepository;
    private LeaveRepository leaveRepository;
    private List<Leave> leaves;
    private NotificationRepository notificationRepository;

    private PerformanceReviewRepository performanceReviewRepository;

    private GoalRepository goalRepository;

    private EmailSenderService emailSenderService;
    PasswordEncoder passwordEncoder;
    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, LeaveRepository leaveRepository, NotificationRepository notificationRepository,
                           PerformanceReviewRepository performanceReviewRepository, GoalRepository goalRepository,
                           EmailSenderService emailSenderService){
        this.employeeRepository = employeeRepository;
        this.leaveRepository = leaveRepository;
        this.notificationRepository= notificationRepository;
        this.performanceReviewRepository = performanceReviewRepository;
        this.goalRepository = goalRepository;
        leaves = new ArrayList<>();
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.emailSenderService = emailSenderService;
    }

    /**
     * register an employee with a unique email address
     * @param employee
     * @return
     */

    public Employee createEmployee(Employee employee) throws InvalidCredential, MessagingException, UnsupportedEncodingException {

        //Check if email is already exists
        Employee existedEmployee = employeeRepository.findByEmail(employee.getEmail());
        if (existedEmployee != null) {
            throw new InvalidCredential("Email " + employee.getEmail() + " already exists");
        }
        //check if employee email and password meet the requirements
        String email = employee.getEmail();
        String password = employee.getPassword();

        if (!email.matches("^.+@.+$")) {
            throw new InvalidCredential("Invalid email format");
        }
//        if (password.length() < 6 || !password.matches(".*[a-zA-Z].*")) {
//            throw new InvalidCredential("Password must be at least 6 characters long and must contain letters");
//        }

        //create random number for password

        Random random = new Random();
        String randomCode = String.valueOf(random.nextInt(9999999));
        employee.setPassword(randomCode);


        //Send verification email

        emailSenderService.sendAccountRegistrationEmail(employee);


        return employeeRepository.save(employee);
    }



    /**
     * employee login with email and password
     * @param email
     * @param password
     * @return
     */

    public Employee login(String email, String password){
        Employee employee = employeeRepository.findByEmailAndPassword(email, password);
            if(employee == null) {
                throw new UserNotFoundException("User with email " + email + " not found!");
            }
            employee.setEmail(email);
            employee.setPassword(password);

            return employee;
        }

    /**
     * Password reset
     * @param employee
     * @throws MessagingException
     * @throws UnsupportedEncodingException
     */
    public void passwordReset(Employee employee) throws MessagingException, UnsupportedEncodingException {
        Employee existedEmployee = employeeRepository.findByEmail(employee.getEmail());
        if(existedEmployee == null) {
            throw new UserNotFoundException("User with email " + employee.getEmail()+ " not found!");
        }
        Random random = new Random();
        String randomCode = String.valueOf(random.nextInt(9999999));
        existedEmployee.setPassword(randomCode);


        emailSenderService.sendPasswordReset(existedEmployee);
        employeeRepository.save(existedEmployee);


    }

    /**
     * update employee detail , the employee should not be able to update manager id and leave
     * @param employeeId
     * @param updatedEmployee
     * @return
     */
    public Employee updateEmployeeById(long employeeId, Employee updatedEmployee){
        Employee employee = employeeRepository.findById(employeeId).get();
        if (updatedEmployee.getEmail() != null)
            employee.setEmail(updatedEmployee.getEmail());
        if (updatedEmployee.getPhoneNumber() != null)
        employee.setPhoneNumber(updatedEmployee.getPhoneNumber());
        if (updatedEmployee.getFirstName() != null)
        employee.setFirstName(updatedEmployee.getFirstName());
        if (updatedEmployee.getLastName() != null)
        employee.setLastName(updatedEmployee.getLastName());
        if (updatedEmployee.getIsManager() != 0)
        employee.setIsManager(updatedEmployee.getIsManager());
        if (updatedEmployee.getManagerId() != null)
        employee.setManagerId(updatedEmployee.getManagerId());
        return employeeRepository.save(employee);
        }


    /**
     * Get Employee By Manager
     * @param managerId
     * @return
     */
    public List<Employee> getEmployeeByManager(Long managerId){
        List<Employee> employees = employeeRepository.findByManagerId(managerId);
        return employees;
    }

    /**
     * Get Employee By Id
     * @param employeeId
     * @return
     */

    public Optional<Employee> getEmployeeById(long employeeId) {

        return employeeRepository.findById(employeeId);
    }

    /***
     * Get All Employee
     * @return
     */

    public List<Employee> getAllEmployee(){
        return employeeRepository.findAll();
    }

    /**
     * Get Manager By Id
     * @param id
     * @return
     */
    public Employee getManagerById(Long id){
       Optional<Employee> employeeOptional = Optional.ofNullable(employeeRepository.findByIdAndIsManager(id, 1));
       if(employeeOptional.isPresent()){
           return employeeOptional.get();
       } else {
           throw  new InvalidCredential("Manager Not Found!");
       }
    }

//
//    public Employee createProfile(Employee employee) throws InvalidCredential {
//        if ((employee.getIsManager() != 0 && employee.getIsManager() != 1)) {
//            throw new InvalidCredential("Invalid 'isManager' value. It should be either 0 or 1.");
//        }
//
//        // Check if email already exists
//        Employee existedEmployee = employeeRepository.findByEmail(employee.getEmail());
//        if (existedEmployee != null) {
//            throw new InvalidCredential("Email " + employee.getEmail() + " already exists");
//        }
//
//        // Check if employee email and password meet the requirements
//        String email = employee.getEmail();
//        String password = employee.getPassword();
//
//        if (!email.matches("^.+@.+$")) {
//            throw new InvalidCredential("Invalid email format");
//        }
//        if (password.length() < 6 || !password.matches(".*[a-zA-Z].*")) {
//            throw new InvalidCredential("Password must be at least 6 characters long and must contain letters");
//        }
//
//        // If all requirements pass, save the employee
//        return employeeRepository.save(employee);
//    }
//
//    public List<Leave> getAllLeaves() {
//        return leaveRepository.findAll();
//    }
//
//    public void deleteManagerById(long managerId) {
//        Employee manager = employeeRepository.findByIdAndIsManager(managerId, 1);
//        if (manager != null) {
//            employeeRepository.deleteById(managerId);
//        } else {
//            throw new RuntimeException("Manager not found with id: " + managerId);
//        }
//    }
//
//
//    @Transactional
//    public void deleteEmployeeById(long employeeId) {
//        // Find the employee by id
//        Optional<Employee> employeeOpt = employeeRepository.findById(employeeId);
//
//        if (employeeOpt.isPresent()) {
//            Employee employee = employeeOpt.get();
//
//            // Delete associated records in the leave table
//            leaveRepository.deleteByEmployee(employee);
//
//
//            performanceReviewRepository.deleteByEmployeeId(employeeId);
//
//
//
//            // Delete notifications related to the employee
//            notificationRepository.deleteByEmployeeId(employeeId);
//
//            goalRepository.deleteByEmployeeId(employeeId);
//            // Delete the employee
//            employeeRepository.deleteById(employeeId);
//
//
//        } else {
//            throw new RuntimeException("Employee not found with id: " + employeeId);
//        }
//    }
}
