package com.cognixia.jump.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.cognixia.jump.connection.BetterConnectionManager;
import com.cognixia.jump.model.Classroom;
import com.cognixia.jump.model.Student;

public class StudentDaoSql implements StudentDao {
	
	private Connection conn;

	@Override
	public void setConnection() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException {
		conn = BetterConnectionManager.getConnection();
	}
	

	@Override
	public boolean studentExists(String username) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Optional<Student> login(String username, String password) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public List<Classroom> viewClasses(int studentId) {
		// TODO Auto-generated method stub
		return null;
	}

}
