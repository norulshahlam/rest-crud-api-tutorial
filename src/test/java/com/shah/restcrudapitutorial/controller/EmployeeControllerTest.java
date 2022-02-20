package com.shah.restcrudapitutorial.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shah.restcrudapitutorial.model.entity.Employee;
import com.shah.restcrudapitutorial.model.response.OneEmployeeResponse;
import com.shah.restcrudapitutorial.service.EmployeeService;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

  @Autowired
  private MockMvc mockMvc;
  private ObjectMapper objectMapper = new ObjectMapper();

  @MockBean
  private EmployeeService employeeService;
  private OneEmployeeResponse oneEmployeeResponse;
  private List<Employee> employees;
  private Employee employee;

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
    employee = employee1;
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

    when(employeeService.getOneEmployee(uuid)).thenReturn(oneEmployeeResponse);

    mockMvc.perform(get("/one-employee/{uuid}", uuid)
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.employee.id")
            .value(uuid.toString()));
  }

  @Test
  void testNewEmployee() throws Exception {

    when(employeeService.post(any(Employee.class), any(BindingResult.class)))
        .thenReturn(oneEmployeeResponse);
        String content = objectMapper.writeValueAsString(employee);

    mockMvc.perform(post("/create-employee")
        .contentType(MediaType.APPLICATION_JSON)
        .content(content))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.employee.id").exists());
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
