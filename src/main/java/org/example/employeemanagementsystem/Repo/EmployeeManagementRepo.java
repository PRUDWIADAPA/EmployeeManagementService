package org.example.employeemanagementsystem.Repo;

import org.example.employeemanagementsystem.Model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import java.util.Optional;

@Repository
public interface EmployeeManagementRepo extends JpaRepository<Employee,Integer> {

    Optional<Employee> findByEmail(String email);

    List<Employee> findBySalaryGreaterThan(double salaryIsGreaterThan);

    List<Employee> findByDept(String dept);
}
