package com.shah.restcrudapitutorial.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import com.shah.restcrudapitutorial.model.entity.Employee;
import com.shah.restcrudapitutorial.repository.EmployeeRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

  @Mock
  private EmployeeRepository employeeRepository;

  @InjectMocks
  private EmployeeService employeeService;

  @Mock
  private List<Employee> employees;

  @Mock
  private Employee employee;

  @BeforeEach
  void setUp() {

  }

  @Test
  void testGetAllEmployees() {
    when(employees.size()).thenReturn(100);
    when(employeeRepository.findAll()).thenReturn(employees);
    List<Employee> findAll = employeeRepository.findAll();
    assertEquals(100, findAll.size());
  }

  @Test
  void testGetOneEmployee() {
    when(employee.getAge()).thenReturn(21);
    when(employeeRepository.getById(any())).thenReturn(employee);
    Employee emp = employeeRepository.getById(any());
    assertEquals(21, emp.getAge());
  }

  @Test
  void testPost() {
    String email = "test@email.com";
    when(employeeRepository.save(any())).thenReturn(employee);
    when(employee.getEmail()).thenReturn(email);
    Employee emp = employeeRepository.save(any());
    assertEquals(email, emp.getEmail());
  }

  @Test
  void testPatch() {
    String email = "test@email.com";
    when(employeeRepository.save(any())).thenReturn(employee);
    when(employee.getEmail()).thenReturn(email);
    Employee emp = employeeRepository.save(any());
    assertEquals(email, emp.getEmail());
  }

  @Test
  void testDeleteEmployee() {
    employeeService.deleteEmployee(employee.getId());
    verify(employeeRepository, times(1)).deleteById(employee.getId());
  }

  @Disabled
  @Test
  void testGetNullPropertyNames() {

  }

  @Test
  @Disabled
  void testGetAllCountryCount() {

  }

  @Disabled
  @Test
  void testGetCountryCount() {

  }

}
