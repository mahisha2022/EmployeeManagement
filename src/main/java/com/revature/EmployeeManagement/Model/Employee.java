package com.revature.EmployeeManagement.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.id.factory.spi.CustomIdGeneratorCreationContext;

import java.util.List;
@Entity
@Data
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor

public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private String email;
    @Column
    private  String password;
    @Column
    private String phoneNumber;
    @Column
    private int isManager;
    @Column
    private Long managerId;
    @Column
    private int leaveBalance;

    @OneToMany(fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Leave> leaves;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "goalId")
    @JsonManagedReference
    private List<Goal> goal;

    @OneToMany(fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Meeting> meetings;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin")
    @JsonBackReference
    private Admin admin;

}
