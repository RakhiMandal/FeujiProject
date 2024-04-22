package com.feuji.timesheetentryservice.exception;

public class WeekNotFoundException  extends RuntimeException{
	
	String message;
	public WeekNotFoundException()
	{
		
	}
	public WeekNotFoundException(String message) {
		super();
		this.message = message;
	}
	

}
