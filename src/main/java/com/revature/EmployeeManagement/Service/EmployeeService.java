package com.revature.EmployeeManagement.Service;


import com.revature.EmployeeManagement.Exception.UserNotFoundException;
import com.revature.EmployeeManagement.Exception.InvalidCredential;
import com.revature.EmployeeManagement.Model.Employee;
import com.revature.EmployeeManagement.Model.Leave;
import com.revature.EmployeeManagement.Repositoty.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    PasswordEncoder passwordEncoder;
    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
        leaves = new ArrayList<>();
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    /**
     * register an employee with a unique email address
     * @param employee
     * @return
     */

    public Employee createEmployee(Employee employee) throws InvalidCredential {

        //Check if email is already exists
        Employee existedEmployee = employeeRepository.findByEmail(employee.getEmail());
        if(existedEmployee != null){
            throw new InvalidCredential("Email " + employee.getEmail() + " already exists");
        }
        //check if employee email and password meet the requirements
        String email = employee.getEmail();
        String password = employee.getPassword();

        if(!email.matches("^.+@.+$")){
            throw  new InvalidCredential("Invalid email format");
        }
        if (password.length() < 6 || !password.matches(".*[a-zA-Z].*")){
            throw new InvalidCredential("Password must be at least 6 characters long and must contain letters");
        }
        if (employee.getIsManager() !=1) {
            employee.setIsManager(0);
        }
       //If all requirement pass, save the employee and encode the password
//            String encodedPassword = this.passwordEncoder.encode(employee.getPassword());
//            employee.setEmail(encodedPassword);
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
//            String encodedPassword = employee.getPassword();
//            return employee;
//            if(!this.passwordEncoder.matches(password.trim(), encodedPassword)){
//                throw new InvalidCredential("Invalid Password");
//            }

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
