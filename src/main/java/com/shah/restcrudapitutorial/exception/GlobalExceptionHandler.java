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
    public ResponseEntity<?> handleEmptyResultDataAccessException(HttpServletRequest r,
            EmptyResultDataAccessException s) {

        HttpStatus notFound = HttpStatus.NOT_FOUND;

        Message messsage = new Message(s.getLocalizedMessage(), 
                notFound, 
                r.getRequestURI(), 
                r.getMethod(),
                ZoneId.systemDefault(), 
                ZonedDateTime.now(ZoneId.systemDefault()));
        return new ResponseEntity<Object>(messsage, notFound);
    }

    // ID NOT FOUND DURING GET OR DELETE
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<?> handleNoSuchElementException(HttpServletRequest r, NoSuchElementException s) {

        HttpStatus notFound = HttpStatus.NOT_FOUND;

        Message messsage = new Message(s.getLocalizedMessage(), 
                notFound, 
                r.getRequestURI(), 
                r.getMethod(),
                ZoneId.systemDefault(), 
                ZonedDateTime.now(ZoneId.systemDefault()));

        return new ResponseEntity<Object>(messsage, notFound);
    }

    
    // HIBERNATE VALIDATION FAILED DURING RESOURCE CREATION
    @ExceptionHandler(MyConstraintViolationException.class)
    public ResponseEntity<?> myConstraintViolationException(MyConstraintViolationException e, HttpServletRequest r) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        List<String> messageArray = new ArrayList<String>();
        for (Object object : e.result.getAllErrors()) {
            FieldError fieldError = (FieldError) object;
            messageArray.add(fieldError.getDefaultMessage());
        }
        Messages messsages = new Messages(messageArray, 
                status, 
                r.getRequestURI(), 
                r.getMethod(),
                ZoneId.systemDefault(), 
                ZonedDateTime.now(ZoneId.systemDefault()));

        return new ResponseEntity<Object>(messsages, status);
    }

    // ERROR OCCUR WHEN WRITING TO DB DUE TO CONSTRAINT
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> myDataIntegrityViolationException(HttpServletRequest r,
            DataIntegrityViolationException e) {

        HttpStatus status = HttpStatus.BAD_GATEWAY;

        Message messsage = new Message(e.getMostSpecificCause().getMessage(), 
                status, 
                r.getRequestURI(),
                r.getMethod(),
                ZoneId.systemDefault(), 
                ZonedDateTime.now(ZoneId.systemDefault()));
        return new ResponseEntity<Object>(messsage, status);
    }

    // SCHEMA / TABLE NOT FOUND
    @ExceptionHandler(InvalidDataAccessResourceUsageException.class)
    public ResponseEntity<?> myInvalidDataAccessResourceUsageException(HttpServletRequest r,
            InvalidDataAccessResourceUsageException s) {

        HttpStatus status = HttpStatus.BAD_REQUEST;

        Message messsage = new Message(s.toString(), 
                status, 
                r.getRequestURI(), 
                r.getMethod(), 
                ZoneId.systemDefault(),
                ZonedDateTime.now(ZoneId.systemDefault()));
                
        return new ResponseEntity<Object>(messsage, status);
    }
    // OTHER UNEXPECTED ERRORS
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> otherException(Exception e) {

        HttpStatus status = HttpStatus.SERVICE_UNAVAILABLE;
        return new ResponseEntity<Object>(e.getMessage(), status);
    }
}

@Data
@AllArgsConstructor
class Message {
    private final String message;
    private final HttpStatus status;
    private final String path;
    private final String method;
    private final ZoneId region;
    private final ZonedDateTime timestamp;
}

@Data
@AllArgsConstructor
class Messages {
    private final List<String> message;
    private final HttpStatus status;
    private final String path;
    private final String method;
    private final ZoneId region;
    private final ZonedDateTime timestamp;
}
