package com.revature.EmployeeManagement.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
@Entity
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Leave {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstname;
    private String lastname;
    private LocalDate startDate;
    private LocalDate endDate;
    private String leaveType;
    private String status;
    private String notes;
    private String feedback;
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    @JoinColumn(name = "employeeId")
    private Employee employee;

    @Column(name = "employeeId", insertable = false, updatable = false)
    private long employeeId;
}
