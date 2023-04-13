package com.revature.EmployeeManagement.Service;


import com.revature.EmployeeManagement.Exception.UserNotFoundException;
import com.revature.EmployeeManagement.Exception.UsernameAlreadyExistsException;
import com.revature.EmployeeManagement.Model.Employee;
import com.revature.EmployeeManagement.Model.Leave;
import com.revature.EmployeeManagement.Repositoty.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    private List<Leave> leaves;
    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
        leaves = new ArrayList<>();
    }

    /**
     * register an employee with a unique email address
     * @param employee
     * @return
     */

    public Employee createEmployee(Employee employee){
        if(employeeRepository.findByEmail(employee.getEmail())){
            throw new UsernameAlreadyExistsException("Email " + employee.getEmail() + " already exists");
        }
        else {
          return   employeeRepository.save(employee);
        }

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
     * update employee detail , the employee should not be able to update manager id and leave
     * @param employeeId
     * @param updatedEmployee
     * @return
     */
    public Employee updateEmployeeById(long employeeId, Employee updatedEmployee){
        Employee employee = employeeRepository.findById(employeeId).get();
        employee.setEmail(updatedEmployee.getEmail());
        employee.setPhoneNumber(updatedEmployee.getPhoneNumber());
        employee.setFirstName(updatedEmployee.getFirstName());
        employee.setLastName(updatedEmployee.getLastName());
        return employee;
        }




    public List<Employee> getEmployeeByManager(Long managerId){
        List<Employee> employees = employeeRepository.findByManagerId(managerId);
        return employees;
    }


}
