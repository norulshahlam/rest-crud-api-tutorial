package com.shah.restcrudapitutorial.controller;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import com.shah.restcrudapitutorial.model.CountryCount;
import com.shah.restcrudapitutorial.model.FieldCount;
import com.shah.restcrudapitutorial.model.dto.EmployeeDto;
import com.shah.restcrudapitutorial.model.entity.Employee;
import com.shah.restcrudapitutorial.model.request.EmployeePatch;
import com.shah.restcrudapitutorial.model.response.OneEmployeeResponse;
import com.shah.restcrudapitutorial.service.EmployeeService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.Data;


@RequestMapping("/crud-api")
@Data
@RestController
@ApiResponses(value = {
		@ApiResponse(code = 201, message = "Created"),
		@ApiResponse(code = 302, message = "Found"),
		@ApiResponse(code = 404, message = "Not found"),
		@ApiResponse(code = 400, message = "Bad Request"),
		@ApiResponse(code = 500, message = "Service Unavaliable")})

public class EmployeeController {

    @Autowired
    private final EmployeeService employeeService;

    @GetMapping("/all-employees")
    @ApiOperation(value = "Get all employees", response = Employee.class, tags = "Get all employees")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return new ResponseEntity<>(employeeService.getAllEmployees(), HttpStatus.OK);
    }
    @ApiOperation(value = "Retrieve one employee", response = OneEmployeeResponse.class, tags = "Retrieve one employee")
    @GetMapping("/one-employee/{id}")
    public ResponseEntity<OneEmployeeResponse> getOneEmployee(
    		@ApiParam(defaultValue = "001d846e-4488-4ecc-84c2-9b6f1d130711")
    		@PathVariable UUID id) {
            return new ResponseEntity<>
            (employeeService.getOneEmployee(id), HttpStatus.OK);
    }
 
    @ApiOperation(value = "Add employee", response = OneEmployeeResponse.class, tags = "Add employee")
    @PostMapping("/create-employee")
    public ResponseEntity<OneEmployeeResponse> newEmployee(
    		@Valid @RequestBody EmployeeDto employeeDto, BindingResult result) {

        return new ResponseEntity<>(employeeService.post(employeeDto, result),HttpStatus.CREATED);
    }

    @ApiOperation(value = "Edit employee", response = OneEmployeeResponse.class, tags = "Edit employee")
    @PatchMapping("/employee/{id}")
    public ResponseEntity<OneEmployeeResponse> patchEmployee(
    		@PathVariable UUID id, @Valid @RequestBody EmployeePatch fields,
            BindingResult result) {
                return new ResponseEntity<>( employeeService.patch(id, fields, result), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Delete employee", tags = "Delete employee")
	@DeleteMapping("/employee/{id}")
	public ResponseEntity<Object> deleteEmployee(@PathVariable UUID id) {
		return new ResponseEntity<>(employeeService.deleteEmployee(id), HttpStatus.OK);
	}
    
	
    @ApiOperation(value = "Count each country", response = FieldCount.class, tags = "Count each country")
	@GetMapping("/employees/count/countries")
	public ResponseEntity<List<FieldCount>> getAllCountryCount() {
		return new ResponseEntity<>(employeeService.getAllCountryCount(), HttpStatus.FOUND);
	}

	
    // COUNT NUMBER OF A COUNTRY
    @ApiOperation(value = "Count one country", response = CountryCount.class, tags = "Count one country")
    @GetMapping("/employees/count/{field}")
	public ResponseEntity<List<CountryCount>> getOneCountryCount(@PathVariable String field) {
		return new ResponseEntity<>(employeeService.getOneCountryCount(field), HttpStatus.FOUND);
	}
	
}
