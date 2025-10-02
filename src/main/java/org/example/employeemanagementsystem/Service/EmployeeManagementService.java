package org.example.employeemanagementsystem.Service;

import org.example.employeemanagementsystem.Exception.DepartmentException;
import org.example.employeemanagementsystem.Exception.EmployeeException;
import org.example.employeemanagementsystem.Model.Employee;
import org.example.employeemanagementsystem.Repo.EmployeeManagementRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeManagementService {

    @Autowired
    private EmployeeManagementRepo employeeManagementRepo;

    public Employee getEmpById(int id)
    {
        Optional<Employee> employee=employeeManagementRepo.findById(id);

        if(employee.isEmpty())
        {
            throw new EmployeeException("Employee with id: "+ id+" Not found");
        }

        return employee.get();
    }


    public Employee getEmpByEmail(String email)
    {

       Optional<Employee>employee= employeeManagementRepo.findByEmail(email);

       if (employee.isEmpty())
       {
           throw new EmployeeException("Employee with Email: "+email+" Not found");
       }

       return employee.get();
    }


    public Employee addEmp(Employee employee) {
        Optional<Employee> emp=employeeManagementRepo.findByEmail(employee.getEmail());
        if(emp.isPresent())
        {
            throw new EmployeeException("Employee with email: "+employee.getEmail()+" already existing");
        }

        Optional<Employee>empWithId=employeeManagementRepo.findById(employee.getId());

        if (empWithId.isPresent())
        {
            throw new EmployeeException("Employee with id: "+employee.getId()+" is already existing");
        }

        return employeeManagementRepo.save(employee);
    }


    public Employee updateEmp(int id,Employee employee)
    {
        Optional<Employee>SavedEmp=employeeManagementRepo.findById(id);

        if(SavedEmp.isEmpty())
        {
            throw new EmployeeException("Employee with id :"+id+" Not found to update");
        }

        Employee UpdatedEmp=SavedEmp.get();

        UpdatedEmp.setName(employee.getName());
        UpdatedEmp.setDept(employee.getDept());
        UpdatedEmp.setEmail(employee.getEmail());
        UpdatedEmp.setSalary(employee.getSalary());
        UpdatedEmp.setTeam(employee.getTeam());

        return employeeManagementRepo.save(UpdatedEmp);
    }


    public void DeleteEmpById(int id)
    {
        Optional<Employee>SavedEmp=employeeManagementRepo.findById(id);

        if (SavedEmp.isEmpty())
        {
            throw new EmployeeException("Emp with id : "+id+" Not found");
        }

        employeeManagementRepo.deleteById(id);
    }

    public List<Employee>empByDept(String dept)
    {
        List<String>Depts=employeeManagementRepo.findAll().stream().map(emp->emp.getDept()).collect(Collectors.toList());

        if(!Depts.contains(dept))
        {
            throw new DepartmentException("Department: "+dept+"Not Found");
        }

        return employeeManagementRepo.findByDept(dept);
    }


    public List<Employee> getAll() {
        return employeeManagementRepo.findAll();
    }
}
