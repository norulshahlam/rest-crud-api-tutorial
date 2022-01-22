package com.shah.restcrudapitutorial.service;


import java.util.List;

import com.shah.restcrudapitutorial.model.entity.Employee;
import com.shah.restcrudapitutorial.repository.EmployeeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class EmployeeService {

    @Autowired
    private final EmployeeRepository empRepo;

    // GET ALL
    public List<Employee> getAllEmployees() {
      return  empRepo.findAll();
    }
}
// PROB - CANT FIND A DYNAMIC METHOD TO MAP ALL FIELDS DYNAMICALLY. CURRENTLY
// CANT MAP DATE, BIGINT.
// PROB - CANT DECLARE EMPLOYEE ON TOP, NEED TO HAVE BEAN
// WHY PUT CHECKS FOR UNIQUE EMAIL, BUT PATCH DOESNT?