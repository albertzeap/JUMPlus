package com.cognixia.jump.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.cognixia.jump.dao.StudentDao;
import com.cognixia.jump.dao.StudentDaoSql;
import com.cognixia.jump.dao.TeacherDao;
import com.cognixia.jump.dao.TeacherDaoSql;
import com.cognixia.jump.model.Classroom;
import com.cognixia.jump.model.Student;
import com.cognixia.jump.model.Teacher;

public class GradeBookController {
	
	public boolean getTeacherByUsername(String username) {
		TeacherDao teacherDao = new TeacherDaoSql();
		boolean exists = false;
		
		try {
			
			teacherDao.setConnection();
			exists = teacherDao.teacherExists(username);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return exists;
	}
	
	public boolean getStudentByUsername(String username) {
		StudentDao studentDao = new StudentDaoSql();
		boolean exists = false;
		
		try {
			studentDao.setConnection();
			exists = studentDao.studentExists(username);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return exists;
	}
	
	public boolean createTeacher(int id, String username, String password, String name) {
		
		Teacher teacher = new Teacher(id, username, password, name);
		boolean created = false;
		TeacherDao teacherDao = new TeacherDaoSql();
		
		try {
			
			teacherDao.setConnection();
			created = teacherDao.register(teacher);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return created;
		
	}
	
	public List<Object> loginUser(String username, String password){
		
		TeacherDao teacherDao = new TeacherDaoSql();
		StudentDao studentDao = new StudentDaoSql();
		List<Object> users = new ArrayList<>();
		
		try {
			
			teacherDao.setConnection();
			studentDao.setConnection();
			
			Optional<Teacher> teacher = teacherDao.login(username, password);
			Optional<Student> student = studentDao.login(username, password);
			
			
			if(teacher.isPresent() && student.isEmpty()) {
				users.add(teacher.get());
			} else if(student.isPresent() && teacher.isEmpty()) {
				users.add(student.get());
			} 
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return users;	
	}
	
	public Map<Classroom, Integer> viewStudentClasses(int studentId){
		
		Map<Classroom, Integer> classes = new HashMap<>();
		StudentDao studentDao = new StudentDaoSql();
		
		try {
			
			studentDao.setConnection();
			classes = studentDao.viewClasses(studentId);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return classes;
		
	}
	
	public List<Classroom> viewTeacherClasses(int teacherId){
		
		TeacherDao teacherDao = new TeacherDaoSql();
		List<Classroom> classes = new ArrayList<>();
		
		try {
			
			teacherDao.setConnection();
			classes = teacherDao.viewClasses(teacherId);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return classes;
		
	}
	
	public Optional<Classroom> getClassById(int classId){
		
		TeacherDao teacherDao = new TeacherDaoSql();
		Optional<Classroom> found = Optional.empty();
		
		try {
			teacherDao.setConnection();
			found = teacherDao.getClassById(classId);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return found;
		
	}
	
	public Map<Student, Integer> getStudentsInClass(int classId){
		TeacherDao teacherDao = new TeacherDaoSql();
		Map<Student, Integer> students = new HashMap<>();
		
		try {
			teacherDao.setConnection();
			students = teacherDao.getStudentsInClass(classId);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return students;
		
	}
	
	public List<Double> getClassAverageMedian(int classId) {
		
		TeacherDao teacherDao = new TeacherDaoSql();
		List<Double> averageMedian = new ArrayList<>();
		
		try {
			
			teacherDao.setConnection();
			double average = teacherDao.getClassAverage(classId);
			double median = teacherDao.getClassMedian(classId);
			averageMedian.add(average);
			averageMedian.add(median);
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
		
		return averageMedian;
	}
	
	public Map<Student, Integer> sortByName(int classId){
		
		Map<Student, Integer> students = new LinkedHashMap<>();
		TeacherDao teacherDao = new TeacherDaoSql();
		
		try {
			
			teacherDao.setConnection();
			students = teacherDao.sortByName(classId);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return students;
	}
	
	public Map<Student, Integer> sortByGrade(int classId){
		
		Map<Student, Integer> students = new LinkedHashMap<>();
		TeacherDao teacherDao = new TeacherDaoSql();
		
		try {
			
			teacherDao.setConnection();
			students = teacherDao.sortByGrade(classId);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return students;
	}
	
	public boolean updateStudentGrade(int classId, int studentId, int grade) {
		TeacherDao teacherDao = new TeacherDaoSql();
		boolean updated = false;
		
		try {
			teacherDao.setConnection();
			updated = teacherDao.updateStudentGrade(classId, studentId, grade);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return updated;
	}
	
	public List<Student> getAllStudentsNotInClass(int classId){
		TeacherDao teacherDao = new TeacherDaoSql();
		List<Student> students = new ArrayList<>();
		
		try {
			teacherDao.setConnection();
			students = teacherDao.getAllStudentsNotInClass(classId);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return students;
	}
	
	public boolean addStudentToClass(int studentId, int classId) {
		TeacherDao teacherDao = new TeacherDaoSql();
		boolean added = false;
		
		try {
			teacherDao.setConnection();
			added = teacherDao.addStudentToClass(studentId,classId);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return added;
		
	}
	
	public boolean removeStudentFromClass(int studentId, int classId) {
		TeacherDao teacherDao = new TeacherDaoSql();
		boolean removed = false;
		
		try {
			teacherDao.setConnection();
			removed = teacherDao.removeStudentFromClass(studentId,classId);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return removed;
		
	}
	
	
}
