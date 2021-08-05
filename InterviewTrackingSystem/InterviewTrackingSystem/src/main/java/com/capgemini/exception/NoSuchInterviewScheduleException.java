package com.capgemini.exception;

public class NoSuchInterviewScheduleException extends RuntimeException
{
	public NoSuchInterviewScheduleException(String message) 
	{
		super(message);
	}
}
