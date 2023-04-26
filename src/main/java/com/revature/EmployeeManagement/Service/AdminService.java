package com.revature.EmployeeManagement.Service;

import com.revature.EmployeeManagement.Exception.AdminNotFoundException;
import com.revature.EmployeeManagement.Exception.InvalidCredential;
import com.revature.EmployeeManagement.Exception.UserNotFoundException;
import com.revature.EmployeeManagement.Model.Admin;
import com.revature.EmployeeManagement.Model.Employee;
import com.revature.EmployeeManagement.Model.Leave;
import com.revature.EmployeeManagement.Repositoty.AdminRepository;
import com.revature.EmployeeManagement.Repositoty.EmployeeRepository;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class AdminService {
    @Autowired
    private  AdminRepository adminRepository;
    @Autowired
    private  EmployeeRepository employeeRepository;
    @Autowired
    private EmailSenderService emailSenderService;



    public Admin createAdmin(Admin admin) {
        Admin existedAdmin = adminRepository.findByUsername(admin.getUsername());
        if (existedAdmin != null){
            throw new InvalidCredential("Admin with already existed.");
        }

        return adminRepository.save(admin);
    }

    public Admin getAdminByUsername(String username) throws AdminNotFoundException {
        return adminRepository.findByUsername(username);
    }

    /**
     * Admin login
     * @param username
     * @param password
     * @return
     */

    public Admin adminLogin(String username, String password){
        Admin admin = adminRepository.findByUsernameAndPassword(username, password);
        if (admin == null){
            throw new UserNotFoundException("admin not found");
        }
        return admin;
    }

    /**
     * Admin password Reset
     * @param admin
     * @throws MessagingException
     * @throws UnsupportedEncodingException
     */

    public void adminPasswordReset(Admin admin) throws MessagingException, UnsupportedEncodingException {
        Admin existedAdmin = adminRepository.findByUsername(admin.getUsername());
        if (existedAdmin == null){
            throw new UserNotFoundException("Admin not found");
        }
        Random random = new Random();
        String randomCode = String.valueOf(random.nextInt(9999999));
        existedAdmin.setPassword(randomCode);

        //send email here
        emailSenderService.sendPasswordResetToAdmin(existedAdmin);
        adminRepository.save(existedAdmin);

    }


}
