package com.shah.restcrudapitutorial.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.shah.restcrudapitutorial.exception.MyConstraintViolationException;
import com.shah.restcrudapitutorial.model.CountryCount;
import com.shah.restcrudapitutorial.model.FieldCount;
import com.shah.restcrudapitutorial.model.dto.EmployeeDto;
import com.shah.restcrudapitutorial.model.entity.Employee;
import com.shah.restcrudapitutorial.model.request.EmployeePatch;
import com.shah.restcrudapitutorial.model.response.OneEmployeeResponse;
import com.shah.restcrudapitutorial.repository.EmployeeRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.validation.BindingResult;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

	@Mock
	private EmployeeRepository employeeRepository;

	@InjectMocks
	private EmployeeService employeeService;

	@Mock
	private List<Employee> employees;

	@Mock
	private EmployeePatch fields;

	@Mock
	private Employee employee;

	@Mock
	private BindingResult result;

	@Mock
	private List<FieldCount> allCountryCount;

	@Mock
	private List<CountryCount> oneCountryCount;
	
	@Mock
	private EmployeeDto employeeDto;

	@BeforeEach
	void setUp() {

	}

	@Test
	void testGetAllEmployeesReturnNotEmpty() {
		when(employeeRepository.findAll()).thenReturn(employees);
		List<Employee> allEmployees = employeeService.getAllEmployees();
		assertThat(allEmployees).isNotNull();
	}

	@Test
	void testGetAllEmployeesReturnEmpty() {
		assertThrows(EmptyResultDataAccessException.class, () -> {
			employeeService.getAllEmployees();
		});
	}

	@Test
	void testGetOneEmployee() {
		when(employeeRepository.findById(any())).thenReturn(Optional.of(employee));
		OneEmployeeResponse oneEmployee = employeeService.getOneEmployee(UUID.randomUUID());
		assertThat(oneEmployee.getEmployee()).isNotNull();
	}

	@Test
	void testPostValidationPass() {
		when(result.hasErrors()).thenReturn(false);
		when(employeeRepository.save(any())).thenReturn(employee);
		OneEmployeeResponse post = employeeService.post(employeeDto, result);
		assertThat(post.getEmployee()).isNotNull();
	}

	@Test
	void testPostValidationFailed() {
		when(result.hasErrors()).thenReturn(true);
		assertThrows(MyConstraintViolationException.class, () -> {
			employeeService.post(employeeDto, result);
		});
	}

	@Test
	void testPatchValidationFailed() {
		when(result.hasErrors()).thenReturn(true);
		assertThrows(MyConstraintViolationException.class, () -> {
			employeeService.patch(UUID.randomUUID(), fields, result);
		});
	}

	@Test
	void testPatchValidationPassed() throws IllegalAccessException, InvocationTargetException {
		when(result.hasErrors()).thenReturn(false);
		when(employeeRepository.findById(any())).thenReturn(Optional.of(employee));
		when(employeeRepository.save(any())).thenReturn(employee);
		OneEmployeeResponse patch = employeeService.patch(UUID.randomUUID(), fields, result);
		assertThat(patch.getEmployee()).isNotNull();
	}

	@Test
	void testDeleteEmployee() {
		employeeService.deleteEmployee(employee.getId());
		verify(employeeRepository, times(1)).deleteById(employee.getId());
	}

	@Test
	void testGetAllCountryCount() {
		when(employeeRepository.getAllCountryCount()).thenReturn(allCountryCount);
		List<FieldCount> count = employeeService.getAllCountryCount();
		assertThat(count).isNotNull();
	}

	@Test
	void testGetOneCountryCount() {
		when(employeeRepository.getOneCountryCount(any())).thenReturn(oneCountryCount);
		List<CountryCount> count = employeeService.getOneCountryCount("anything");
		assertThat(count).isNotNull();
	}

}
