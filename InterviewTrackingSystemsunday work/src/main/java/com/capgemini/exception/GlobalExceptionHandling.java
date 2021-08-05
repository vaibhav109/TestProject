package com.capgemini.exception;

import java.util.Map;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javassist.tools.web.BadHttpRequest;

@ControllerAdvice
public class GlobalExceptionHandling extends ResponseEntityExceptionHandler  {
	
	//Handled Custom EmployeeNotFound Exception
	public ResponseEntity<String> handleEmployeeNotFoundException(EmployeeNotFoundException e)
	{
		return new ResponseEntity<String>("Employee with the given Name not found", HttpStatus.NOT_FOUND);
	}
	
	//Handled Inbuilt No Such Element Exception
	public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException e)
	{
		return new ResponseEntity<String>("Entered Value not present, Please change your Request",HttpStatus.NOT_FOUND);
	}
	
	//Handled Method Not Supported Exception
	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) 
	{
		return new ResponseEntity<Object>("Please change http method type", HttpStatus.NOT_FOUND);
	}
	
	//Handled Inbuilt NullPointer Exception
	public ResponseEntity<String> handleNullPointerException(NullPointerException e)
	{
		return new ResponseEntity<String>("Null Value Found", HttpStatus.BAD_REQUEST);
	}
	
	//Handled No handler found Exception
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		return new ResponseEntity<Object>("Please Enter the Correct Input",HttpStatus.BAD_REQUEST);
	}

}
	


