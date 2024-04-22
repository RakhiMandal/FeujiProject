package com.feuji.accountprojectservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class AccountProjectTaskExceptionHandler {
	@ExceptionHandler(value = IdNotFoundException.class)
	public ResponseEntity<String> exception(IdNotFoundException exception) {
		log.error("Invalid id-" + exception.getMessage(), exception);
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value = NullPointerException.class)
	public ResponseEntity<String> exception(NullPointerException exception) {
		log.error("ObjectIsEmptyProvidevalues-" + exception.getMessage(), exception);
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
	}

}