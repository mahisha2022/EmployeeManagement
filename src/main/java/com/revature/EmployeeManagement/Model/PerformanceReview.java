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
    private String managerFeedback;
    private int score;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "goalId", nullable = false)
    @JsonBackReference
    private Goal goal;


}
