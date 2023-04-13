package com.revature.EmployeeManagement.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

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
    private boolean isManager;
    @Column
    private Long managerId;

    @OneToMany(fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Leave> leaves;


}
