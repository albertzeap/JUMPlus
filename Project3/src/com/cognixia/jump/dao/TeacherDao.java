package com.cognixia.jump.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.cognixia.jump.model.Classroom;
import com.cognixia.jump.model.Teacher;

public interface TeacherDao {
	
	public void setConnection() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException;

	// Get teacher by username
	public boolean teacherExists(String username);
	
	// Register for new account
	public boolean register(Teacher newTeacher);
	
	// Login to account 
	public Optional<Teacher> login(String username, String password);

	// View classes
	public List<Classroom> viewClasses(int teacherId);
	
	// Select class and show students in class
	public Optional<Classroom> getStudentsInClass(int classId);
	
	// Select class by Id
	public Optional<Classroom> getClassById(int classId);
	
	// Select class by subject
	public Optional<Classroom> getClassBySubject(String subject);
	
	// Create a class
	public boolean createClass(Classroom newClass, int teacherId);
	
	// Link a teacher to a class
	public boolean linkTeacherToClass(int classId, int teacherId);
	
	public void sortByName(int classId);
	
	public void sortByGrade(int classId);
	
}


