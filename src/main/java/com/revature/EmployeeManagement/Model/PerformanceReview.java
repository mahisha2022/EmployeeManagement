package com.revature.EmployeeManagement.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long reviewNumber;
    private String deliverables;
    private String achievements;
    private String areaOfImprovement;
    private int score;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    private Employee employee;



}
