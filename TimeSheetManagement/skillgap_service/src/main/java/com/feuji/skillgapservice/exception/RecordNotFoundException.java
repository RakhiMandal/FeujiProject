package com.feuji.skillgapservice.exception;

public class RecordNotFoundException extends Exception{
	private static final long serialVersionUID = -8807717109116125437L;
	public RecordNotFoundException(String message)
	{
		super(message);
	}
	public RecordNotFoundException(String message,Throwable throwable)
	{
		super(message,throwable);
	}
}
