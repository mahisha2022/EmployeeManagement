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
