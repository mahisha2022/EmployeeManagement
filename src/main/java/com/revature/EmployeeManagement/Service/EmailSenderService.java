package com.revature.EmployeeManagement.Service;

import com.revature.EmployeeManagement.Model.Admin;
import com.revature.EmployeeManagement.Model.Employee;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
@Service
@NoArgsConstructor
@AllArgsConstructor
public class EmailSenderService {



    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String toEmail, String body, String subject){
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("revworkforce@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);

        mailSender.send(message);


    }

    protected void sendAccountRegistrationEmail(Employee employee) throws MessagingException, UnsupportedEncodingException, MessagingException {
        String toAddress = employee.getEmail();
        String fromAddress = "revworkforce@gmail.com";
        String senderName = "Rev Work Force";
        String subject = "Welcome to Rev WorkForce";
        String content = "Dear [[name]],<br>"
                + "Welcome to Rev Work Force! <br> Please login to your account and update as needed. <br> " +
                "your temporary password is : [[password]] :<br>"
                + "login into your account and change your password. <br>"
                + "Thank you,<br>"
                + "Rev Work Force.";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", employee.getFirstName() + " " + employee.getLastName());
        content = content.replace("[[password]]", employee.getPassword());
        String siteURL = "http://localhost:9000";
        String verifyURL = siteURL + "/verify?code=" ;
//                + user.getVerificationCode();

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        mailSender.send(message);
    }


    protected void sendPasswordReset(Employee employee) throws MessagingException, UnsupportedEncodingException {
        String toAddress = employee.getEmail();
        String fromAddress = "revworkforce@gmail.com";
        String senderName = "Rev Work Force";
        String subject = "Password Reset";
        String content = "Dear [[name]],<br>"
                + "Your temporary password is:  <br> " +
                "[[password]] :<br>"
                + "login into your account and change your password. <br>"
                + "Thank you,<br>"
                + "Rev Work Force.";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", employee.getFirstName() + " " + employee.getLastName());
        content = content.replace("[[password]]", employee.getPassword());
        String siteURL = "http://localhost:9000";
        String verifyURL = siteURL + "/verify?code=" ;
//                + user.getVerificationCode();

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        mailSender.send(message);
    }


    protected void sendPasswordResetToAdmin(Admin admin) throws MessagingException, UnsupportedEncodingException {
        String toAddress = admin.getEmail();
        String fromAddress = "revworkforce@gmail.com";
        String senderName = "Rev Work Force";
        String subject = "Password Reset";
        String content = "Dear [[name]],<br>"
                + "Your temporary password is:  <br> " +
                "[[password]] :<br>"
                + "login into your account and change your password. <br>"
                + "Thank you,<br>"
                + "Rev Work Force.";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", admin.getUsername() );
        content = content.replace("[[password]]", admin.getPassword());
        String siteURL = "http://localhost:9000";
        String verifyURL = siteURL + "/verify?code=" ;
//                + user.getVerificationCode();

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        mailSender.send(message);
    }
}
