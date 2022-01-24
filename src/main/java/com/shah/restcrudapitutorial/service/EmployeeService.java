package com.shah.restcrudapitutorial.service;

import java.beans.FeatureDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import javax.validation.Valid;

import com.shah.restcrudapitutorial.exception.MyConstraintViolationException;
import com.shah.restcrudapitutorial.model.entity.Employee;
import com.shah.restcrudapitutorial.model.request.EmployeePatch;
import com.shah.restcrudapitutorial.model.response.OneEmployeeResponse;
import com.shah.restcrudapitutorial.repository.EmployeeRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
  public OneEmployeeResponse post(@Valid Employee employee, BindingResult result) {

    // BEFORE IT REACHES DB AND TRIGGER ERROR FOR CONSTRAINTS, WE WILL MANUALLY TRIGGER IT.
    if (result.hasErrors()) {
      throw new MyConstraintViolationException(result);
    }
  
    // ID & DATE CREATED IS MANAGED BY JPA. IN CASE USER SETS THIS, REMOVE IT.
    employee.setId(null);
    employee.setCreatedAt(null);

    return new OneEmployeeResponse("Employee created!", empRepo.save(employee));
  }

  // PATCH
  public OneEmployeeResponse patch(UUID id, @Valid EmployeePatch fields, BindingResult result)
      throws IllegalAccessException, InvocationTargetException {

    Employee employee = empRepo.findById(id).get();
    // BEFORE IT REACHES DB AND TRIGGER ERROR FOR CONSTRAINTS, WE WILL MANUALLY  TRIGGER IT.
    if (result.hasErrors()) {
      throw new MyConstraintViolationException(result);
    }
    BeanUtils.copyProperties(fields, employee, getNullPropertyNames(fields));
    return new OneEmployeeResponse("Employee updated!", empRepo.save(employee));
  }

  // DELETE
	public String deleteEmployee(UUID id) {
		empRepo.deleteById(id);
		return "User with ID " + id + " deleted!";
	}
  
	public ResponseEntity<?> getAllCountryCount() {
		return new ResponseEntity<Object>(empRepo.getAllCountryCount(), HttpStatus.OK);
	}

	public ResponseEntity<?> getOneCountryCount(String field) {
		return new ResponseEntity<Object>(empRepo.getOneCountryCount(field), HttpStatus.OK);
	}

  // GET PROPERTY HAVING NULL VALUES
  public static String[] getNullPropertyNames(Object source) {
    final BeanWrapper wrappedSource = new BeanWrapperImpl(source);
    return Stream.of(wrappedSource.getPropertyDescriptors())
        .map(FeatureDescriptor::getName)
        .filter(propertyName -> wrappedSource.getPropertyValue(propertyName) == null)
        .toArray(String[]::new);
  }
}