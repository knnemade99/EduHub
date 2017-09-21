package com.eduhub.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity(name="authtable")
public class AuthTable  {

	@OneToOne
	@JoinColumn(name="userId")
	private User user;
	
	@Id
	@Column(name="authToken")
	private String authToken;
	
	public String getAuthToken() {
		return authToken;
	}
	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@Override
	public String toString() {
		return "AuthTable [user=" + user + ", authToken=" + authToken + "]";
	}

	
}