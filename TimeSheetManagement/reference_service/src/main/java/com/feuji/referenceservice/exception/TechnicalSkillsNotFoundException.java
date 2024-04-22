package com.feuji.referenceservice.exception;

public class TechnicalSkillsNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public TechnicalSkillsNotFoundException() {
		super();
	}

	public TechnicalSkillsNotFoundException(String message) {
		super(message);
	}
}