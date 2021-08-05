package com.capgemini.exception;

public class EmployeeNotFoundException extends RuntimeException {
	public String errorMessage;

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public EmployeeNotFoundException() {

	}

	public EmployeeNotFoundException(String errorMessage) {
		super();
		this.errorMessage = errorMessage;
	}

	
}
