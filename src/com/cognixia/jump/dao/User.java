package com.cognixia.jump.dao;

public class User {
	
	// User attributes
	private int userId;
	private String email;
	private String password;
	private int roleType;
	
	public User() {
		this.userId = -1;
		this.email = null;
		this.password = null;
		this.roleType = -1;
	}
	
	public User(int userId, String username, String password, int roleType) {
		super();
		this.userId = userId;
		this.email = username;
		this.password = password;
		this.roleType = roleType;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getRoleType() {
		return roleType;
	}

	public void setRoleType(int roleType) {
		this.roleType = roleType;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", email=" + email + ", password=" + password + ", roleType=" + roleType
				+ "]";
	}
	
}
