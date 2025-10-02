package org.example.employeemanagementsystem.controller;

import org.example.employeemanagementsystem.Exception.DepartmentException;
import org.example.employeemanagementsystem.Exception.EmployeeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerAdviceClass {

    @ExceptionHandler(EmployeeException.class)
    public ResponseEntity<?> EmployeeException(EmployeeException e)
    {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DepartmentException.class)
    public ResponseEntity<?> DepartmentException(DepartmentException e)
    {
        return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
    }
}
