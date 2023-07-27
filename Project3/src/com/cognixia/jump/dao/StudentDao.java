package com.cognixia.jump.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;

import com.cognixia.jump.model.Classroom;
import com.cognixia.jump.model.Student;

public interface StudentDao {
	
	public void setConnection() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException;
	
	// Get student by username
	public boolean studentExists(String username);
	
	// Login to account
	public Optional<Student> login(String username, String password);
	
	// View classes student is in
	public Map<Classroom, Integer> viewClasses(int studentId);
	
	
	
}
