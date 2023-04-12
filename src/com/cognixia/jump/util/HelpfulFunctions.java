package com.cognixia.jump.util;

import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cognixia.jump.dao.Movie;
import com.cognixia.jump.dao.User;
import com.cognixia.jump.dao.UserDao;
import com.cognixia.jump.dao.UserDaoSql;

public class HelpfulFunctions {
	
	static public int menu(Scanner scan) {
		
		int choice = 0;
		
		System.out.println("====================================");
		System.out.println("Welcome to the Movie Rating Console App");
		System.out.println("====================================\n");

		System.out.println("Please select an option below:\n");

		System.out.println("1. Register");
		System.out.println("2. Login");
		System.out.println("3. View Movies");
		System.out.println("4. Exit\n");

		
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
		
		System.out.println("====================================");
		System.out.println("Register for the Movie Rating Console App");
		System.out.println("====================================\n");
		
		System.out.println("Please enter your email address:");
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
		
		System.out.println("Please enter a password:");
		password = scan.next();
	
		
		System.out.println("Please confirm your password:");
		confirm = scan.next();
		scan.nextLine();
		
		if(!password.equals(confirm)) {
			System.out.println("Passwords do not match");
			return false;
		};
		
		System.out.println("Please enter your name:");
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
	

		System.out.println("====================================");
		System.out.println("Login to the Movie Rating Console App");
		System.out.println("====================================\n");

		System.out.println("Please enter your email address:");
		username = scan.next();

		System.out.println("Please enter your password:");
		password = scan.next();

		
			
		
		
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
	
	static public Movie selectMovie(Scanner scan, User user) {
		
		UserDao userDao = new UserDaoSql();
		int movieId = 0;
		Movie movie = new Movie();
		try {
			
			System.out.println("Please select a movie to rate:");
			movieId = scan.nextInt();
			
			userDao.setConnection();
			movie = userDao.getMovieById(movieId);
			
		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		}
		
		return movie;
	}
	
	static public void rateMovie(Scanner scan, Movie movie, User user) {
		
		System.out.println("====================================");
		System.out.println("Rate the following movie: " + movie.getTitle());
		System.out.println("====================================\n");

		System.out.println(movie.getDescript() + "\n");

		System.out.println("Please enter your rating (1-5):");
		int rating = scan.nextInt();
		UserDao userDao = new UserDaoSql();
		
		try {
			userDao.setConnection();
			userDao.rateMovie(movie, user, rating);
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
	
}
