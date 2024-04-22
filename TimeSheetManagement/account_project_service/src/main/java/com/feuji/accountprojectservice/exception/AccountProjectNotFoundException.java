package com.feuji.accountprojectservice.exception;

public class AccountProjectNotFoundException extends Exception {
	String message;

	public AccountProjectNotFoundException() {

	}

	public AccountProjectNotFoundException(String message) {
		super();
		this.message = message;
	}

}
