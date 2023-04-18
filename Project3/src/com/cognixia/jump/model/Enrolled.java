package com.cognixia.jump.model;

public class Enrolled {
	
	private int studentId;
	private int classId;
	private int grade;
	
	public Enrolled() {
		
	}

	public Enrolled(int studentId, int classId, int grade) {
		super();
		this.studentId = studentId;
		this.classId = classId;
		this.grade = grade;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public int getClassId() {
		return classId;
	}

	public void setClassId(int classId) {
		this.classId = classId;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	@Override
	public String toString() {
		return "Enrolled [studentId=" + studentId + ", classId=" + classId + ", grade=" + grade + "]";
	}
	
	
}
