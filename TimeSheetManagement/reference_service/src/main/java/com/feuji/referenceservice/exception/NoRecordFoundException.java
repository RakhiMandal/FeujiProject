package com.feuji.referenceservice.exception;

public class NoRecordFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public NoRecordFoundException() {
		super();
	}

	public NoRecordFoundException(String msg) {
		super(msg);
	}
}