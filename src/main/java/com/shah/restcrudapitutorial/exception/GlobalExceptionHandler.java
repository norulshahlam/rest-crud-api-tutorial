package com.shah.restcrudapitutorial.exception;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.AllArgsConstructor;
import lombok.Data;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // EMPTY RESULT
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<Object> handleEmptyResultDataAccessException(HttpServletRequest r,
            EmptyResultDataAccessException error) {

        HttpStatus notFound = HttpStatus.NOT_FOUND;

        Message messsage = new Message(error.getLocalizedMessage(), 
                notFound, 
                r.getRequestURI(), 
                r.getMethod(),
                ZoneId.systemDefault(), 
                ZonedDateTime.now(ZoneId.systemDefault()));
        return new ResponseEntity<>(messsage, notFound);
    }

    // ID NOT FOUND DURING GET OR DELETE
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Object> handleNoSuchElementException(HttpServletRequest r, NoSuchElementException s) {

        HttpStatus notFound = HttpStatus.NOT_FOUND;

        Message messsage = new Message(s.getLocalizedMessage(), 
                notFound, 
                r.getRequestURI(), 
                r.getMethod(),
                ZoneId.systemDefault(), 
                ZonedDateTime.now(ZoneId.systemDefault()));

        return new ResponseEntity<>(messsage, notFound);
    }

    
    // HIBERNATE VALIDATION FAILED DURING RESOURCE CREATION
    @ExceptionHandler(MyConstraintViolationException.class)
    public ResponseEntity<Object> myConstraintViolationException(MyConstraintViolationException e, HttpServletRequest r) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        List<String> messages = new ArrayList<>();
        for (Object object : e.result.getAllErrors()) {
            FieldError fieldError = (FieldError) object;
            messages.add(fieldError.getDefaultMessage());
        }
        Messages messsages = new Messages(messages, 
                status, 
                r.getRequestURI(), 
                r.getMethod(),
                ZoneId.systemDefault(), 
                ZonedDateTime.now(ZoneId.systemDefault()));

        return new ResponseEntity<>(messsages, status);
    }
 
    // ERROR OCCUR WHEN WRITING TO DB DUE TO CONSTRAINT
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> myDataIntegrityViolationException(HttpServletRequest r,
            DataIntegrityViolationException e) {

        HttpStatus status = HttpStatus.BAD_GATEWAY;

        Message messsage = new Message(e.getMostSpecificCause().getMessage(), 
                status, 
                r.getRequestURI(),
                r.getMethod(),
                ZoneId.systemDefault(), 
                ZonedDateTime.now(ZoneId.systemDefault()));
        return new ResponseEntity<>(messsage, status);
    }

    // SCHEMA / TABLE NOT FOUND
    @ExceptionHandler(InvalidDataAccessResourceUsageException.class)
    public ResponseEntity<Object> myInvalidDataAccessResourceUsageException(HttpServletRequest r,
            InvalidDataAccessResourceUsageException s) {

        HttpStatus status = HttpStatus.BAD_REQUEST;

        Message messsage = new Message(s.toString(), 
                status, 
                r.getRequestURI(), 
                r.getMethod(), 
                ZoneId.systemDefault(),
                ZonedDateTime.now(ZoneId.systemDefault()));
                
        return new ResponseEntity<>(messsage, status);
    }
    // OTHER UNEXPECTED ERRORS
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> otherException(Exception e) {

        HttpStatus status = HttpStatus.SERVICE_UNAVAILABLE;
        return new ResponseEntity<>(e.getMessage(), status);
    }
}

@Data
@AllArgsConstructor
class Message {
    private final String description;
    private final HttpStatus status;
    private final String path;
    private final String method;
    private final ZoneId region;
    private final ZonedDateTime timestamp;
}

@Data
@AllArgsConstructor
class Messages {
    private final List<String> messageList;
    private final HttpStatus status;
    private final String path;
    private final String method;
    private final ZoneId region;
    private final ZonedDateTime timestamp;
}
