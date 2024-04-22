package com.feuji.employeeservice.exception;

public class PasswordUpdateException extends RuntimeException{

	private static final long serialVersionUID = 2071516219514763125L;

	public PasswordUpdateException(String message) {
		super(message);
	}
	
	public PasswordUpdateException(String message,Throwable throwable) {
		super(message,throwable);
	}
}
