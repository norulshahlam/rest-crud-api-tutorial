package com.shah.restcrudapitutorial.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import com.shah.restcrudapitutorial.model.entity.Employee;
import com.shah.restcrudapitutorial.model.response.OneEmployeeResponse;
import com.shah.restcrudapitutorial.service.EmployeeService;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private EmployeeService employeeService;
  private OneEmployeeResponse oneEmployeeResponse;
  private List<Employee> employees;

  @BeforeEach
  void setUp() throws Exception {

    Employee employee1 = Employee.builder().email(randomString() + "@email.com").firstName(randomString())
        .lastName(randomString()).gender("male").age(randomInt()).country("Singapore")
        .birthDate(new Date(1987 - 03 - 29)).id(UUID.randomUUID()).build();

    Employee employee2 = Employee.builder().email(randomString() + "@email.com").firstName(randomString())
        .lastName(randomString()).gender("female").age(randomInt()).country("Singapore")
        .birthDate(new Date(1987 - 03 - 29)).id(UUID.randomUUID()).build();

    Employee employee3 = Employee.builder().email(randomString() + "@email.com").firstName(randomString())
        .lastName(randomString()).gender("male").age(randomInt()).country("Singapore")
        .birthDate(new Date(1987 - 03 - 29)).id(UUID.randomUUID()).build();

    Employee employee4 = Employee.builder().email(randomString() + "@email.com").firstName(randomString())
        .lastName(randomString()).gender("male").age(randomInt()).country("Singapore")
        .birthDate(new Date(1987 - 03 - 29)).id(UUID.randomUUID()).build();

    employees = Arrays.asList(employee1, employee2, employee3, employee4);
    oneEmployeeResponse = new OneEmployeeResponse(null, employee1);
  }

  @Test
  void testGetAllEmployees() throws Exception {
    when(employeeService.getAllEmployees()).thenReturn(employees);

    mockMvc.perform(get("/all-employees")
        .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].firstName")
        .exists())
        .andExpect(jsonPath("$.*", hasSize(4)));
  }

  @Test
  void testGetOneEmployee() throws Exception {

    UUID uuid = oneEmployeeResponse.getEmployee().getId();
    String email=oneEmployeeResponse.getEmployee().getEmail();

    when(employeeService.getOneEmployee(uuid)).thenReturn(oneEmployeeResponse);
System.out.println(oneEmployeeResponse);
    mockMvc.perform(get("/one-employee/{uuid}", uuid)
      .contentType(MediaType.APPLICATION_JSON))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.employee.email")
        .value(email));
  }

  @Disabled
  @Test
  void testNewEmployee() {

  }

  @Disabled
  @Test
  void testPatchEmployee() {

  }

  @Disabled
  @Test
  void testDeleteEmployee() {

  }

  @Disabled
  @Test
  void testGetAllCountryCount() {

  }

  @Disabled
  @Test
  void testGetOneCountryCount() {

  }

  private int randomInt() {
    return ThreadLocalRandom.current().nextInt(21, 55 + 1);
  }

  private String randomString() {
    return RandomStringUtils.randomAlphabetic(6);
  }

}
