package com.feuji.employeeservice.exception;

public class RecordsNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public RecordsNotFoundException(String message) {
		super(message);
	}

	public RecordsNotFoundException(String message, Throwable throwable) {
		super(message, throwable);
	}
}