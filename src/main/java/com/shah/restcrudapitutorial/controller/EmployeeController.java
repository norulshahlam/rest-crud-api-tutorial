package com.shah.restcrudapitutorial.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import javax.validation.Valid;

import com.shah.restcrudapitutorial.model.entity.Employee;
import com.shah.restcrudapitutorial.model.request.EmployeePatch;
import com.shah.restcrudapitutorial.model.response.OneEmployeeResponse;
import com.shah.restcrudapitutorial.service.EmployeeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import lombok.Data;

@Data
@RestController
public class EmployeeController {

    @Autowired
    private final EmployeeService employeeService;

    @GetMapping("/all-employees")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return new ResponseEntity<List<Employee>>(employeeService.getAllEmployees(), HttpStatus.OK);
    }

    @GetMapping("/one-employee/{id}")
    public ResponseEntity<OneEmployeeResponse> getAllEmployees(@PathVariable UUID id) {

        try {
            return new ResponseEntity<OneEmployeeResponse>(employeeService.getOneEmployee(id), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            OneEmployeeResponse body = new OneEmployeeResponse(e.getMessage(), null);
            return new ResponseEntity<OneEmployeeResponse>(body, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/create-employee")
    public ResponseEntity<?> newEmployee(@Valid @RequestBody Employee employee, BindingResult result) {

        return employeeService.post(employee, result);
    }

    @PatchMapping("/employees/{id}")
    public ResponseEntity<Employee> patchEmployee(@PathVariable UUID id, @Valid @RequestBody EmployeePatch fields,
            BindingResult result) throws IllegalAccessException, InvocationTargetException {
        return new ResponseEntity<Employee>(employeeService.patch(id, fields, result), HttpStatus.OK);
    }
}
