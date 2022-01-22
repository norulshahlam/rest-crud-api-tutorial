package com.shah.restcrudapitutorial.repository;

import com.shah.restcrudapitutorial.model.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
  
}
