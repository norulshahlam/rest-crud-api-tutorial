package com.shah.restcrudapitutorial.repository;

import java.util.UUID;

import com.shah.restcrudapitutorial.model.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
  boolean existsByEmail(String email);
}
