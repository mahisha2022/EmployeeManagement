package com.revature.EmployeeManagement.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private LocalDate deadline;
    private int weightage;
    private String comments;
    private String status;
    private String fellowEmpComments;
    private int personal;
    private boolean notificationSent;


    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    @JoinColumn(name = "employeeId")
    private Employee employees;

    @Column(name = "employeeId", insertable = false, updatable = false)
    private long employeeId;

    @OneToOne(mappedBy = "goal", cascade = CascadeType.ALL)
    @JsonManagedReference
    private PerformanceReview performanceReviews;

//    public boolean isNotificationSent(){
//        return notificationSent;
//    }

}
