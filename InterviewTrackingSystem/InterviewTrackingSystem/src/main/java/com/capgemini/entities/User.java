package com.capgemini.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(scopeName="prototype")
@Entity
@Table(name="LOGIN_TBL")
public class User {
	
	@Id
	@Column(name="PASSWORD")
	private String password;
	
	@Column(name="USERNAME")
	private String username;
	
	public User() {
		
	}
	
	public User(String password, String username) {
		super();
		this.password = password;
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "User [password=" + password + ", username=" + username + "]";
	}
	
	
	
}
