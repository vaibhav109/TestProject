package com.capgemini.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("employee")
@Scope(scopeName = "prototype")
@Entity
@Table(name = "EMPLOYEE") // Explicitly setting table name
public class Employee 
{
	// Primary Key for EMPLOYEE is EMPLOYEE_ID
	@Id
	@Column(name = "EMPLOYEE_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int employeeId;

	@Column(name = "EMPLOYEE_NAME", length = 50, nullable = false)
	@Pattern(regexp = "^[a-zA-Z ]+$", message = "Please Enter Valid Name.")
	private String employeeName;

	@Column(name = "DEPARTMENT", length = 30, nullable = false)
	@Pattern(regexp = "^[a-zA-Z ]+$", message = "Please Enter Valid Department.")
	private String department;

	@Column(name = "DESIGNATION", length = 30, nullable = false)
	@Pattern(regexp = "^[a-zA-Z ]+$", message = "Please Enter Valid Designation.")
	private String designation;

	public Employee() {

	}

	public Employee(int employeeId, String employeeName, String department, String designation) {
		super();
		this.employeeId = employeeId;
		this.employeeName = employeeName;
		this.department = department;
		this.designation = designation;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	@Override
	public String toString() {
		return "Employee [employeeId=" + employeeId + ", employeeName=" + employeeName + ", department=" + department
				+ ", designation=" + designation + "]";
	}

}
