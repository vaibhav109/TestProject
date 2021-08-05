package com.capgemini.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("panelMember")
@Scope(scopeName="prototype")
@Entity
@Table(name="PANEL_MEMBER") //Explicitly setting table name
public class PanelMember 
{
	//Primary key for PANEL_MEMBER
	@Id
	@Column(name="PANEL_ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer panelId;
	
	@Column(name="EMPLOYEE_TYPE", length=30, nullable=false)
	@Pattern(regexp="^[a-zA-Z ]+$",message="Please Enter Valid Type.")
	private String employeeType;
	
	@Column(name="LOCATION", length=50)
	@Pattern(regexp="^[a-zA-Z ]+$",message="Please Enter Valid Location.")
	private String location;
	
	@Column(name = "DELETE_STATUS",length=10)
	private boolean deleteStatus = Boolean.FALSE;
	
	//OneToOne Unidirectional
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="EMPLOYEE_ID")
	@Autowired
	private Employee employee;
	
	//Inverse Class
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="CANDIDATE_ID")
	@Autowired
	private Candidate candidate;

	public Candidate getCandidate() {
		return candidate;
	}

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public PanelMember()
	{
		
	}

	public PanelMember(Integer panelId,
			@Pattern(regexp = "^[a-zA-Z ]+$", message = "Please Enter Valid Type.") String employeeType,
			@Pattern(regexp = "^[a-zA-Z ]+$", message = "Please Enter Valid Location.") String location,
			boolean deleteStatus) {
		super();
		this.panelId = panelId;
		this.employeeType = employeeType;
		this.location = location;
		this.deleteStatus = deleteStatus;
	}

	public Integer getPanelId() {
		return panelId;
	}

	public void setPanelId(Integer panelId) {
		this.panelId = panelId;
	}

	public String getEmployeeType() {
		return employeeType;
	}

	public void setEmployeeType(String employeeType) {
		this.employeeType = employeeType;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public boolean getDeleteStatus() {
		return deleteStatus;
	}

	public void setDeleteStatus(boolean deleteStatus) {
		this.deleteStatus = deleteStatus;
	}

	@Override
	public String toString() {
		return "PanelMember [panelId=" + panelId + ", employeeType=" + employeeType + ", location=" + location
				+ ", deleteStatus=" + deleteStatus + "]";
	}

	
	
}
