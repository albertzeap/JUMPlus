package com.cognixia.jump.application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cognixia.jump.dao.TeacherDao;
import com.cognixia.jump.dao.TeacherDaoSql;
import com.cognixia.jump.model.Classroom;
import com.cognixia.jump.model.Teacher;
import com.cognixia.jump.util.ConsoleColors;

public class StudentGradeBookMenu {
	
	
	static public void run() {
		
		Scanner scan = new Scanner(System.in);
		Teacher activeUser = null;
		
		
		int firstMenuChoice = landingMenu(scan);
		if(firstMenuChoice == 1) {
			registerMenu(scan);
			firstMenuChoice = landingMenu(scan);
		}
		if (firstMenuChoice == 2) {
			int teacherMenuChoice = 0;
			activeUser = loginMenu(scan);
			teacherMenuChoice = teacherMenu(scan, activeUser);
			
			if(teacherMenuChoice == 1) {
				selectClass(scan);
			}
			
			if(teacherMenuChoice == 2) {
				createClass(scan, activeUser);
			}
			
			if(teacherMenuChoice == 3) {
				System.out.println("Thank You!");
			}
			
			
		}
		
		
	}
	
	static public int landingMenu (Scanner scan) {
		int firstMenuChoice = 0;
		
		while(true) {
			System.out.println(ConsoleColors.ANSI_WHITE_BOLD_BRIGHT + "====================================");
			System.out.println(ConsoleColors.ANSI_WHITE_BOLD_BRIGHT + "Welcome to Grade Book!");
			System.out.println(ConsoleColors.ANSI_WHITE_BOLD_BRIGHT + "====================================\n" + ConsoleColors.ANSI_RESET);

			System.out.println(ConsoleColors.ANSI_ITALIC + "Please select an option below:\n" + ConsoleColors.ANSI_RESET);

			System.out.println("1. Register");
			System.out.println("2. Login");
			System.out.println("3. Exit\n");
			
			
			try {
				System.out.print(ConsoleColors.ANSI_CYAN);
				firstMenuChoice = scan.nextInt();
				System.out.print(ConsoleColors.ANSI_RESET);
				if(firstMenuChoice != 1 && firstMenuChoice != 2 && firstMenuChoice != 3) {
					throw new Exception(ConsoleColors.ANSI_RED + "Invalid Input. Please select an option from above(1-3)" + ConsoleColors.ANSI_RESET);
				}
				return firstMenuChoice;
				
			} catch(InputMismatchException e) { 
				System.out.println(ConsoleColors.ANSI_RED + "Invalid Input. Please select an option from above(1-2)" + ConsoleColors.ANSI_RESET);
				scan.nextLine();
			} catch (Exception e) {
				System.out.println(ConsoleColors.ANSI_RED + e.getMessage() + ConsoleColors.ANSI_RESET);
			}
			
			
		}
	}
	
	static public void registerMenu(Scanner scan) {
		
		while(true) {
			System.out.println(ConsoleColors.ANSI_WHITE_BOLD_BRIGHT +"====================================");
			System.out.println(ConsoleColors.ANSI_WHITE_BOLD_BRIGHT+ "Register for Grade Book");
			System.out.println(ConsoleColors.ANSI_WHITE_BOLD_BRIGHT+ "====================================\n");
			
			
			TeacherDao teacherDao = new TeacherDaoSql();
			String username = null;
			String password = null;
			String name = null;
			
			try {
				
				// Username validation 
				boolean exists = true;
				while (exists) {
					System.out.print(ConsoleColors.ANSI_RESET);
					System.out.println(ConsoleColors.ANSI_ITALIC + "Please enter your username:" + ConsoleColors.ANSI_RESET);
					
					System.out.print(ConsoleColors.ANSI_CYAN);
					username = scan.next();
					System.out.print(ConsoleColors.ANSI_RESET);
					// Check if username already exists
					teacherDao.setConnection();
					exists = teacherDao.teacherExists(username);
					if(exists) {
						System.out.println(ConsoleColors.ANSI_RED + "This username is taken. Please enter another one\n" + ConsoleColors.ANSI_RESET);
					}
				}
				
				// Password validation
				boolean matches = false;
				while(!matches) {
					System.out.println(ConsoleColors.ANSI_ITALIC +"Please enter a password:" + ConsoleColors.ANSI_RESET);
					System.out.print(ConsoleColors.ANSI_CYAN);
					password = scan.next();
					System.out.print(ConsoleColors.ANSI_RESET);
					System.out.println(ConsoleColors.ANSI_ITALIC +"Please confirm your password:"+ ConsoleColors.ANSI_RESET);
					
					System.out.print(ConsoleColors.ANSI_CYAN);
					String confirm = scan.next();
					System.out.print(ConsoleColors.ANSI_RESET);
					
					scan.nextLine();
					if(!password.equals(confirm)) {
						System.out.println(ConsoleColors.ANSI_RED + "Passwords do not match\n" + ConsoleColors.ANSI_RESET);
					} else {
						break;
					}
				}
				
				// Name validation
				String nameRegex = "[A-Z][a-z]+\s[A-Z][a-z]+";
				Pattern namePattern = Pattern.compile(nameRegex);
				
				boolean validName = false;
				while(!validName) {
					System.out.println(ConsoleColors.ANSI_ITALIC + "Please enter your name:" + ConsoleColors.ANSI_RESET);
					
					System.out.print(ConsoleColors.ANSI_CYAN);
					name = scan.nextLine();
					System.out.print(ConsoleColors.ANSI_RESET);
					
					Matcher nameMatcher = namePattern.matcher(name);
					if(!nameMatcher.matches()) {
						System.out.println( ConsoleColors.ANSI_RED +"Invalid name entered. Please use the following format: Firstname Lastname\n" + ConsoleColors.ANSI_RESET);
					} else {
						break;
					}
				}
				
				Teacher teacher = new Teacher(0, username, password, name);
				boolean created = teacherDao.register(teacher);
				if(!created) {
					throw new Exception(ConsoleColors.ANSI_RED + "Could not create user\n" + ConsoleColors.ANSI_RESET);
				} else {
					System.out.println(ConsoleColors.ANSI_GREEN + "User successfully created!" + ConsoleColors.ANSI_RESET);
					break;
				}
				
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			
			
		}
	}
	
	static public Teacher loginMenu(Scanner scan) {
		String username = null;
		String password = null;
		TeacherDao teacherDao = new TeacherDaoSql();
		

		System.out.println(ConsoleColors.ANSI_WHITE_BOLD_BRIGHT + "====================================");
		System.out.println(ConsoleColors.ANSI_WHITE_BOLD_BRIGHT + "Login to Grade Book");
		System.out.println(ConsoleColors.ANSI_WHITE_BOLD_BRIGHT + "====================================\n" + ConsoleColors.ANSI_RESET);
		boolean authenticated = false;
		while(!authenticated) {
			System.out.println(ConsoleColors.ANSI_ITALIC + "Please enter your username:" + ConsoleColors.ANSI_RESET);
			System.out.print(ConsoleColors.ANSI_CYAN);
			username = scan.next();
			
			System.out.print(ConsoleColors.ANSI_RESET);
			System.out.println(ConsoleColors.ANSI_ITALIC + "Please enter your password:" + ConsoleColors.ANSI_RESET);
			System.out.print(ConsoleColors.ANSI_CYAN);
			password = scan.next();
			System.out.print(ConsoleColors.ANSI_RESET);
			
			try {
				teacherDao.setConnection();
				Optional<Teacher> valid = teacherDao.login(username, password);
				if (valid.isEmpty()) {
					System.out.println(ConsoleColors.ANSI_RED + "Invalid credentials\n" + ConsoleColors.ANSI_RESET);
				} else {
					System.out.println(ConsoleColors.ANSI_GREEN + "Successfully logged in!\n" + ConsoleColors.ANSI_RESET);
					return valid.get();
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			
		}
		
		return null;
	}
	
	static public int teacherMenu(Scanner scan, Teacher activeUser) {
		int teacherMenuChoice = 0;
		boolean teacherMenuActive = true;
		
		while(teacherMenuActive) {
			
			System.out.println("====================================");
			System.out.println("Welcome " + activeUser.getName()+ "!");
			System.out.println("====================================\n");
			
			viewClasses(activeUser);
			
			System.out.println(ConsoleColors.ANSI_ITALIC + "What would you like to do?\n" + ConsoleColors.ANSI_RESET);
			
			System.out.println("1. Select Class");
			System.out.println("2. Create Class");
			System.out.println("3. Exit\n");
			
			try {
				System.out.print(ConsoleColors.ANSI_CYAN);
				teacherMenuChoice = scan.nextInt();
				System.out.print(ConsoleColors.ANSI_RESET);
				scan.nextLine();
				
				if(teacherMenuChoice < 1 || teacherMenuChoice > 3) {
					throw new Exception(ConsoleColors.ANSI_RED + "Invalid input. Please enter a valid option(1-3)" + ConsoleColors.ANSI_RESET);
				}
				
				break;
				
			} catch(InputMismatchException e) {
				System.out.println(ConsoleColors.ANSI_RED + "Invalid input. Please enter a valid option(1-3)" + ConsoleColors.ANSI_RESET);
				scan.nextLine();
				
			} catch (Exception e) {
				System.out.println(ConsoleColors.ANSI_RED + e.getMessage() + ConsoleColors.ANSI_RESET);
			}
		}
		
		
		return teacherMenuChoice;
	}
	
	static public void viewClasses(Teacher activeUser) {
		
		TeacherDao teacherDao = new TeacherDaoSql();
		List<Classroom> classes = new ArrayList<>();
		
		try {
			
			teacherDao.setConnection();
			classes = teacherDao.viewClasses(activeUser.getTeacherId());
			
			if(classes.isEmpty()) {
				System.out.println("No classes currently being taught\n");
			} else {
				
				 System.out.printf(ConsoleColors.ANSI_YELLOW + "%-10s%-10s%n", "Class ID", "Subject" + ConsoleColors.ANSI_RESET);
				for(Classroom subject : classes) {
					System.out.printf("%-10s%-10s%n", subject.getClassId(), subject.getSubject());
				}
				System.out.println();
			}
			
			
		} catch (Exception e) {
			System.out.println(ConsoleColors.ANSI_RED + e.getMessage() + ConsoleColors.ANSI_RESET);
		}
		
	}
	
	
	static public void selectClass(Scanner scan) {
		boolean selectClassChoice = true;
		
		while (selectClassChoice) {
			
			TeacherDao teacherDao = new TeacherDaoSql();
			System.out.println(ConsoleColors.ANSI_ITALIC + "Please enter the class you would like to view" + ConsoleColors.ANSI_RESET);
			int classId = 0;
			
			try {
				
				classId = scan.nextInt();
				if(classId == 0) {
					break;
				}
				
				teacherDao.setConnection();
				
				Optional<Classroom> found = teacherDao.getClassById(classId);
				if(found.isEmpty()) {
					throw new Exception(ConsoleColors.ANSI_RED + "Could not find selected class" + ConsoleColors.ANSI_RESET);
				}
				
				System.out.println("====================================");
				System.out.println(found.get().getSubject());
				System.out.println("====================================\n");
				
				double average = 0.0;
				double median = 0.0;
				average = teacherDao.getClassAverage(classId);
				median = teacherDao.getClassMedian(classId);
				
				System.out.println("Class Average: " + average);
				System.out.println("Class Median: " + median + "\n");
				teacherDao.getStudentsInClass(classId);
				System.out.println(ConsoleColors.ANSI_WHITE_BOLD_BRIGHT + "------------------------------------\n" + ConsoleColors.ANSI_RESET);
				classMenu(scan, classId);
				
			} catch(InputMismatchException e) {
				System.out.println(ConsoleColors.ANSI_RED + "Attempted to select invalid class. Please select one from the list" + ConsoleColors.ANSI_RESET);
				scan.nextLine();
			} catch (Exception e) {
				System.out.println(ConsoleColors.ANSI_RED + e.getMessage() + ConsoleColors.ANSI_RESET);
			}
		}
		
	}
	
	static void classMenu(Scanner scan, int classId) {
		boolean active = true;
		while(active) {
			System.out.println(ConsoleColors.ANSI_ITALIC + "What would you like to do?\n" + ConsoleColors.ANSI_RESET);
			System.out.println("1. Sort Students by Name");
			System.out.println("2. Sort Students by Grade");
			System.out.println("3. Update Student Grade");
			System.out.println("4. Add Student to Class");
			System.out.println("5. Remove Student from Class");
			System.out.println("6. Exit\n");
			// Add exit option here
			
			int classChoice = 0;
			TeacherDao teacherDao = new TeacherDaoSql();
			
			try {
				classChoice = scan.nextInt();
				
				teacherDao.setConnection();
				
				double average = 0.0;
				double median = 0.0;
				
				average = teacherDao.getClassAverage(classId);
				median = teacherDao.getClassMedian(classId);
				
				switch(classChoice) {
				
				case 1:
					System.out.println();
					System.out.println(ConsoleColors.ANSI_WHITE_BOLD_BRIGHT + "Class By Name");
					System.out.println(ConsoleColors.ANSI_WHITE_BOLD_BRIGHT + "------------------------------------\n" + ConsoleColors.ANSI_RESET);
					System.out.println("Class Average: " + average);
					System.out.println("Class Median: " + median + "\n");
					teacherDao.sortByName(classId);
					System.out.println(ConsoleColors.ANSI_WHITE_BOLD_BRIGHT + "------------------------------------\n" + ConsoleColors.ANSI_RESET);
					break;
				case 2:
					System.out.println();
					System.out.println(ConsoleColors.ANSI_WHITE_BOLD_BRIGHT + "Class By Grade");
					System.out.println(ConsoleColors.ANSI_WHITE_BOLD_BRIGHT + "------------------------------------\n" + ConsoleColors.ANSI_RESET);
					System.out.println("Class Average: " + average);
					System.out.println("Class Median: " + median + "\n");
					teacherDao.sortByGrade(classId);
					System.out.println(ConsoleColors.ANSI_WHITE_BOLD_BRIGHT + "------------------------------------\n" + ConsoleColors.ANSI_RESET);
					break;
				case 3:
					break;
				case 4:
					break;
				case 5:
					break;
				case 6: 
					active = false;
					break;
				default:
					throw new Exception(ConsoleColors.ANSI_RED + "Please enter a valid input" + ConsoleColors.ANSI_RESET);
					
				}
				
				
				
			} catch(InputMismatchException e) { 
				System.out.println(ConsoleColors.ANSI_RED + "Please enter a valid input" + ConsoleColors.ANSI_RESET);
				scan.nextLine();
			} catch (Exception e) {
				System.out.println(ConsoleColors.ANSI_RED + e.getMessage() + ConsoleColors.ANSI_RESET);
			}
			
		}
	}
	
	static public void createClass(Scanner scan, Teacher activeUser) {
		System.out.println(ConsoleColors.ANSI_WHITE_BOLD_BRIGHT + "====================================");
		System.out.println(ConsoleColors.ANSI_WHITE_BOLD_BRIGHT + "Create a Class");
		System.out.println(ConsoleColors.ANSI_WHITE_BOLD_BRIGHT + "====================================\n" + ConsoleColors.ANSI_RESET);
		
		System.out.println(ConsoleColors.ANSI_ITALIC + "Please enter the subject of the class\n" + ConsoleColors.ANSI_RESET);
		
		// Add validation here later
		String subject = scan.nextLine();
		TeacherDao teacherDao = new TeacherDaoSql();
		
		try {
			
			Classroom created = new Classroom(0, subject);
			
			teacherDao.setConnection();
			Optional<Classroom> exists = teacherDao.getClassBySubject(subject);
			
			// Check to see if the class already exists before creation
			if(exists.isPresent()) {
				throw new Exception(ConsoleColors.ANSI_RED + "Class already exists" + ConsoleColors.ANSI_RESET);
			}
			teacherDao.createClass(created, activeUser.getTeacherId());
			
			// Do this to get the proper id of the class
			Optional<Classroom> found = teacherDao.getClassBySubject(subject);
			if(found.isEmpty()) {
				throw new Exception(ConsoleColors.ANSI_RED + "Error finding created class" + ConsoleColors.ANSI_RESET);
			}
			teacherDao.linkTeacherToClass(found.get().getClassId(), activeUser.getTeacherId());
			System.out.println(ConsoleColors.ANSI_GREEN + "Successfully created class!" + ConsoleColors.ANSI_RESET);
			
			
		} catch (Exception e) {
			System.out.println(ConsoleColors.ANSI_RED + e.getMessage() + ConsoleColors.ANSI_RESET);
		}
		
	}
}

