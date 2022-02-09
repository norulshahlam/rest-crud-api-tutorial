package com.shah.restcrudapitutorial.model.entity;

import org.junit.jupiter.api.Test;

class EmployeeTest {
	
	@Test
	public void testObjectMethod() {
		Employee object = new Employee();
		object.equals(new Employee());
		object.hashCode();
		object.toString();
	}
	@Test
	public void testAll() {
		Employee object = new Employee();
		object.equals(Employee.builder().build());
	}
}
