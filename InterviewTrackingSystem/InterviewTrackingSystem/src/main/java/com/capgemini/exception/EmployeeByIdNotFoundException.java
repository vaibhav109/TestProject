package com.capgemini.exception;

public class EmployeeByIdNotFoundException extends RuntimeException {
	private String errorMessage;

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public EmployeeByIdNotFoundException() {

	}

	public EmployeeByIdNotFoundException(String errorMessage) {
		super();
		this.errorMessage = errorMessage;
	}

}
