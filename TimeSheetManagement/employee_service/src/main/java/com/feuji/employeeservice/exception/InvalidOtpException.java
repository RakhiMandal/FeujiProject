package com.feuji.employeeservice.exception;

public class InvalidOtpException  extends RuntimeException{
	
	public InvalidOtpException(String message) {
		super(message);
	}
	
	public InvalidOtpException(String message, Throwable throwable) {
		super(message, throwable);
	}

}