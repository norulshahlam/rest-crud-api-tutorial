package com.shah.restcrudapitutorial.controller;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import com.shah.restcrudapitutorial.model.CountryCount;
import com.shah.restcrudapitutorial.model.entity.Employee;
import com.shah.restcrudapitutorial.model.request.EmployeePatch;
import com.shah.restcrudapitutorial.model.response.OneEmployeeResponse;
import com.shah.restcrudapitutorial.service.EmployeeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
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
        return new ResponseEntity<>(employeeService.getAllEmployees(), HttpStatus.OK);
    }

    @GetMapping("/one-employee/{id}")
    public ResponseEntity<OneEmployeeResponse> getOneEmployee(@PathVariable UUID id) {
            return new ResponseEntity<>
            (employeeService.getOneEmployee(id), HttpStatus.OK);
    }
 
    @PostMapping("/create-employee")
    public ResponseEntity<OneEmployeeResponse> newEmployee(@Valid @RequestBody Employee employee, BindingResult result) {

        return new ResponseEntity<>(employeeService.post(employee, result),HttpStatus.CREATED);
    }

    @PatchMapping("/employee/{id}")
    public ResponseEntity<OneEmployeeResponse> patchEmployee(@PathVariable UUID id, @Valid @RequestBody EmployeePatch fields,
            BindingResult result) {
                return new ResponseEntity<>( employeeService.patch(id, fields, result), HttpStatus.CREATED);
    }

    
	@DeleteMapping("/employee/{id}")
	public ResponseEntity<Object> deleteEmployee(@PathVariable UUID id) {
		return new ResponseEntity<>(employeeService.deleteEmployee(id), HttpStatus.OK);
	}
    
	
    // COUNT NUMBER OF EACH COUNTRY PRESENT
	@GetMapping("/employees/count/countries")
	public ResponseEntity<Object> getAllCountryCount() {
		return new ResponseEntity<>(employeeService.getAllCountryCount(), HttpStatus.FOUND);
	}

	
    // COUNT NUMBER OF A COUNTRY
	@GetMapping("/employees/count/{field}")
	public ResponseEntity<List<CountryCount>> getOneCountryCount(@PathVariable String field) {
		return new ResponseEntity<>(employeeService.getOneCountryCount(field), HttpStatus.FOUND);
	}
	
}
