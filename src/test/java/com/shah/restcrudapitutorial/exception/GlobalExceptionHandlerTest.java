package com.shah.restcrudapitutorial.exception;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;

class GlobalExceptionHandlerTest {
	
	@Mock
	private Message message;

	@Mock
	private HttpStatus httpStatus;
	
	@InjectMocks
	private GlobalExceptionHandler exceptionHandler;

	@Mock
	private HttpServletRequest request;

	@Mock
	private EmptyResultDataAccessException error;
	
	
	@Test
	void testHandleEmptyResultDataAccessException() {
		
	assertThrows(EmptyResultDataAccessException.class, ()->{
		exceptionHandler.handleEmptyResultDataAccessException(request, error);
	});
		
//		ResponseEntity<?> handleEmptyResultDataAccessException = exceptionHandler.handleEmptyResultDataAccessException(request, error);
//		assertThat(handleEmptyResultDataAccessException.getBody()).isNotNull();
	}

	@Test
	@Disabled
	void testHandleNoSuchElementException() {
		fail("Not yet implemented");
	}
	
	@Disabled
	@Test
	void testMyConstraintViolationException() {
		fail("Not yet implemented");
	}
	
	@Disabled
	@Test
	void testMyDataIntegrityViolationException() {
		fail("Not yet implemented");
	}
	
	@Disabled
	@Test
	void testMyInvalidDataAccessResourceUsageException() {
		fail("Not yet implemented");
	}
	
	@Disabled
	@Test
	void testOtherException() {
		fail("Not yet implemented");
	}

}
