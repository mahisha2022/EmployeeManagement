package com.revature.EmployeeManagement.Controller;

import com.revature.EmployeeManagement.Exception.InvalidCredential;
import com.revature.EmployeeManagement.Exception.UserNotFoundException;
import com.revature.EmployeeManagement.Model.Employee;
import com.revature.EmployeeManagement.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/revWorkforce")
public class EmployeeController {

    EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }

    /**
     * Register new employee
     * @param employee
     * @return
     */
    @PostMapping("/register")
    public ResponseEntity<String> postEmployee(@RequestBody Employee employee){
        try {
            employeeService.createEmployee(employee);
            return ResponseEntity.ok("Employee registered successfully");
        }catch (InvalidCredential e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * login into existed account
     * Endpoint POST localhost:9000/revWorkforce/login
     * @param employee
     * @return
     */

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Employee employee ){
        try {
            Employee employeeLogin = employeeService.login(employee.getEmail(), employee.getPassword());
            return ResponseEntity.ok(employeeLogin);
        } catch (UserNotFoundException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
    }

    /**
     * getEmployee by Id
     * Endpoint GET localhost:9000/revWorkforce/employee/{id}
     * @param employeeId
     * @return
     */

    @GetMapping("/employee/{employeeId}")
    public Optional<Employee> getEmployeeById(@PathVariable long employeeId){
        return employeeService.getEmployeeById(employeeId);

    }

    /**
     * get Employee by manager
     * Endpoint GET localhost:9000/revWorkforce/employee/manager/{managerId}
     * @param managerId
     * @return
     */

    @GetMapping("/employee/manager/{managerId}")
    public List<Employee> getEmployeeByManager(@PathVariable long managerId){
        return employeeService.getEmployeeByManager(managerId);
    }

    /**
     * get All Employees
     * Endpoint GET localhost:9000/revWorkforce/employee
     * @return
     */

    @GetMapping("/employees")
    public List<Employee> getAllEmployees(){
        return employeeService.getAllEmployee();
    }


    /**
     * get manager by it's employee id
     * Endpoint GET localhost:9000/revWorkforce/manager/{managerId}
     * @param id
     * @return
     */
    @GetMapping("/manager/{id}")
    public ResponseEntity<Employee> getManagerById(@PathVariable Long id ){
        Employee manager = employeeService.getManagerById(id);
        return new ResponseEntity<>(manager, HttpStatus.OK);
    }

    /**
     * Update employee by it's id
     * Endpoint PATCH localhost:9000/revWorkforce/employee/{id}
     * @param id
     * @param employee
     * @return
     */


    @PatchMapping("/employee/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable long id, @RequestBody Employee employee){
        Employee updatedEmployee = employeeService.updateEmployeeById(id, employee);
        return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
    }


    /**
     * Custom exception handler for unavailable resources
     * @param e
     * @return
     */

    @ExceptionHandler(InvalidCredential.class)
    public ResponseEntity<String > handleResourceNotFoundExceptions(InvalidCredential e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The requested resource not Found");
    }





}
