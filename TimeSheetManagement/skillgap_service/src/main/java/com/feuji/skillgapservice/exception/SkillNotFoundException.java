package com.feuji.skillgapservice.exception;

public class SkillNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public SkillNotFoundException(String message, Exception exception) {
		super(message, exception);
	}

	public SkillNotFoundException(String message) {
		super(message);
	}
}
