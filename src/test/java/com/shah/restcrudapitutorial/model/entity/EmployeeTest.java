package com.shah.restcrudapitutorial.model.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.junit.jupiter.api.Test;

class EmployeeTest {
	
	
	@Test
	void testBuilder() {

		Employee build = Employee.builder()
				.accBalance(null)
				.age(0)
				.birthDate(null)
				.country(null)
				.createdAt(null)
				.designation(null)
				.email(null)
				.firstName(null)
				.gender(null)
				.id(null)
				.lastName(null)
				.build();
		Employee.builder().toString();
		assertThat(build).isNotNull();
		
	}

	@Test
	void testObjectGetterSetter() throws InterruptedException {
	Employee object = new Employee();
		
		object.setAccBalance(null);
		object.setAge(1);
		object.setBirthDate(null);
		object.setCountry(null);
		object.setCreatedAt(null);
		object.setDesignation(null);
		object.setEmail(null);
		object.setFirstName(null);
		object.setGender(null);
		object.setId(null);
		object.setLastName(null);
		object.equals(null);
		object.hashCode();
		object.toString();
		object.equals(new Employee());
		object.equals(object);
		assertThat(object).isNotNull();
		
	}
	
	@Test
	void testEqualsObject() {
		
		Employee object = new Employee();
		Employee object2 = new Employee();
		assertThat(object).isEqualTo(object2).isInstanceOf(object2.getClass());
//		assertTrue(object.equals(object2) && object2.equals(object));
//		assertNotEquals(null,object);
//		assertThat(object.hashCode()).hasSameHashCodeAs(object2);
//		 Map<Employee, String> map = new HashMap<>();
//		    map.put(object, "dummy");
//		    assertEquals("dummy",map.get(object));
	}
	
	
}
