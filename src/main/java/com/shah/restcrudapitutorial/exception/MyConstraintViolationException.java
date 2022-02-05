package com.shah.restcrudapitutorial.exception;

import org.springframework.validation.BindingResult;

import lombok.AllArgsConstructor;

// CUSTOM EXCEPTION 
@AllArgsConstructor
public class MyConstraintViolationException extends RuntimeException {
    
	private static final long serialVersionUID = -4197133332567581277L;
	
	BindingResult result;
}
