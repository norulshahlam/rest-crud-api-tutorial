package com.shah.restcrudapitutorial.model.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class EmployeeTest {

	@Test
	public void testObjectMethod() {
		Employee object = new Employee();
		object.equals(new Employee());
		object.hashCode();
		object.toString();
		assertThat(object).isNotNull();
	}
	@Test
	public void testAll() {
		Employee object = new Employee();
		object.equals(Employee.builder().build());
		assertThat(object).isNotNull();
	}

}
