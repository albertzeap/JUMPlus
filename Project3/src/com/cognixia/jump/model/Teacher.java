package com.cognixia.jump.model;

public class Teacher {
	
	private int teacherId;
	private String username; 
	private String password;
	private String name;
	
	public Teacher() {
		
	}

	public Teacher(int teacherId, String username, String password, String name) {
		super();
		this.teacherId = teacherId;
		this.username = username;
		this.password = password;
		this.name = name;
	}

	public int getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Teacher [teacherId=" + teacherId + ", username=" + username + ", password=" + password + ", name="
				+ name + "]";
	}
	
	
}
