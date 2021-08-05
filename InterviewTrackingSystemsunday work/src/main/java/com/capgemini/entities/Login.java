package com.capgemini.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "LOGIN")
public class Login {
	@Id
	@Column(name = "LOGIN_ID")
	private int loginId;

	@NotEmpty(message = "Password should not empty")
	@Size(min = 8, max = 12, message = "Password must be between 8 and 12 characters long") 
	@Column(name = "PASSWORD",length=12)
	private String password;

	@Column(name = "USER_TYPE" , length = 15)
	private String userType;

	@Column(name = "IS_ACTIVE")
	private boolean isActive;

	public Login() {
		super();
	}

	
	public Login(int loginId,
			@NotEmpty(message = "Password should not empty") @Size(min = 8, max = 12, message = "Password must be between 8 and 12 characters long") String password,
			String userType, boolean isActive) {
		super();
		this.loginId = loginId;
		this.password = password;
		this.userType = userType;
		this.isActive = isActive;
	}


	public int getLoginId() {
		return loginId;
	}

	public void setLoginId(int loginId) {
		this.loginId = loginId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	@Override
	public String toString() {
		return "Login [loginId=" + loginId + ", password=" + password + ", userType=" + userType + ", isActive="
				+ isActive + "]";
	}

	
}
