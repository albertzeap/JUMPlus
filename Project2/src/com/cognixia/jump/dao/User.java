package com.cognixia.jump.dao;

public class User {
	
	// User attributes
	private int userId;
	private String email;
	private String password;
	private String name;
	private int roleType;
	
	public User() {
		this.userId = -1;
		this.email = null;
		this.password = null;
		this.name = null;
		this.roleType = -1;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public User(int userId, String email, String password, String name, int roleType) {
		super();
		this.userId = userId;
		this.email = email;
		this.password = password;
		this.name = name;
		this.roleType = roleType;
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
		return "User [userId=" + userId + ", email=" + email + ", password=" + password + ", name=" + name
				+ ", roleType=" + roleType + "]";
	}
	
}
