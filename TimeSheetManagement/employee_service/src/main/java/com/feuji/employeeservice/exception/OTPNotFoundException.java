package com.feuji.employeeservice.exception;

public class OTPNotFoundException extends RuntimeException {
    public OTPNotFoundException(String message) {
        super(message);
    }
}