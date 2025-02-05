package com.search.specification.dao.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "employee")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "employee_id")
    private Integer id;
    @Column(name = "employee_name")
    private String name;
    @Column(name = "status")
    private String status;
    @Column(name = "joining_date")
    private Timestamp joiningDate;
    @Column(name = "salary")
    private Double salary;
}
