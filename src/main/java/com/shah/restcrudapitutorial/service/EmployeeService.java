package com.shah.restcrudapitutorial.service;

import java.beans.FeatureDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import javax.validation.Valid;

import com.shah.restcrudapitutorial.model.entity.Employee;
import com.shah.restcrudapitutorial.model.request.EmployeePatch;
import com.shah.restcrudapitutorial.model.response.OneEmployeeResponse;
import com.shah.restcrudapitutorial.repository.EmployeeRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class EmployeeService {

  @Autowired
  private final EmployeeRepository empRepo;

  // GET ALL
  public List<Employee> getAllEmployees() {
    return empRepo.findAll();
  }

  // GET 1
  public OneEmployeeResponse getOneEmployee(UUID id) {

    Employee employee = empRepo.findById(id).get();
    return new OneEmployeeResponse("Employee found!", employee);
  }

  // POST
  public ResponseEntity<?> post(@Valid Employee employee, BindingResult result) {

    System.out.println(employee);

    // VALIDATE ENTITY
    if (result.hasErrors()) {
      List<String> message = new ArrayList<String>();
      for (Object object : result.getAllErrors()) {
        FieldError fieldError = (FieldError) object;
        message.add(fieldError.getDefaultMessage());
      }
      return new ResponseEntity<Object>(message, HttpStatus.BAD_REQUEST);
    }
    try {
      // CHECK FOR DUPLICATE EMAIL
      if (empRepo.existsByEmail(employee.getEmail())) {
        return new ResponseEntity<Object>("Email exists. Use another email", HttpStatus.BAD_REQUEST);
      }
      // OTHER ERRORS
    } catch (Exception e) {
      return new ResponseEntity<Object>(e.getMessage(),
          HttpStatus.SERVICE_UNAVAILABLE);
    }

    // ID & DATE CREEATED IS MANAGED BY JPA. IN CASE USER SETS THIS, REMOVE IT.
    employee.setId(null);
    employee.setCreatedAt(null);

    // IN CASE THERE'S ERROR IN CREATING NEW RESOURCES
    try {
      return new ResponseEntity<Object>(empRepo.save(employee), HttpStatus.CREATED);
    } catch (Exception e) {
      System.out.println(e);
      return new ResponseEntity<Object>(e.getLocalizedMessage(), HttpStatus.SERVICE_UNAVAILABLE);
    }
  }

  public Employee patch(UUID id, @Valid EmployeePatch fields, BindingResult result)
      throws IllegalAccessException, InvocationTargetException {

    Employee employee = empRepo.findById(id).get();

    BeanUtils.copyProperties(fields, employee, getNullPropertyNames(fields));
    System.out.println(employee);
    Employee updatedEmployee = empRepo.save(employee);

    return updatedEmployee;
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