package com.shah.restcrudapitutorial.controller;

import com.shah.restcrudapitutorial.model.entity.Employee;
import com.shah.restcrudapitutorial.service.EmployeeService;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

  
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private EmployeeService employeeService;

	@Mock
	private Employee employee;

  @Test
  void testDeleteEmployee() {

  }

  @Test
  void testGetAllCountryCount() {

  }

  @Test
  void testGetAllEmployees() {

  }

  @Test
  void testGetOneCountryCount() {

  }

  @Test
  void testGetOneEmployee() {

  }

  @Test
  void testNewEmployee() {

  }

  @Test
  void testPatchEmployee() {

  }
}
