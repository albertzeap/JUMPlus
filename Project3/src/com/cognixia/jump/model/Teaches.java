package com.cognixia.jump.model;

public class Teaches {
	
	private int teacherId;
	private int classId;
	
	public Teaches() {
		
	}

	public Teaches(int teacherId, int classId) {
		super();
		this.teacherId = teacherId;
		this.classId = classId;
	}

	public int getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}

	public int getClassId() {
		return classId;
	}

	public void setClassId(int classId) {
		this.classId = classId;
	}

	@Override
	public String toString() {
		return "Teaches [teacherId=" + teacherId + ", classId=" + classId + "]";
	}
	
	
}
