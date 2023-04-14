package com.revature.EmployeeManagement.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PerformanceReview {

    @Id
    Long id;
    private String deliverables;
    private String achievements;
    private String areaOfImprovement;
    private int score;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    private Employee employee;



}
