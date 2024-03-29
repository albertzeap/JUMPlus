package com.cognixia.jump.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.cognixia.jump.connection.BetterConnectionManager;
import com.cognixia.jump.model.Classroom;
import com.cognixia.jump.model.Student;
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
		
		List<Classroom> classes = new ArrayList<>();
		
		try(PreparedStatement pstmnt = conn.prepareStatement("SELECT c.classId, subject FROM classroom c JOIN teaches t ON c.classId = t.classId WHERE t.teacherId = ? ORDER BY c.classId")) {
			
			pstmnt.setInt(1, teacherId);
			
			ResultSet rs = pstmnt.executeQuery();
			
			int classId = 0; 
			String subject = null;
			
			while(rs.next()) {
				classId = rs.getInt("c.classId");
				subject = rs.getString("subject");
				
				if(subject == null) {
					return classes;
				} else {
					classes.add(new Classroom(classId, subject));
				}
				
				
			}
			
		} catch (Exception e) {
			System.out.println(ConsoleColors.ANSI_RED + e.getMessage() + ConsoleColors.ANSI_RESET);
		}
		
		
		return classes;
	}

	@Override
	public Map<Student, Integer> getStudentsInClass(int classId) {
		
		Map<Student, Integer> students = new LinkedHashMap<>();
		
		try(PreparedStatement pstmnt = conn.prepareStatement("SELECT s.studentId, s.username, s.password, s.name, e.grade FROM student s JOIN enrolled e ON s.studentId = e.studentId WHERE classId = ?")) {
			
			pstmnt.setInt(1, classId);
			ResultSet rs = pstmnt.executeQuery();
			

			while(rs.next()) {
				
				int studentId = rs.getInt("s.studentId");
				String username = rs.getString("username");
				String password = rs.getString("password");
				String name = rs.getString("s.name");
				int grade = rs.getInt("e.grade");
				
				students.put(new Student(studentId, username, password, name), grade);
				
			}
			rs.close();
			
			System.out.println();
			
		} catch (Exception e) {
			System.out.println(ConsoleColors.ANSI_RED + e.getMessage() + ConsoleColors.ANSI_RESET);
		}
		
		
		return students;
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

	@Override
	public Map<Student, Integer> sortByName(int classId) {
		
		Map<Student, Integer> students = new LinkedHashMap<>();
		
		try(PreparedStatement pstmnt = conn.prepareStatement("SELECT s.studentId, s.username, s.password, s.name, e.grade FROM student s JOIN enrolled e ON s.studentId = e.studentId WHERE classId = ? ORDER BY s.name")) {
			
			pstmnt.setInt(1, classId);
			ResultSet rs = pstmnt.executeQuery();
			
//			System.out.printf(ConsoleColors.YELLOW_UNDERLINED + "%-10s%-20s%-10s%n", "StudentID", "Name", "Grade" + ConsoleColors.ANSI_RESET);
//			System.out.println();

			while(rs.next()) {
				
				int studentId = rs.getInt("s.studentId");
				String username = rs.getString("s.username");
				String password = rs.getString("s.password");
				String name = rs.getString("s.name");
				int grade = rs.getInt("e.grade");
				
//				System.out.printf("%-10d%-20s%-10d%n", studentId, name, grade);
				students.put(new Student(studentId, username, password, name), grade);
				
				
			}
			System.out.println();
			
			
		} catch (Exception e) {
			System.out.println(ConsoleColors.ANSI_RED + e.getMessage() + ConsoleColors.ANSI_RESET);
		}
	
		return students;
	}

	@Override
	public Map<Student, Integer> sortByGrade(int classId) {
		
		Map<Student, Integer> students = new LinkedHashMap<>();
		
		try(PreparedStatement pstmnt = conn.prepareStatement("SELECT s.studentId, s.username, s.password, s.name, e.grade FROM student s JOIN enrolled e ON s.studentId = e.studentId WHERE classId = ? ORDER BY e.grade DESC")) {
			
			pstmnt.setInt(1, classId);
			ResultSet rs = pstmnt.executeQuery();
			
//			System.out.printf(ConsoleColors.YELLOW_UNDERLINED + "%-10s%-20s%-10s%n", "StudentID", "Name", "Grade" + ConsoleColors.ANSI_RESET);
//			System.out.println();

			while(rs.next()) {
				
				int studentId = rs.getInt("s.studentId");
				String username = rs.getString("s.username");
				String password = rs.getString("s.password");
				String name = rs.getString("s.name");
				int grade = rs.getInt("e.grade");
				
//				System.out.printf("%-10d%-20s%-10d%n", studentId, name, grade);
				students.put(new Student(studentId, username, password, name), grade);
			}
			System.out.println();
			
			
		} catch (Exception e) {
			System.out.println(ConsoleColors.ANSI_RED + e.getMessage() + ConsoleColors.ANSI_RESET);
		}
		
		return students;
	}

	@Override
	public double getClassAverage(int classId) {
		double average = -1.0;
		
		try(PreparedStatement pstmnt = conn.prepareStatement("SELECT  ROUND(AVG(e.grade), 1) AS avg_grade FROM student s JOIN enrolled e ON s.studentId = e.studentId WHERE classId = ?")) {
			
			pstmnt.setInt(1, classId);
			ResultSet rs = pstmnt.executeQuery();
			while(rs.next()) {
				average = rs.getDouble("avg_grade");
			}
			
			if(average == -1.0) {
				throw new Exception(ConsoleColors.ANSI_RED + "Could not get average" + ConsoleColors.ANSI_RESET);
			}
			
			
		} catch (Exception e) {
			System.out.println(ConsoleColors.ANSI_RED + e.getMessage() + ConsoleColors.ANSI_RESET);
		}
		
		return average;
	}

	@Override
	public double getClassMedian(int classId) {
		double median = 0.0;
		List<Double> grades = new ArrayList<>();
		
		try(PreparedStatement pstmnt = conn.prepareStatement("SELECT e.grade FROM student s JOIN enrolled e ON s.studentId = e.studentId WHERE classId = ? ORDER BY e.grade DESC;")) {
			
			pstmnt.setInt(1, classId);
			ResultSet rs = pstmnt.executeQuery();
			double grade = 0.0;
			while(rs.next()) {
				grade = rs.getDouble("e.grade");
				grades.add(grade);
			}
			
			
			if(grades.isEmpty()) {
				throw new Exception(ConsoleColors.ANSI_RED + "Could not retrieve class median" + ConsoleColors.ANSI_RESET);
			}
			
			if(grades.size() % 2 == 0) {			
				Double high = grades.get(grades.size() / 2);
				Double low = grades.get((grades.size() / 2) - 1);
				median = (high + low) / 2;
				
			} else {
				median = grades.get(grades.size() / 2);
			}
			
			
			
		} catch (Exception e) {
			System.out.println(ConsoleColors.ANSI_RED + e.getMessage() + ConsoleColors.ANSI_RESET);
		}
		
		return median;
	}

	@Override
	public boolean updateStudentGrade(int classId, int studentId, int grade) {
		
		try(PreparedStatement pstmnt = conn.prepareStatement("UPDATE enrolled SET grade = ? WHERE classId = ? AND studentId = ? ")) {
			
			pstmnt.setInt(1, grade);
			pstmnt.setInt(2, classId);
			pstmnt.setInt(3, studentId);
			
			int count = pstmnt.executeUpdate();
			
			if(count < 0) {
				throw new Exception(ConsoleColors.ANSI_RED + "Could not update" + ConsoleColors.ANSI_RESET);
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
		
		
		return true;
	}

	@Override
	public List<Student> getAllStudentsNotInClass(int classId) {
		
		List<Student> students = new ArrayList<>();
		
		try(PreparedStatement pstmnt = conn.prepareStatement("SELECT s.* FROM student s LEFT JOIN enrolled e ON s.studentId = e.studentId AND e.classId = ? WHERE e.classId IS NULL;")) {			
			
			pstmnt.setInt(1, classId);
			ResultSet rs = pstmnt.executeQuery();
			
			
			int studentId = 0;
			String username = null;
			String password = null;
			String name = null;
			
			while(rs.next()) {
				
				studentId = rs.getInt("studentId");
				username = rs.getString("username");
				password = rs.getString("password");
				name = rs.getString("name");
				
				Student student = new Student(studentId, username, password, name);
				
				students.add(student);
			}
			
			
			
		} catch (Exception e) {
			System.out.println(ConsoleColors.ANSI_RED + e.getMessage() + ConsoleColors.ANSI_RESET);
		}
		
		return students;
	}

	@Override
	public boolean addStudentToClass(int studentId, int classId) {
		
		try(PreparedStatement pstmnt = conn.prepareStatement("INSERT INTO enrolled (studentId, classId) VALUES (?,?)")) {
			
			pstmnt.setInt(1, studentId);
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
	public boolean removeStudentFromClass(int studentId, int classId) {
		
		try(PreparedStatement pstmnt = conn.prepareStatement("DELETE FROM enrolled WHERE studentId = ? AND classId = ?")) {
			
			pstmnt.setInt(1, studentId);
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


}
