package com.cognixia.jump.application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cognixia.jump.controller.GradeBookController;
import com.cognixia.jump.dao.TeacherDao;
import com.cognixia.jump.dao.TeacherDaoSql;
import com.cognixia.jump.model.Classroom;
import com.cognixia.jump.model.Student;
import com.cognixia.jump.model.Teacher;
import com.cognixia.jump.util.ConsoleColors;

public class StudentGradeBookMenu {
	
	private static GradeBookController controller = new GradeBookController();
	
	
	static public void run() {
		
		Scanner scan = new Scanner(System.in);
		Teacher activeUser = null;
		Student activeStudentUser = null;
		
		int firstMenuChoice = landingMenu(scan);
		if(firstMenuChoice == 1) {
			registerMenu(scan);
			firstMenuChoice = landingMenu(scan);
		}
		if (firstMenuChoice == 2) {
			int secondMenuChoice = 0;
			
			// Determine whether returned list contains student or teacher
			List<Object> users = loginMenu(scan);
			
			if(users.get(0) instanceof Student) {
				activeStudentUser = (Student) users.get(0);
				secondMenuChoice = dashboardMenu(scan, activeStudentUser);
				
			} else {
				activeUser = (Teacher) users.get(0);
				secondMenuChoice = dashboardMenu(scan,activeUser);
			}
			
			
			if(secondMenuChoice == 3) {
				System.out.println("Thank you for using Grade Book. We hope you had a pleasant experience!\n");
				run();
			}
		}
		if(firstMenuChoice == 3) {
			System.out.println("Exiting Grade Book application...\n");
			return;
		}
	}
	
	private static int landingMenu (Scanner scan) {
		int firstMenuChoice = 0;
		
		while(true) {
			System.out.println(ConsoleColors.ANSI_BLUE_BRIGHT + "====================================");
			System.out.println(ConsoleColors.ANSI_WHITE_BOLD_BRIGHT + "Welcome to Grade Book!");
			System.out.println(ConsoleColors.ANSI_BLUE_BRIGHT + "====================================\n" + ConsoleColors.ANSI_RESET);

			System.out.println(ConsoleColors.ANSI_ITALIC + "Please select an option below:\n" + ConsoleColors.ANSI_RESET);

			System.out.println("1. Register");
			System.out.println("2. Login");
			System.out.println("3. Exit\n");
			
			
			try {
				System.out.print(ConsoleColors.ANSI_CYAN);
				firstMenuChoice = scan.nextInt();
				System.out.print(ConsoleColors.ANSI_RESET);
				if(firstMenuChoice < 1 || firstMenuChoice > 3) {
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
	
	private static void registerMenu(Scanner scan) {
		
		while(true) {
			System.out.println(ConsoleColors.ANSI_BLUE_BRIGHT +"====================================");
			System.out.println(ConsoleColors.ANSI_WHITE_BOLD_BRIGHT+ "Register for Grade Book");
			System.out.println(ConsoleColors.ANSI_BLUE_BRIGHT + "====================================\n" + ConsoleColors.ANSI_RESET);
			
			String username = "";
			String password = "";
			String name = "";
			
			try {
				
				// Username validation 
				boolean exists = true;
				boolean studentExists = true;
				
				while (exists || studentExists) {
					System.out.print(ConsoleColors.ANSI_RESET);
					System.out.println(ConsoleColors.ANSI_ITALIC + "Please enter your username:" + ConsoleColors.ANSI_RESET);
					
					System.out.print(ConsoleColors.ANSI_CYAN);
					username = scan.next();
					System.out.print(ConsoleColors.ANSI_RESET);
					
					// Check if username already exists
					studentExists = controller.getStudentByUsername(username);
					exists = controller.getTeacherByUsername(username);
					
					if(exists || studentExists) {
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
				
				
				boolean created = controller.createTeacher(0, username, password, name);
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
	
	private static List<Object> loginMenu(Scanner scan) {
		String username = "";
		String password = "";
		List<Object> users = new ArrayList<>();
		
		System.out.println(ConsoleColors.ANSI_BLUE_BRIGHT + "====================================");
		System.out.println(ConsoleColors.ANSI_WHITE_BOLD_BRIGHT + "Login to Grade Book");
		System.out.println(ConsoleColors.ANSI_BLUE_BRIGHT +  "====================================\n" + ConsoleColors.ANSI_RESET);
		
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
			
			users = controller.loginUser(username, password);
				
				
			if(!users.isEmpty()) {
				System.out.println(ConsoleColors.ANSI_GREEN + "Successfully logged in!\n" + ConsoleColors.ANSI_RESET);
				authenticated = true;
			} else {
				System.out.println(ConsoleColors.ANSI_RED + "Invalid credentials\n" + ConsoleColors.ANSI_RESET);
			}			
		}
		
		return users;
	}
	
	private static int dashboardMenu(Scanner scan, Student activeStudentUser) {
		int studentMenuChoice = 0;
		boolean studentMenuActive = true;
		
		while(studentMenuActive) {
			
			System.out.println(ConsoleColors.ANSI_BLUE_BRIGHT + "====================================");
			System.out.println(ConsoleColors.ANSI_WHITE_BOLD_BRIGHT + "Welcome " + activeStudentUser.getName()+ "!");
			System.out.println(ConsoleColors.ANSI_BLUE_BRIGHT + "====================================\n" + ConsoleColors.ANSI_RESET);
			
			viewClasses(activeStudentUser);
			
			System.out.println(ConsoleColors.ANSI_ITALIC + "\nWhat would you like to do?\n" + ConsoleColors.ANSI_RESET);
			
			System.out.println("1. Select Class");
			System.out.println("2. Exit\n");
			
			try {
				System.out.print(ConsoleColors.ANSI_CYAN);
				studentMenuChoice = scan.nextInt();
				System.out.print(ConsoleColors.ANSI_RESET);
				
				switch(studentMenuChoice) {
				
				case 1:
					System.out.println("Feature not yet developed :)");
					break;
				case 2:
					studentMenuActive = false;
					studentMenuChoice = 3;
					break;
				default:
					throw new Exception(ConsoleColors.ANSI_RED + "Invalid input. Please enter a valid option(1-3)" + ConsoleColors.ANSI_RESET);
				}
				
			} catch(InputMismatchException e) {
				System.out.println(ConsoleColors.ANSI_RED + "Invalid input. Please enter a valid option(1-3)" + ConsoleColors.ANSI_RESET);
				scan.nextLine();
				
			} catch (Exception e) {
				System.out.println(ConsoleColors.ANSI_RED + e.getMessage() + ConsoleColors.ANSI_RESET);
			}
		}
		return studentMenuChoice;
	}
	
	private static int dashboardMenu(Scanner scan, Teacher activeUser) {
		int teacherMenuChoice = 0;
		boolean teacherMenuActive = true;
		
		while(teacherMenuActive) {
			
			System.out.println(ConsoleColors.ANSI_BLUE_BRIGHT + "====================================");
			System.out.println(ConsoleColors.ANSI_WHITE_BOLD_BRIGHT + "Welcome " + activeUser.getName()+ "!");
			System.out.println(ConsoleColors.ANSI_BLUE_BRIGHT + "====================================\n" + ConsoleColors.ANSI_RESET);
			
			List<Classroom> classes = viewClasses(activeUser);
			
			System.out.println(ConsoleColors.ANSI_ITALIC + "What would you like to do?\n" + ConsoleColors.ANSI_RESET);
			
			System.out.println("1. Select Class");
			System.out.println("2. Create Class");
			System.out.println("3. Exit\n");
			
			try {
				System.out.print(ConsoleColors.ANSI_CYAN);
				teacherMenuChoice = scan.nextInt();
				System.out.print(ConsoleColors.ANSI_RESET);
				
				switch(teacherMenuChoice) {
				
				case 1:
					selectClass(scan, classes);						
					break;
				case 2: 
					createClass(scan, activeUser);
					break;
				case 3:
					teacherMenuActive = false;
					break;
				default:
					throw new Exception(ConsoleColors.ANSI_RED + "Invalid input. Please enter a valid option(1-3)" + ConsoleColors.ANSI_RESET);
				}

			} catch(InputMismatchException e) {
				System.out.println(ConsoleColors.ANSI_RED + "Invalid input. Please enter a valid option(1-3)" + ConsoleColors.ANSI_RESET);
				scan.nextLine();
				
			} catch (Exception e) {
				System.out.println(ConsoleColors.ANSI_RED + e.getMessage() + ConsoleColors.ANSI_RESET);
			}
		}
		
		
		return teacherMenuChoice;
	}
	
	private static void viewClasses(Student activeUser) {
	
		Map<Classroom, Integer> classes = controller.viewStudentClasses(activeUser.getStudentId());
		
		// Print out the classes if the map is not empty
		if(!classes.isEmpty()) {
			System.out.printf(ConsoleColors.YELLOW_UNDERLINED + "%-8s%-25s%-10s%n", "Class ID", "Subject", "Grade" + ConsoleColors.ANSI_RESET);
			for(Map.Entry<Classroom, Integer> entry : classes.entrySet()) {
				
				System.out.printf("%-8d%-25s%-10d%n", entry.getKey().getClassId(), entry.getKey().getSubject(), entry.getValue());	
			}			
		} else {
			System.out.println("Not enrolled in any classes\n");
		}
	}
	
	private static List<Classroom> viewClasses(Teacher activeUser) {
	
		List<Classroom> classes = controller.viewTeacherClasses(activeUser.getTeacherId());
			
		if(classes.isEmpty()) {
			System.out.println("No classes currently being taught\n");
		} else {
			
			System.out.printf(ConsoleColors.YELLOW_UNDERLINED + "%-10s%-10s%n", "Class ID", "Subject" + ConsoleColors.ANSI_RESET);
			for(Classroom subject : classes) {
				System.out.printf("%-10s%-10s%n", subject.getClassId(), subject.getSubject());
			}
			System.out.println();
		}
		
		return classes;
		
	}
	
	
	private static void selectClass(Scanner scan, List<Classroom> classes) {
		boolean selectClassChoice = true;
		
		while (selectClassChoice) {
			
			System.out.println(ConsoleColors.ANSI_ITALIC + "Please enter the class you would like to view" + ConsoleColors.ANSI_RESET);
			int classId = 0;
			
			try {
				System.out.print(ConsoleColors.ANSI_CYAN);
				classId = scan.nextInt();
				System.out.print(ConsoleColors.ANSI_RESET);
				
				if(classId == 0) {
					break;
				}
				
				Optional<Classroom> found = controller.getClassById(classId);
				if(found.isEmpty()) {
					throw new Exception(ConsoleColors.ANSI_RED + "Could not find selected class" + ConsoleColors.ANSI_RESET);
				}
				
				System.out.println(ConsoleColors.ANSI_BLUE_BRIGHT + "====================================");
				System.out.println(ConsoleColors.ANSI_WHITE_BOLD_BRIGHT + found.get().getSubject());
				System.out.println(ConsoleColors.ANSI_BLUE_BRIGHT + "====================================\n" + ConsoleColors.ANSI_RESET);
				
				Map<Student, Integer> students = controller.getStudentsInClass(classId);
				List<Double> averageMedian = new ArrayList<>();
				
				if(students.isEmpty()) {
					System.out.println("No students in class");
					System.out.println();
				} else {
					averageMedian = controller.getClassAverageMedian(classId);
					System.out.println( ConsoleColors.ANSI_YELLOW + ConsoleColors.ANSI_ITALIC +"Class Average: " + averageMedian.get(0) + ConsoleColors.ANSI_RESET);
					System.out.println( ConsoleColors.ANSI_YELLOW + ConsoleColors.ANSI_ITALIC +"Class Median: " + averageMedian.get(1) + "\n" + ConsoleColors.ANSI_RESET);
					
					System.out.printf(ConsoleColors.YELLOW_UNDERLINED + "%-10s%-20s%-10s%n", "StudentID", "Name", "Grade" + ConsoleColors.ANSI_RESET);
					System.out.println();
					
					for(Map.Entry<Student, Integer> student: students.entrySet()) {
						
						System.out.printf("%-10d%-20s%-10d%n", student.getKey().getStudentId(), student.getKey().getName(), student.getValue());
					}
				}
				
				System.out.println(ConsoleColors.ANSI_WHITE_BOLD_BRIGHT + "------------------------------------\n" + ConsoleColors.ANSI_RESET);
				int classMenuChoice = classMenu(scan, classId);
				
				// Go back to previous menu
				if(classMenuChoice == 6) {
					break;
				}
				
			} catch(InputMismatchException e) {
				System.out.println(ConsoleColors.ANSI_RED + "Attempted to select invalid class. Please select one from the list" + ConsoleColors.ANSI_RESET);
				scan.nextLine();
			} catch (Exception e) {
				System.out.println(ConsoleColors.ANSI_RED + e.getMessage() + ConsoleColors.ANSI_RESET);
			}
		}
		
	}
	
	private static int classMenu(Scanner scan, int classId) {
		boolean active = true;
		int classChoice = 0;
		
		while(active) {
			System.out.println(ConsoleColors.ANSI_ITALIC + "What would you like to do?\n" + ConsoleColors.ANSI_RESET);
			System.out.println("1. Sort Students by Name");
			System.out.println("2. Sort Students by Grade");
			System.out.println("3. Update Student Grade");
			System.out.println("4. Add Student to Class");
			System.out.println("5. Remove Student from Class");
			System.out.println("6. Exit\n");
			// Add exit option here
			
			try {
				System.out.println(ConsoleColors.ANSI_CYAN);
				classChoice = scan.nextInt();
				System.out.println(ConsoleColors.ANSI_RESET);
				
				
				Map<Student, Integer> studentsInClass = controller.getStudentsInClass(classId);
				List<Double> averageMedian = controller.getClassAverageMedian(classId);
				switch(classChoice) {
				
				// Sort by name
				case 1:
					if(studentsInClass.isEmpty()) {
						throw new Exception("No students to sort.");
					}
					
					System.out.println(ConsoleColors.ANSI_WHITE_BOLD_BRIGHT + "Class By Name");
					System.out.println(ConsoleColors.ANSI_BLUE_BRIGHT + "------------------------------------\n" + ConsoleColors.ANSI_RESET);
					System.out.println(ConsoleColors.ANSI_YELLOW + ConsoleColors.ANSI_ITALIC + "Class Average: " + averageMedian.get(0) + ConsoleColors.ANSI_RESET);
					System.out.println(ConsoleColors.ANSI_YELLOW + ConsoleColors.ANSI_ITALIC + "Class Median: "  + averageMedian.get(1) + "\n" + ConsoleColors.ANSI_RESET);
					studentsInClass = controller.sortByName(classId);
					
					System.out.printf(ConsoleColors.YELLOW_UNDERLINED + "%-10s%-20s%-10s%n", "StudentID", "Name", "Grade" + ConsoleColors.ANSI_RESET);
					System.out.println();
					for(Map.Entry<Student, Integer> student: studentsInClass.entrySet()) {
						
						System.out.printf("%-10d%-20s%-10d%n", student.getKey().getStudentId(), student.getKey().getName(), student.getValue());
					}
					
					System.out.println(ConsoleColors.ANSI_WHITE_BOLD_BRIGHT + "------------------------------------\n" + ConsoleColors.ANSI_RESET);
					break;
					
				// Sort by grade
				case 2:
					
					if(studentsInClass.isEmpty()) {
						throw new Exception("No students to sort.");
					}

					System.out.println(ConsoleColors.ANSI_WHITE_BOLD_BRIGHT + "Class By Grade");
					System.out.println(ConsoleColors.ANSI_BLUE_BRIGHT + "------------------------------------\n" + ConsoleColors.ANSI_RESET);
					System.out.println(ConsoleColors.ANSI_YELLOW + ConsoleColors.ANSI_ITALIC +"Class Average: " +  averageMedian.get(0) + ConsoleColors.ANSI_RESET);
					System.out.println(ConsoleColors.ANSI_YELLOW + ConsoleColors.ANSI_ITALIC +"Class Median: " +   averageMedian.get(1) + "\n" + ConsoleColors.ANSI_RESET);
					studentsInClass = controller.sortByGrade(classId);
					
					System.out.printf(ConsoleColors.YELLOW_UNDERLINED + "%-10s%-20s%-10s%n", "StudentID", "Name", "Grade" + ConsoleColors.ANSI_RESET);
					System.out.println();
					for(Map.Entry<Student, Integer> student: studentsInClass.entrySet()) {
						
						System.out.printf("%-10d%-20s%-10d%n", student.getKey().getStudentId(), student.getKey().getName(), student.getValue());
					}
					
					System.out.println(ConsoleColors.ANSI_WHITE_BOLD_BRIGHT + "------------------------------------\n" + ConsoleColors.ANSI_RESET);
					break;
					
				// Update grade
				case 3:
					
					if(studentsInClass.isEmpty()) {
						throw new Exception("No students to update.");
					}
					
					int studentId = 0;
					int grade = 0;
					System.out.println(ConsoleColors.ANSI_ITALIC + "Which student grade would you like to update?" + ConsoleColors.ANSI_RESET);
					
					System.out.println(ConsoleColors.ANSI_CYAN);
					studentId = scan.nextInt();
					System.out.println(ConsoleColors.ANSI_RESET);
					
					System.out.println(ConsoleColors.ANSI_ITALIC + "What grade would you like to update to?" + ConsoleColors.ANSI_RESET);
					System.out.println(ConsoleColors.ANSI_CYAN);
					grade = scan.nextInt();
					System.out.println(ConsoleColors.ANSI_RESET);
					
					boolean updated = controller.updateStudentGrade(classId, studentId, grade);
					
					if(updated) {
						System.out.println(ConsoleColors.ANSI_GREEN + "Successfully updated!" + ConsoleColors.ANSI_RESET);
					}
					
					break;
				// Add student 
				case 4:
					List<Student> students = controller.getAllStudentsNotInClass(classId);
					
					if(students.isEmpty()) {
						System.out.println("All students enrolled in class");
						break;
					} else { 
						
						System.out.printf(ConsoleColors.YELLOW_UNDERLINED + "%-10s%-10s%n", "StudentID", "Name" + ConsoleColors.ANSI_RESET);
						System.out.println();
						for(Student stu : students) {
							System.out.printf("%-10d%-10s%n", stu.getStudentId(), stu.getName());
							
						}
					}
					
					int studentIdToAdd = 0;
					System.out.println(ConsoleColors.ANSI_ITALIC + "Which student would you like to add?" + ConsoleColors.ANSI_RESET);
					
					System.out.println(ConsoleColors.ANSI_CYAN);
					studentIdToAdd = scan.nextInt();
					System.out.println(ConsoleColors.ANSI_RESET);
					
					boolean success = controller.addStudentToClass(studentIdToAdd, classId);
					if(!success) {
						throw new Exception(ConsoleColors.ANSI_RED + "Error adding student to class" + ConsoleColors.ANSI_RESET);
					}
					System.out.println(ConsoleColors.ANSI_GREEN + "Student successfully added to class!" + ConsoleColors.ANSI_RESET );
					break;
					
				// Remove student
				case 5:
					int studentIdToRemove = 0;
					System.out.println(ConsoleColors.ANSI_ITALIC + "Which student would you like to remove?" + ConsoleColors.ANSI_RESET);
					
					System.out.println(ConsoleColors.ANSI_CYAN);
					studentIdToRemove = scan.nextInt();
					System.out.println(ConsoleColors.ANSI_RESET);
					
					boolean removed = controller.removeStudentFromClass(studentIdToRemove, classId);
					if(!removed) {
						throw new Exception(ConsoleColors.ANSI_RED + "Error removing student from class" + ConsoleColors.ANSI_RESET);
					}
					System.out.println(ConsoleColors.ANSI_GREEN + "Student successfully removed from class!" + ConsoleColors.ANSI_RESET );
					break;
				case 6: 
					active = false;
					return classChoice;
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
		return classChoice;
	}
	
	static public void createClass(Scanner scan, Teacher activeUser) {
		System.out.println(ConsoleColors.ANSI_BLUE_BRIGHT + "====================================");
		System.out.println(ConsoleColors.ANSI_WHITE_BOLD_BRIGHT + "Create a Class");
		System.out.println(ConsoleColors.ANSI_BLUE_BRIGHT + "====================================\n" + ConsoleColors.ANSI_RESET);
		
		System.out.println(ConsoleColors.ANSI_ITALIC + "Please enter the subject of the class\n" + ConsoleColors.ANSI_RESET);
		
		// Add validation here later
		scan.nextLine();
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

