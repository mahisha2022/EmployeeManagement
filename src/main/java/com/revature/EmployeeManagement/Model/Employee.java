package com.revature.EmployeeManagement.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private  String password;
    private String phoneNumber;
    private boolean isManager;
    private Long managerId;

    @OneToMany(fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Leave> leaves;


}
