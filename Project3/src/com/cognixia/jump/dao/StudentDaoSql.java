package com.cognixia.jump.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
		
		try(PreparedStatement ps = conn.prepareStatement("SELECT * FROM student WHERE username = ?")) {
			
			ps.setString(1, username);
			
			ResultSet rs = ps.executeQuery();
			Optional<Student> exists = Optional.empty();
			
			while(rs.next()) {
				int id = rs.getInt(1);
				String sUsername = rs.getString(2);
				String password = rs.getString(3);
				String name = rs.getString(4);
				
				exists = Optional.of(new Student(id, sUsername, password, name));
			}
			rs.close();
			
			if(exists.isEmpty()) {
				return false;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return true;
	}

	@Override
	public Optional<Student> login(String username, String password) {
		
		try(PreparedStatement ps = conn.prepareStatement("SELECT * FROM student WHERE username = ? AND password = ?")) {
			
			ps.setString(1, username);
			ps.setString(2, password);
			
			ResultSet rs = ps.executeQuery();
			Optional<Student> student = Optional.empty();
			
			while(rs.next()) {
				int id = rs.getInt(1);
				String studentUsername = rs.getString(2);
				String studentPassword = rs.getString(3);
				String studentName = rs.getString(4);
				
				student = Optional.of(new Student(id, studentUsername, studentPassword, studentName));
			}
			
			return student;
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return Optional.empty();
	}

	@Override
	public List<Classroom> viewClasses(int studentId) {
		
		try(PreparedStatement ps = conn.prepareStatement("SELECT c.classId, c.subject, e.grade  FROM classroom c JOIN enrolled e ON e.classId = c.classId WHERE e.studentId = ?")) {
			
			ps.setInt(1, studentId);
			
			ResultSet rs = ps.executeQuery();
			List<Classroom> classes = new ArrayList<>();
			
			while(rs.next()) {
				int classId = rs.getInt(1);
				String subject = rs.getString(2);
				int grade = rs.getInt(3);
				
				if(subject != null) {
					classes.add(new Classroom(classId, subject));
					System.out.printf("%-8d%-25s%-10d%n", classId, subject, grade);					
				}
				
				return classes;
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return null;
	}

}
