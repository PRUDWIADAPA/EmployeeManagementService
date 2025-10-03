package org.example.employeemanagementsystem.ServiceTest;

import org.assertj.core.api.Assertions;
import org.example.employeemanagementsystem.Model.Employee;
import org.example.employeemanagementsystem.Repo.EmployeeManagementRepo;
import org.example.employeemanagementsystem.Service.EmployeeManagementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class EmployeeManagementServiceTest {

    @Mock
    private EmployeeManagementRepo employeeManagementRepo;

    @InjectMocks
    private EmployeeManagementService employeeManagementService;

    private Employee employee;

    int id;

    @BeforeEach
    public void set()
    {
        id=1;
        employee=new Employee();
        employee.setName("PRUDWI CEO");
        employee.setEmail("prudwiceo@protech.com");
        employee.setTeam("CEO");
        employee.setSalary(388.5);
        employee.setId(id);

    }


    @Test
    public void Test_Given_EmpObj_When_Save_Emp_Then_Return_SavedEmpObj()
    {
        BDDMockito.given(employeeManagementRepo.findById(id)).willReturn(Optional.empty());
        BDDMockito.given(employeeManagementRepo.save(employee)).willAnswer(in->in.getArgument(0));

        Employee emp=employeeManagementService.addEmp(employee);

        Assertions.assertThat(emp).isNotNull();
    }


    @Test
    public void Test_Given_Emp_Id_When_GetEmp_ById_Then_Return_EmpObj()
    {
        BDDMockito.given(employeeManagementRepo.findById(id)).willReturn(Optional.of(employee));

        Optional<Employee>employee1=employeeManagementRepo.findById(id);

        Assertions.assertThat(employee1).isNotEmpty();
    }

    @Test
    public void Test_Given_UpdateEmpObj_when_UpdateEmp_Then_Return_Updated_EmpObj()
    {
        BDDMockito.given(employeeManagementRepo.findById(id)).willReturn(Optional.of(employee));


        BDDMockito.given(employeeManagementRepo.save(employee)).willReturn(employee);

        Employee employee1=employeeManagementService.getEmpById(id);
        employee1.setSalary(384747.5);
        employee1.setTeam("ceo");
        employee1.setEmail("Prudwiceo@2025Protech.com");
        employee1.setDept("CEO");

        Employee updatedEmp=employeeManagementService.updateEmp(id,employee1);

        Assertions.assertThat(updatedEmp).isNotNull();

        Assertions.assertThat(updatedEmp.getDept()).isEqualTo("CEO");
    }


    @Test
    public void Given_EmpId_when_Delete_Emp_ById_Then_DeleteEmp()
    {
        BDDMockito.given(employeeManagementRepo.findById(id)).willReturn(Optional.of(employee));

        BDDMockito.willDoNothing().given(employeeManagementRepo).deleteById(id);

        employeeManagementService.DeleteEmpById(id);

        Mockito.verify(employeeManagementRepo,Mockito.times(1)).deleteById(id);

    }


    @Test
    public void Given_empDept_when_empByDept_Then_Return_EmpObj()
    {

        Employee emp2=new Employee();

        emp2.setDept("CEO");
        emp2.setName("PRUDWICEO");

        List<Employee>list=new ArrayList<>();

        list.add(employee);
        list.add(emp2);

        BDDMockito.given(employeeManagementRepo.findAll()).willReturn(list);
        BDDMockito.given(employeeManagementRepo.findByDept(employee.getDept())).willReturn(list);


        List<Employee>list1=employeeManagementService.empByDept(employee.getDept());

        Assertions.assertThat(list1.size()).isEqualTo(list.size());
    }



}
