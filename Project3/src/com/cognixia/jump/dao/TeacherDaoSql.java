package com.cognixia.jump.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.cognixia.jump.connection.BetterConnectionManager;
import com.cognixia.jump.model.Classroom;
import com.cognixia.jump.model.Teacher;

public class TeacherDaoSql implements TeacherDao {
	
	private Connection conn;
	
	public void setConnection() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException {
		conn = BetterConnectionManager.getConnection();
	}
	
	@Override
	public boolean teacherExists(String username) {
		try(PreparedStatement pstmnt = conn.prepareStatement("SELECT * FROM teacher WHERE username = ?")) {
			
			pstmnt.setString(1, username);
			ResultSet rs = pstmnt.executeQuery();
			
			int teacherId = 0;
			String teacherUsername = null;
			String password = null;
			String name = null;
			
			while(rs.next()) {
				teacherId = rs.getInt("teacherId");
				teacherUsername = rs.getString("username");
				password = rs.getString("password");
				name = rs.getString("name");			
			}
			rs.close();
			
			// Teacher does exist
			if(teacherUsername != null) {
				return true;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
	

	@Override
	public boolean register(Teacher newTeacher) {
		
		try(PreparedStatement pstmnt = conn.prepareStatement("INSERT INTO teacher (username, password, name) values (?,?,?)" )) {
			
			// Check if user exists before attempting the insert 
			boolean exists = teacherExists(newTeacher.getUsername());
			if(exists) {
				throw new Exception("User already exists");
			}
			
			pstmnt.setString(1, newTeacher.getUsername());
			pstmnt.setString(2, newTeacher.getPassword());
			pstmnt.setString(3, newTeacher.getName());
			
			pstmnt.executeUpdate();
			return true;
		
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

	@Override
	public Optional<Teacher> login(String username, String password) {
		
		try(PreparedStatement pstmnt = conn.prepareStatement("SELECT * FROM teacher WHERE username = ? AND password = ?")) {
			
			pstmnt.setString(1, username);
			pstmnt.setString(2, password);
			ResultSet rs = pstmnt.executeQuery();
			
			int teacherId = 0;
			String teacherUsername = null;
			String teacherPassword = null;
			String name = null;
			
			while(rs.next()) {
				teacherId = rs.getInt("teacherId");
				teacherUsername = rs.getString("username");
				teacherPassword = rs.getString("password");
				name = rs.getString("name");
			}
			
			if(teacherUsername != null) {
				Teacher teacher = new Teacher(teacherId, teacherUsername, teacherPassword, name);
				return Optional.of(teacher);
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		
		return Optional.empty();
	}

	@Override
	public List<Classroom> viewClasses(int teacherId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Classroom> getClassById(int classId) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public boolean createClass(Classroom newClass) {
		// TODO Auto-generated method stub
		return false;
	}


}
