package com.revature.EmployeeManagement.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "admin")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private int isAdmin;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "adminId")
    @JsonManagedReference
    private List<Employee> employee;

//    @OneToMany(fetch = FetchType.EAGER)
//    @JsonManagedReference
//    private List<Leave> leaves;

//    @OneToMany(fetch = FetchType.EAGER)
//    @JsonManagedReference
//    private List<PerformanceReview> performanceReviews;
//
//    @OneToMany(fetch = FetchType.EAGER)
//    @JoinColumn(name = "goalId")
//    @JsonManagedReference
//    private List<Goal> goals;


}
