package com.cognixia.jump.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.cognixia.jump.dao.StudentDao;
import com.cognixia.jump.dao.StudentDaoSql;
import com.cognixia.jump.dao.TeacherDao;
import com.cognixia.jump.dao.TeacherDaoSql;
import com.cognixia.jump.model.Student;
import com.cognixia.jump.model.Teacher;
import com.cognixia.jump.util.ConsoleColors;

public class GradeBookController {
	
	public static boolean getTeacherByUsername(String username) {
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
	
	public static boolean getStudentByUsername(String username) {
		StudentDao studentDao = new StudentDaoSql();
		boolean exists = false;
		
		try {
			studentDao.setConnection();
			exists = studentDao.studentExists(username);
			
			
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
		
		return exists;
	}
	
	public static boolean createTeacher(int id, String username, String password, String name) {
		
		Teacher teacher = new Teacher(id, username, password, name);
		boolean created = false;
		TeacherDao teacherDao = new TeacherDaoSql();
		
		try {
			
			teacherDao.setConnection();
			created = teacherDao.register(teacher);
			
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
		
		return created;
		
	}
	
	public static List<Object> loginUser(String username, String password){
		
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
		
		
		
		return users;
		
		
	}
}
