package com.revature.EmployeeManagement.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Goal {
    @Id
    private Long id;
    private String name;
    private String description;
    private LocalDate deadline;
    private int weightage;

    @OneToMany(fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Employee> employees;


}
