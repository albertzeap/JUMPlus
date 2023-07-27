package com.cognixia.jump.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.cognixia.jump.model.Classroom;
import com.cognixia.jump.model.Student;
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
	public Map<Student, Integer> getStudentsInClass(int classId);
	
	// Select class by Id
	public Optional<Classroom> getClassById(int classId);
	
	// Select class by subject
	public Optional<Classroom> getClassBySubject(String subject);
	
	// Create a class
	public boolean createClass(Classroom newClass, int teacherId);
	
	// Link a teacher to a class
	public boolean linkTeacherToClass(int classId, int teacherId);
	
	// Sort by student name
	public Map<Student,Integer> sortByName(int classId);
	
	// Sort by student grades
	public Map<Student,Integer> sortByGrade(int classId);
	
	// Get the class average
	public double getClassAverage(int classId); 
	
	// Get the class median
	public double getClassMedian(int classId);
	
	// Update student grade
	public boolean updateStudentGrade(int classId, int studentId, int grade);
	
	// Get all students
	public List<Student> getAllStudentsNotInClass(int classId);
	
	// Add a student to a class
	public boolean addStudentToClass(int studentId, int classId);
	
	// Remove a student from a class
	public boolean removeStudentFromClass(int studentId, int classId);
}


