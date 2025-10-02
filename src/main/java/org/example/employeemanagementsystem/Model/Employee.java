package org.example.employeemanagementsystem.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String email;

    private double salary;

    private String dept;

    private String team;

    public Employee(String name, String email, double salary, String dept, String team) {
        this.name = name;
        this.email = email;
        this.salary = salary;
        this.dept = dept;
        this.team = team;
    }
}
