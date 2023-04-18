package com.revature.EmployeeManagement.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Goal {
    @Id
    private Long id;
    private String name;
    private String description;
    private LocalDate deadline;
    private int weightage;
    private String comments;
    private String status;


    @OneToMany(fetch = FetchType.EAGER)
    @JsonManagedReference
    private Employee employees;


}
