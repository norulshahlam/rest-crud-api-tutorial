package com.shah.restcrudapitutorial.service;

import java.beans.FeatureDescriptor;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import javax.validation.Valid;

import com.shah.restcrudapitutorial.exception.MyConstraintViolationException;
import com.shah.restcrudapitutorial.model.CountryCount;
import com.shah.restcrudapitutorial.model.FieldCount;
import com.shah.restcrudapitutorial.model.dto.EmployeeDto;
import com.shah.restcrudapitutorial.model.entity.Employee;
import com.shah.restcrudapitutorial.model.request.EmployeePatch;
import com.shah.restcrudapitutorial.model.response.OneEmployeeResponse;
import com.shah.restcrudapitutorial.repository.EmployeeRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class EmployeeService {

	@Autowired
	private final EmployeeRepository empRepo;

	// GET ALL
	public List<Employee> getAllEmployees() {
		final List<Employee> findAll = empRepo.findAll();
		if (findAll.isEmpty()) {
			throw new EmptyResultDataAccessException("No employee at all. Please insert some.", 1);
		}
		return findAll;
	}

	// GET 1
	public OneEmployeeResponse getOneEmployee(UUID id) {

		Employee employee = empRepo.findById(id).get();
		return new OneEmployeeResponse("Employee found!", employee);
	}

	// POST
	public OneEmployeeResponse post(@Valid EmployeeDto employeeDto, BindingResult result) {

		// BEFORE IT REACHES DB AND TRIGGER ERROR FOR CONSTRAINTS, WE WILL MANUALLY
		// TRIGGER IT.
		if (result.hasErrors()) {
			throw new MyConstraintViolationException(result);
		}
		
		Employee employee = new Employee();
		BeanUtils.copyProperties(employeeDto, employee);
		
		return new OneEmployeeResponse("Employee created!", empRepo.save(employee));
	}

	// PATCH
	public OneEmployeeResponse patch(UUID id, @Valid EmployeePatch fields, BindingResult result)
			 {
		// BEFORE IT REACHES DB AND TRIGGER ERROR FOR CONSTRAINTS, WE WILL MANUALLY
		// TRIGGER IT.
		if (result.hasErrors()) {
			throw new MyConstraintViolationException(result);
		}
		Employee employee = empRepo.findById(id).get();

		BeanUtils.copyProperties(fields, employee, getNullPropertyNames(fields));
		return new OneEmployeeResponse("Employee updated!", empRepo.save(employee));
	}

	// DELETE
	public String deleteEmployee(UUID id) {
		empRepo.deleteById(id);
		return "User with ID " + id + " deleted!";
	}

	public List<FieldCount> getAllCountryCount() {
		return empRepo.getAllCountryCount();
	}

	public List<CountryCount> getOneCountryCount(String country) {
		return empRepo.getOneCountryCount(country);
	}

	// GET PROPERTY HAVING NULL VALUES
	public static String[] getNullPropertyNames(Object source) {
		final BeanWrapper wrappedSource = new BeanWrapperImpl(source);
		return Stream.of(wrappedSource.getPropertyDescriptors()).map(FeatureDescriptor::getName)
				.filter(propertyName -> wrappedSource.getPropertyValue(propertyName) == null).toArray(String[]::new);
	}
}