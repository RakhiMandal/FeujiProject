package com.feuji.accountprojectservice.exception;

public class IdNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;
	public IdNotFoundException(String message) {
		super(message);
	}

	public IdNotFoundException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
