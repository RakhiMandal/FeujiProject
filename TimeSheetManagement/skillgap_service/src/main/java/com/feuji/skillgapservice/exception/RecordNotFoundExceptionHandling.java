package com.feuji.skillgapservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class RecordNotFoundExceptionHandling {
	@ExceptionHandler(value = RecordNotFoundException.class)
	public ResponseEntity<String> exception(RecordNotFoundException exception)
	{
	log.error("RecordNotFoundException- "+exception.getMessage(),exception);
	return new ResponseEntity<>(exception.getMessage(),HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<String> exception(Exception exception)
	{
	log.error("RecordNotFoundException- "+exception.getMessage(),exception);
	return new ResponseEntity<>(exception.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
