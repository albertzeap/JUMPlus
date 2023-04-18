package com.cognixia.jump.model;

public class Classroom {

	private int classId;
	private String subject;
	
	
	public Classroom() {
		
	}
	
	public Classroom(int classId, String subject) {
		super();
		this.classId = classId;
		this.subject = subject;
	}

	public int getClassId() {
		return classId;
	}

	public void setClassId(int classId) {
		this.classId = classId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	@Override
	public String toString() {
		return "Class [classId=" + classId + ", subject=" + subject + "]";
	}
	
	
}
