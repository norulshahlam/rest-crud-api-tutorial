package com.shah.restcrudapitutorial.controller;

import java.util.List;

import com.shah.restcrudapitutorial.model.entity.Employee;
import com.shah.restcrudapitutorial.service.EmployeeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;

@Data
@RestController
public class EmployeeController {

    @Autowired
    private final EmployeeService employeeService;

    @GetMapping("/all-employees")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        System.out.println(222);

        return new ResponseEntity<List<Employee>>(employeeService.getAllEmployees(),HttpStatus.OK);
    }

}
