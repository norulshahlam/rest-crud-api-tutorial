package com.shah.restcrudapitutorial.exception;

import org.springframework.validation.BindingResult;

import lombok.AllArgsConstructor;

// CUSTOM EXCEPTION 
@AllArgsConstructor
public class MyConstraintViolationException extends RuntimeException {
    BindingResult result;
}
