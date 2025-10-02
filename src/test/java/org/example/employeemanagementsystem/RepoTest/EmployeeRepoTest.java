package org.example.employeemanagementsystem.RepoTest;

import org.assertj.core.api.Assertions;
import org.example.employeemanagementsystem.Model.Employee;
import org.example.employeemanagementsystem.Repo.EmployeeManagementRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DataJpaTest
public class EmployeeRepoTest {

    @Autowired
    private EmployeeManagementRepo repo;

    Employee employee;

    int id;

    @BeforeEach
    public void setup()
    {
        repo.deleteAll();
        employee=new Employee();
        id=1;

        //employee.setId(id);
        employee.setName("PRUDWI CEO");
        employee.setEmail("prudwiceo@protech.com");
        employee.setDept("CEO");
        employee.setTeam("CEO");
        employee.setSalary(47589598.97);
    }

    @Test
    public void Test_Given_Emp_obj_When_SaveEmp_then_Return_SavedEmpObj()
    {
        Employee emp=repo.save(employee);

        Assertions.assertThat(emp).isNotNull();

        Assertions.assertThat(emp.getId()).isGreaterThan(0);

    }

    @Test
    public void Test_GivenEmpId_when_FindEmpById_Then_ReturnEmpObj()
    {
        repo.save(employee);

        Employee emp=repo.findById(employee.getId()).get();

        Assertions.assertThat(emp).isNotNull();
    }

    @Test
    public void Test_given_EmpList_When_GetAllEmp_then_Return_EmpList()
    {
        Employee e=new Employee();
        e.setName("CEO PRUDW");
        e.setEmail("ceoPrudwi@Protech.com");
        e.setDept("CEO");
        e.setTeam("CEO");
        e.setSalary(4948.4);

        List<Employee>list=new ArrayList<>();

        list.add(employee);
        list.add(e);

        repo.saveAll(list);

        List<Employee>employeeList=repo.findAll();

        Assertions.assertThat(employeeList).isNotNull();

        Assertions.assertThat(employeeList).size().isEqualTo(2);

    }

    @Test
    public void Test_Given_EmpId_And_EmpObj_when_UpdateEmp_then_Return_Updated_EmpObj()
    {
        repo.save(employee);
        Employee e=repo.findById(employee.getId()).get();

        e.setName("PRUDWI CEO 2025");

       Employee updated= repo.save(e);

       Assertions.assertThat(updated).isNotNull();

       Assertions.assertThat(updated.getName()).isEqualTo("PRUDWI CEO 2025");
    }


}
