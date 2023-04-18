package com.cognixia.jump.application;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cognixia.jump.dao.TeacherDao;
import com.cognixia.jump.dao.TeacherDaoSql;
import com.cognixia.jump.model.Teacher;

public class StudentGradeBookMenu {
	
	
	static public void run() {
		
		Scanner scan = new Scanner(System.in);
		
		
		int firstMenuChoice = landingMenu(scan);
		if(firstMenuChoice == 1) {
			registerMenu(scan);
		} else if (firstMenuChoice == 2) {
			
		}
		
		
	}
	
	static public int landingMenu (Scanner scan) {
		int firstMenuChoice = 0;
		
		while(true) {
			System.out.println("====================================");
			System.out.println("Welcome to Grade Book!");
			System.out.println("====================================\n");

			System.out.println("Please select an option below:\n");

			System.out.println("1. Register");
			System.out.println("2. Login\n");
			
			firstMenuChoice = scan.nextInt();
			
			if(firstMenuChoice == 1 || firstMenuChoice == 2) {
				return firstMenuChoice;
			}
		}
	}
	
	static public void registerMenu(Scanner scan) {
		
		while(true) {
			System.out.println("====================================");
			System.out.println("Register for Grade Book");
			System.out.println("====================================\n");
			
			
			TeacherDao teacherDao = new TeacherDaoSql();
			String username = null;
			String password = null;
			String name = null;
			
			try {
				
				// Username validation 
				boolean exists = true;
				while (exists) {
					System.out.println("Please enter your username:");
					username = scan.next();
					
					// Check if username already exists
					teacherDao.setConnection();
					exists = teacherDao.teacherExists(username);
					if(exists) {
						System.out.println("This username is taken. Please enter another one\n");
					}
				}
				
				// Password validation
				boolean matches = false;
				while(!matches) {
					System.out.println("Please enter a password:");
					password = scan.next();
					System.out.println("Please confirm your password:");
					String confirm = scan.next();
					scan.nextLine();
					if(!password.equals(confirm)) {
						System.out.println("Passwords do not match\n");
					} else {
						break;
					}
				}
				
				// Name validation
				String nameRegex = "[A-Z][a-z]+\s[A-Z][a-z]+";
				Pattern namePattern = Pattern.compile(nameRegex);
				
				boolean validName = false;
				while(!validName) {
					System.out.println("Please enter your name:");
					name = scan.nextLine();
					
					Matcher nameMatcher = namePattern.matcher(name);
					if(!nameMatcher.matches()) {
						System.out.println("Invalid name entered. Please use the following format: Firstname Lastname\n");
					} else {
						break;
					}
				}
				
				Teacher teacher = new Teacher(0, username, password, name);
				boolean created = teacherDao.register(teacher);
				if(!created) {
					throw new Exception("Could not create user\n");
				} else {
					System.out.println("User successfully created!");
					break;
				}
				
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			
			
		}
	}
	
}
