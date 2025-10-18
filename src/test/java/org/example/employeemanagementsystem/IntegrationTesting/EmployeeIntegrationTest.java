package org.example.employeemanagementsystem.IntegrationTesting;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.employeemanagementsystem.Model.Employee;
import org.example.employeemanagementsystem.Repo.EmployeeManagementRepo;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class EmployeeIntegrationTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    private Employee employee;

    private int id;

    @Autowired
    private EmployeeManagementRepo repo;

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
    public void Test_Given_EmpObj_whenSaveEmp_WillReturnSavedEmpObj() throws Exception
    {
                ResultActions response=mockMvc.perform(MockMvcRequestBuilders.post("/emp").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(employee)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is("PRUDWI CEO")));

    }


    @Test
    public void Test_Given_EmpIdAndUpdatedEmpObj_when_UpdateEMp_Then_Return_UpdatedEmpObj() throws Exception
    {
        //BDDMockito.given(service.getEmpById(id)).willReturn(employee);

        Employee saved=repo.save(employee);
        saved.setName("PRUDWI CEO@2025");


        //BDDMockito.given(service.updateEmp(ArgumentMatchers.eq(id),ArgumentMatchers.any(Employee.class))).willAnswer(in->in.getArgument(1));

        ResultActions response=mockMvc.perform(MockMvcRequestBuilders.put("/emp/{id}",saved.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name",CoreMatchers.is("PRUDWI CEO@2025")));

    }


    @Test
    public void Test_Given_EmpId_When_GetEmpById_Then_ReturnEmpObj() throws Exception
    {
        //BDDMockito.given(service.getEmpById(id)).willReturn(employee);

        repo.save(employee);

        ResultActions response=mockMvc.perform(MockMvcRequestBuilders.get("/emp/{id}",employee.getId()).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));


        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name",CoreMatchers.is("PRUDWI CEO")));


    }

    @Test
    public void Test_Given_EmpId_When_DeleteById_Then_DeleteEmp() throws Exception
    {

repo.save(employee);
        ResultActions response= mockMvc.perform(MockMvcRequestBuilders.delete("/emp/{id}",employee.getId()));

        response.andExpect(MockMvcResultMatchers.status().isOk());

        //Mockito.verify(service,Mockito.times(1)).DeleteEmpById(id);
    }


    @Test
    public void Given_EmpList_When_GetAllEmp_Then_Return_All_EmpObj() throws Exception
    {
        Employee employee1=new Employee("CEO PRUDWI","PRUDWICEO@Protech.com",39409.9,"CEO","CEO");

        List<Employee> list=new ArrayList<>();
        list.add(employee1);
        list.add(employee);

        repo.saveAll(list);


        //BDDMockito.given(service.getAll()).willReturn(list);


        ResultActions response=mockMvc.perform(MockMvcRequestBuilders.get("/emp/all"));


        response.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.size()",CoreMatchers.is(list.size())));

    }

}
