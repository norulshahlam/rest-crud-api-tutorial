package com.shah.restcrudapitutorial.model.response;

import com.shah.restcrudapitutorial.model.entity.Employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OneEmployeeResponse {
  
  private String message;
  private Employee employee;
}
