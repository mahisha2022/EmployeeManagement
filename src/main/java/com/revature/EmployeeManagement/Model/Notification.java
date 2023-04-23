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
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String message;
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "employeeId")
    private Employee employee;

    @Column(insertable=false, updatable=false)
    private Long employeeId;




}
