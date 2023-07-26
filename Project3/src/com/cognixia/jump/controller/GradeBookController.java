package com.cognixia.jump.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import com.cognixia.jump.dao.TeacherDao;
import com.cognixia.jump.dao.TeacherDaoSql;
import com.cognixia.jump.model.Teacher;

public class GradeBookController {
	
	private static TeacherDao teacherDao = new TeacherDaoSql();
	
	public static boolean getTeacherByUsername(String username) {
		
		
		try {
			
			teacherDao.setConnection();
			boolean exists = teacherDao.teacherExists(username);
			
			if(exists) {
				return true;
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean createTeacher(int id, String username, String password, String name) {
		
		Teacher teacher = new Teacher(id, username, password, name);
		boolean created = false;
		
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
	
	
}
