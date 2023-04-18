package com.revature.EmployeeManagement.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "admin")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private int isAdmin;

    @OneToMany(fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Leave> leaves;

    @OneToMany(fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<PerformanceReview> performanceReviews;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "goalId")
    @JsonManagedReference
    private List<Goal> goals;

    // Getters and setters
}
