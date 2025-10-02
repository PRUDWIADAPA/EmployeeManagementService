package org.example.employeemanagementsystem.controller;

import org.example.employeemanagementsystem.Model.Employee;
import org.example.employeemanagementsystem.Service.EmployeeManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/emp")
public class EmployeeManagementController {

    @Autowired
    private EmployeeManagementService employeeManagementService;

    @PostMapping
    public ResponseEntity<?> addEmp(@RequestBody Employee employee)
    {
        Employee e=employeeManagementService.addEmp(employee);

        return new ResponseEntity<>(e,HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getEmpById(@PathVariable int id)
    {
        return new ResponseEntity<>(employeeManagementService.getEmpById(id),HttpStatus.OK);
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmp(@PathVariable int id,@RequestBody Employee employee)
    {
        return new ResponseEntity<>(employeeManagementService.updateEmp(id,employee),HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmp(@PathVariable int id)
    {
        employeeManagementService.DeleteEmpById(id);
        return new ResponseEntity<>("Emp with id :"+id+" has been successfully deleted",HttpStatus.OK);
    }

    @GetMapping("/d")
    public ResponseEntity<?> getEmpByDept(@RequestParam String dept)
    {
        return new ResponseEntity<>(employeeManagementService.empByDept(dept),HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<?>getAllEmp()
    {
       return new ResponseEntity<>(employeeManagementService.getAll(),HttpStatus.OK);
    }


}
