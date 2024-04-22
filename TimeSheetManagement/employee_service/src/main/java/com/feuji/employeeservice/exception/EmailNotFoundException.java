package com.feuji.employeeservice.exception;

public class EmailNotFoundException extends RuntimeException {
	public EmailNotFoundException(String message) {
		super(message);
	}
	
	public EmailNotFoundException(String message, Throwable throwable) {
		super(message, throwable);
	}
}