package com.cognixia.jump.model;

public class Student {
	
	private int studentId; 
	private String username; 
	private String password;
	private String name;
	
	public Student() {
		
	}

	public Student(int studentId, String username, String password, String name) {
		super();
		this.studentId = studentId;
		this.username = username;
		this.password = password;
		this.name = name;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
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
		return "Student [studentId=" + studentId + ", username=" + username + ", password=" + password + ", name="
				+ name + "]";
	}
	
	
}
