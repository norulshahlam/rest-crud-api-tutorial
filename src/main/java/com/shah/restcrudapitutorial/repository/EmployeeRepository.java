package com.shah.restcrudapitutorial.repository;

import java.util.List;
import java.util.UUID;

import com.shah.restcrudapitutorial.model.CountryCount;
import com.shah.restcrudapitutorial.model.FieldCount;
import com.shah.restcrudapitutorial.model.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
  boolean existsByEmail(String email);

  
	@Query(value = "SELECT DISTINCT country as field, COUNT(*) as count FROM employee GROUP BY country", nativeQuery = true)
	List<FieldCount> getAllCountryCount();
  
	@Query(value = "SELECT DISTINCT country as country, COUNT(*) as count FROM employee where country=?1 GROUP BY country", nativeQuery = true)
	List<CountryCount> getCountryCount(String country);
}
//  select distinct country as country, count(*) as count from employee where country="japan" group by country;