package com.cognixia.jump.util;

import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cognixia.jump.dao.User;
import com.cognixia.jump.dao.UserDao;
import com.cognixia.jump.dao.UserDaoSql;

public class HelpfulFunctions {
	
	static public int menu(Scanner scan) {
		
		int choice = 0;
		
		System.out.println("1. REGISTER ");
		System.out.println("2. LOGIN ");
		System.out.println("3. VIEW MOVIES ");
		System.out.println("4. EXIT ");
		
		try {
			
			choice = scan.nextInt();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return choice;
		
	}
	
	static public boolean register(Scanner scan) {
		
		// Attributes of a user
		String email = null;
		String password = null;
		String confirm = null;
		String name = null;
		
		System.out.println("Register Now! ");
		
		System.out.println("Enter email ");
		email = scan.next();
		
		String emailRegex = "[a-zA-Z]+@gmail\\.com";
		
		// regex pattern
		Pattern pattern = Pattern.compile(emailRegex);
		
		// matcher created with pattern and string
		Matcher matcher = pattern.matcher(email);
		
		if(matcher.matches()) {
			System.out.println("Email matches");
		}
		else {
			System.out.println("Email doesn't match");
		}
		
		System.out.println("Enter password");
		password = scan.next();
	
		
		System.out.println("Confirm password");
		confirm = scan.next();
		scan.nextLine();
		
		if(!password.equals(confirm)) {
			System.out.println("Passwords do not match");
			return false;
		};
		
		System.out.println("Enter Name");
		name = scan.nextLine();
		
		// Create the user
		User user = new User();
		user.setEmail(email);
		user.setPassword(password);
		user.setName(name);
		
		UserDao userDao = new UserDaoSql();
		
		try {
			
			userDao.setConnection();
			Boolean created = userDao.createAccount(user);
			
			if(created) {
				System.out.println("Your account has been created " + user.getName());
			} else {
				System.out.println("Could not create account");
				return false;
			}
			
			return true;
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
		
	}
	
	static public User login(Scanner scan) {
		
		String username = null;
		String password = null;
		
		// Get user input for login
		try {

			System.out.println("Enter email");
			username = scan.next();
			
			System.out.println("Enter password");
			password = scan.next();
			
			
		} catch(Exception e) {	
			e.printStackTrace();
		}
		
		// Check the user with the database
		UserDao userDao = new UserDaoSql();
		User validUser = new User();
		
		try {
			userDao.setConnection();
			
			Optional<User> currUser = userDao.authenticate(username, password);
			
			if(currUser.get().getEmail() == null) {
				throw new Exception("User does not exist");
			}
			
			validUser = currUser.get();
			
			return validUser;
			
		} catch(Exception e){
			e.printStackTrace();
		}
		
		return validUser;
	}
	
	static public void loadMovies(User user) {
		
		UserDao userDao = new UserDaoSql();
		try {
			
			userDao.setConnection();
			userDao.getMovies();
			
			
		} catch(Exception e) {
			
		}
	}
	
}
