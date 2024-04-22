package com.feuji.timesheetentryservice.exception;

public class HolidayDateExistsException extends RuntimeException
{
	public HolidayDateExistsException()
	{
		super();
	}
	public HolidayDateExistsException(String message)
	{
		super(message);
	}

}