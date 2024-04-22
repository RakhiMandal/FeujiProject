package com.feuji.skillgapservice.exception;

public class InputNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InputNotFoundException(String message) {
		super(message);
	}

	public InputNotFoundException() {
		super();
	}

}