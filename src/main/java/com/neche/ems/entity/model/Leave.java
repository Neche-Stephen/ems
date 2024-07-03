package com.neche.ems.entity.model;

import com.neche.ems.entity.enums.Approved;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "leaves")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Leave extends BaseClass {

    @Column(name = "startDate")
    private LocalDate startDate;

    @Column(name = "endDate")
    private LocalDate endDate;

    @Column(name = "reason")
    private String reason;

    @Enumerated(EnumType.STRING)
    @Column(name = "approvalStatus")
    private Approved approvalStatus;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

}
