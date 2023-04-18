package com.revature.EmployeeManagement.Service;

import com.revature.EmployeeManagement.Exception.AdminNotFoundException;
import com.revature.EmployeeManagement.Exception.InvalidCredential;
import com.revature.EmployeeManagement.Model.Admin;
import com.revature.EmployeeManagement.Model.Employee;
import com.revature.EmployeeManagement.Model.Leave;
import com.revature.EmployeeManagement.Repositoty.AdminRepository;
import com.revature.EmployeeManagement.Repositoty.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {

    private final AdminRepository adminRepository;
    private final EmployeeRepository employeeRepository;

    public AdminService(AdminRepository adminRepository, EmployeeRepository employeeRepository) {
        this.adminRepository = adminRepository;
        this.employeeRepository = employeeRepository;
    }

    public Admin createAdmin(Admin admin) {
        return adminRepository.save(admin);
    }

    public Admin getAdminByUsername(String username) throws AdminNotFoundException {
        return adminRepository.findByUsername(username);
    }


    // Other methods
}
