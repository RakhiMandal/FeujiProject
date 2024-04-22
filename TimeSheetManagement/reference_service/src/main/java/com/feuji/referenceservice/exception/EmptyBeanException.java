package com.feuji.referenceservice.exception;

public class EmptyBeanException extends RuntimeException {
	
	
	private static final long serialVersionUID = 1L;
	
	public EmptyBeanException() {
		super();
	}

	public EmptyBeanException(String message) {
		super(message);
	}
}