package com.feuji.timesheetentryservice.exception;

public class HolidayNameExistException extends RuntimeException
{
 public HolidayNameExistException() {
	 super();
 }
 public HolidayNameExistException(String message) {
	 super(message);
 }
}