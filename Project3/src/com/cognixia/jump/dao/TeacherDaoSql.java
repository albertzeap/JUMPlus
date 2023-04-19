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
import com.cognixia.jump.model.Teacher;
import com.cognixia.jump.util.ConsoleColors;

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
			System.out.println(ConsoleColors.ANSI_RED + e.getMessage() + ConsoleColors.ANSI_RESET);
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
			System.out.println(ConsoleColors.ANSI_RED + e.getMessage() + ConsoleColors.ANSI_RESET);
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
			System.out.println(ConsoleColors.ANSI_RED + e.getMessage() + ConsoleColors.ANSI_RESET);
		}
		return Optional.empty();
	}

	@Override
	public List<Classroom> viewClasses(int teacherId) {
		
		try(PreparedStatement pstmnt = conn.prepareStatement("SELECT c.classId, subject FROM classroom c JOIN teaches t ON c.classId = t.classId WHERE t.teacherId = ?")) {
			
			pstmnt.setInt(1, teacherId);
			
			ResultSet rs = pstmnt.executeQuery();
			
			int classId = 0; 
			String subject = null;
			List<Classroom> classes = new ArrayList<>();
			
			while(rs.next()) {
				classId = rs.getInt("c.classId");
				subject = rs.getString("subject");
				
				if(subject == null) {
					return classes;
				} else {
					classes.add(new Classroom(classId, subject));
				}
				
				
			}
			
			return classes;
			
			
		} catch (Exception e) {
			System.out.println(ConsoleColors.ANSI_RED + e.getMessage() + ConsoleColors.ANSI_RESET);
		}
		
		
		return null;
	}

	@Override
	public Optional<Classroom> getStudentsInClass(int classId) {
		
		try(PreparedStatement pstmnt = conn.prepareStatement("SELECT s.studentId, s.name, e.grade FROM student s JOIN enrolled e ON s.studentId = e.studentId WHERE classId = ?")) {
			
			pstmnt.setInt(1, classId);
			ResultSet rs = pstmnt.executeQuery();
			
			int studentId = 0;
			String name = null;
			int grade = 0;
			
			System.out.printf("%-10s%-20s%-10s%n", "StudentID", "Name", "Grade");
			System.out.println();

			while(rs.next()) {
				
				studentId = rs.getInt("s.studentId");
				name = rs.getString("s.name");
				grade = rs.getInt("e.grade");
				
				System.out.printf("%-10d%-20s%-10d%n", studentId, name, grade);
			}
			System.out.println();
			
			
		} catch (Exception e) {
			System.out.println(ConsoleColors.ANSI_RED + e.getMessage() + ConsoleColors.ANSI_RESET);
		}
		
		
		return Optional.empty();
	}
	
	@Override
	public Optional<Classroom> getClassBySubject(String subject) {
		
		try(PreparedStatement pstmnt = conn.prepareStatement("SELECT * FROM classroom WHERE subject = ?")){
			
			pstmnt.setString(1, subject);
			ResultSet rs = pstmnt.executeQuery();
			
			int classId = 0;
			String newSubject = null;
			
			while(rs.next()) {
				classId = rs.getInt("classId");
				newSubject = rs.getString("subject");
			}
			
			if(newSubject == null) {
				return Optional.empty();
			}
			
			Classroom classroom = new Classroom(classId, newSubject);
			Optional<Classroom> found = Optional.of(classroom);
			return found;
			
		} catch(Exception e) {
			System.out.println(ConsoleColors.ANSI_RED + e.getMessage() + ConsoleColors.ANSI_RESET);
		}
		return Optional.empty();
	}

	@Override
	public boolean createClass(Classroom newClass, int teacherId) {
		
		try(PreparedStatement pstmnt = conn.prepareStatement("INSERT INTO classroom VALUES (null,?)")) {
			
			pstmnt.setString(1, newClass.getSubject());
			int count = pstmnt.executeUpdate();
			
			if(count > 0) {
				return true;
			}
			
			
		} catch (Exception e) {
			System.out.println(ConsoleColors.ANSI_RED + e.getMessage() + ConsoleColors.ANSI_RESET);
		}
		
		
		return false;
	}

	@Override
	public boolean linkTeacherToClass(int classId, int teacherId) {
	try(PreparedStatement pstmnt = conn.prepareStatement("INSERT INTO teaches VALUES (?,?)")) {
			
			pstmnt.setInt(1, teacherId);
			pstmnt.setInt(2, classId);
			int count = pstmnt.executeUpdate();
			
			if(count > 0) {
				return true;
			}
			
			
		} catch (Exception e) {
			System.out.println(ConsoleColors.ANSI_RED + e.getMessage() + ConsoleColors.ANSI_RESET);
		}
		return false;
	}

	@Override
	public Optional<Classroom> getClassById(int classId) {
		try(PreparedStatement pstmnt = conn.prepareStatement("SELECT * FROM classroom WHERE classId = ?")){
			
			pstmnt.setInt(1, classId);
			ResultSet rs = pstmnt.executeQuery();
			
			int newClassId = 0;
			String newSubject = null;
			
			while(rs.next()) {
				newClassId = rs.getInt("classId");
				newSubject = rs.getString("subject");
			}
			
			if(newSubject == null) {
				return Optional.empty();
			}
			
			Classroom classroom = new Classroom(newClassId, newSubject);
			Optional<Classroom> found = Optional.of(classroom);
			return found;
			
		} catch(Exception e) {
			System.out.println(ConsoleColors.ANSI_RED + e.getMessage() + ConsoleColors.ANSI_RESET);
		}
		return Optional.empty();
	}


}
