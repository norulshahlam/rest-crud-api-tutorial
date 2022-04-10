package com.shah.restcrudapitutorial;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.shah.restcrudapitutorial.controller.EmployeeController;

@SpringBootTest
class RestCrudApiTutorialApplicationTests {

	@Autowired
	private EmployeeController employeeController;
	
	@Test
	void contextLoads() {
		assertThat(employeeController).isNotNull();
	}

}
