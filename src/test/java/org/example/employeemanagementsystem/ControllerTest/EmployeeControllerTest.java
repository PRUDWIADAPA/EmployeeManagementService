package org.example.employeemanagementsystem.ControllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.jshell.spi.ExecutionControlProvider;
import org.assertj.core.api.Assertions;
import org.example.employeemanagementsystem.Model.Employee;
import org.example.employeemanagementsystem.Repo.EmployeeManagementRepo;
import org.example.employeemanagementsystem.Service.EmployeeManagementService;
import org.example.employeemanagementsystem.controller.EmployeeManagementController;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

@WebMvcTest(EmployeeManagementController.class)
//@ActiveProfiles("test")
public class EmployeeControllerTest {

    @MockitoBean
    private EmployeeManagementService service;

    @Autowired
    private ObjectMapper objectMapper;

    private Employee employee;

    int id;


    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setup()
    {
        employee=new Employee();
        id=1;

        employee.setId(id);
        employee.setName("PRUDWI CEO");
        employee.setEmail("prudwiceo@protech.com");
        employee.setDept("CEO");
        employee.setTeam("CEO");
        employee.setSalary(47589598.97);
    }


    @Test
    public void Test_Given_EmpObj_whenSaveEmp_WillReturnSavedEmpObj() throws Exception
    {
        BDDMockito.given(service.addEmp(ArgumentMatchers.any(Employee.class))).willAnswer(in->in.getArgument(0));


        ResultActions response=mockMvc.perform(MockMvcRequestBuilders.post("/emp").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(employee)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is("PRUDWI CEO")));

    }

    @Test
    public void Test_Given_EmpIdAndUpdatedEmpObj_when_UpdateEMp_Then_Return_UpdatedEmpObj() throws Exception
    {
        BDDMockito.given(service.getEmpById(id)).willReturn(employee);
        employee.setName("PRUDWI CEO@2025");

        BDDMockito.given(service.updateEmp(ArgumentMatchers.eq(id),ArgumentMatchers.any(Employee.class))).willAnswer(in->in.getArgument(1));

        ResultActions response=mockMvc.perform(MockMvcRequestBuilders.put("/emp/{id}",id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name",CoreMatchers.is("PRUDWI CEO@2025")));

    }


    @Test
    public void Test_Given_EmpId_When_GetEmpById_Then_ReturnEmpObj() throws Exception
    {
        BDDMockito.given(service.getEmpById(id)).willReturn(employee);

        ResultActions response=mockMvc.perform(MockMvcRequestBuilders.get("/emp/{id}",id).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));


        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name",CoreMatchers.is("PRUDWI CEO")));



    }

    @Test
    public void Test_Given_EmpId_When_DeleteById_Then_DeleteEmp() throws Exception
    {
       BDDMockito.given(service.getEmpById(id)).willReturn(employee);

       BDDMockito.willDoNothing().given(service).DeleteEmpById(id);

      ResultActions response= mockMvc.perform(MockMvcRequestBuilders.delete("/emp/{id}",id));

       response.andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(service,Mockito.times(1)).DeleteEmpById(id);
    }

    @Test
    public void Given_EmpList_When_GetAllEmp_Then_Return_All_EmpObj() throws Exception
    {
        Employee employee1=new Employee(1,"CEO PRUDWI","PRUDWICEO@Protech.com",39409.9,"CEO","CEO");

        List<Employee>list=new ArrayList<>();
        list.add(employee1);
        list.add(employee);


        BDDMockito.given(service.getAll()).willReturn(list);


        ResultActions response=mockMvc.perform(MockMvcRequestBuilders.get("/emp/all"));


        response.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.size()",CoreMatchers.is(list.size())));

    }



}
