package com.feuji.referenceservice.exception;

public class ReferenceNotFoundException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;

    public ReferenceNotFoundException() {
        super();
    }
    public ReferenceNotFoundException(String message) {
        super(message);
    }
	
}