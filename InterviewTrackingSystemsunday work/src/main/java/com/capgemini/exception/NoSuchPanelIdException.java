package com.capgemini.exception;

@SuppressWarnings("serial")
public class NoSuchPanelIdException extends RuntimeException
{
	public NoSuchPanelIdException(String msg) 
	{
		super(msg);
	}
}
