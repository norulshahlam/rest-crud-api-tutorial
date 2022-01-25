package com.shah.restcrudapitutorial.repository;


import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.shah.restcrudapitutorial.model.CountryCount;
import com.shah.restcrudapitutorial.model.entity.Employee;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmployeeRepositoryTest {

  @Autowired
  private EmployeeRepository employeeRepository;

  @Test
  @Order(1)
  public void addEmployee() {

    Employee employee1 = Employee.builder().email(randomString() + "@email.com").firstName(randomString()).lastName(randomString()).gender("male").age(randomInt()).country("Singapore").birthDate(new Date(1987 - 03 - 29)).build();

    Employee employee2 = Employee.builder().email(randomString() + "@email.com").firstName(randomString()).lastName(randomString()).gender("female").age(randomInt()).country("Singapore").birthDate(new Date(1987 - 03 - 29)).build();

    Employee employee3 = Employee.builder().email(randomString() + "@email.com").firstName(randomString()).lastName(randomString()).gender("male").age(randomInt()).country("Singapore").birthDate(new Date(1987 - 03 - 29)).build();
    
    Employee employee4 = Employee.builder().email(randomString() + "@email.com").firstName(randomString()).lastName(randomString()).gender("male").age(randomInt()).country("Singapore").birthDate(new Date(1987 - 03 - 29)).build();

    employeeRepository.saveAll(List.of(employee1, employee2, employee3, employee4));
  }

  
	@Test
	void testExistsByEmail() {
		// given
		// data inserted in test case above
		// when
		boolean expected = employeeRepository.existsByEmail("rdolling0@techcrunch.com");
		// then
		assertThat(expected).isTrue();
	}

  @Test
	void testGetCountryCount() {
		final String JAPAN = "Japan";
		List<CountryCount> expected = employeeRepository.getOneCountryCount(JAPAN);

		// JAPAN WILL HAVE A COUNT OF 3
		long count = expected.stream().map(e -> e.getCount()).count();

		assertThat(count).isGreaterThan(0);
	}



  private int randomInt() {
    return ThreadLocalRandom.current().nextInt(21, 55 + 1);
  }

  private String randomString() {
    return RandomStringUtils.randomAlphabetic(6);
  }

}
