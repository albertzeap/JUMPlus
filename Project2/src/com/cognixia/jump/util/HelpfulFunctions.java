package com.cognixia.jump.util;

import java.util.InputMismatchException;
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
		System.out.println("Welcome to Movie Mania!");
		System.out.println("====================================\n");

		System.out.println("Please select an option below:\n");

		System.out.println("0. Exit");
		System.out.println("1. Register");
		System.out.println("2. Login");
		System.out.println("3. View Movies\n");

		
		try {
			
			choice = scan.nextInt();
//			scan.nextLine();
			
			if(choice != 0 && choice != 1 && choice !=2 && choice != 3) {
				throw new Exception("Please enter a choice within the range 0-3\n");
			}
			
			
		} catch(InputMismatchException e) { 
			e.printStackTrace();
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return choice;
		
	}
	
	static public int userMenu(Scanner scan, User user) {
		System.out.println("====================================");
		System.out.println("Welcome " + user.getName()+ "!");
		System.out.println("====================================\n");
		System.out.println("What would you like to do?\n");

		System.out.println("0. Exit");
		System.out.println("1. Rate Movies");
		System.out.println("2. View Previous Ratings");
		System.out.println("3. Edit Ratings\n");
//		System.out.println("4. View Favorites\n");
		
	
		int userAction = 0;
		
		try {
			userAction= scan.nextInt();
			
			if(userAction < 0 || userAction > 3) {
				throw new Exception("Invalid input. Please enter a valid option(0-4)");
			}
			
		} catch(InputMismatchException e) {
			e.printStackTrace();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return userAction;
	}
	
	
	static public boolean register(Scanner scan) {
		
		// Attributes of a user
		String email = null;
		String password = null;
		String confirm = null;
		String name = null;
		
		System.out.println("====================================");
		System.out.println("Register for the Movie Mania");
		System.out.println("====================================\n");
		
		System.out.println("Please enter your email address:");
		
		
		// regex pattern
		String emailRegex = "[a-zA-Z]+[0-9]*@[a-z]+\\.com";
		String nameRegex = "[A-Z][a-z]+\s[A-Z][a-z]+";
		Pattern pattern = Pattern.compile(emailRegex);
		Pattern namePattern = Pattern.compile(nameRegex);
		
		
		
		UserDao userDao = new UserDaoSql();
		
		try {
			
			email = scan.next();
			
			// matcher created with pattern and string
			Matcher matcher = pattern.matcher(email);
			
			if(!matcher.matches()) {
				throw new Exception("Invalid email format. Please use the following format : example@gmail.com\n");
			}
			
			System.out.println("Please enter a password:");
			password = scan.next();
			
			
			System.out.println("Please confirm your password:");
			confirm = scan.next();
			scan.nextLine();
			
			if(!password.equals(confirm)) {
				System.out.println("Passwords do not match\n");
				return false;
			};
			
			System.out.println("Please enter your name:");
			name = scan.nextLine();
			
			Matcher nameMatcher = namePattern.matcher(name);
			
			if(!nameMatcher.matches()) {
				throw new Exception("Invalid name entered\nExample: Firstname Lastname\n");
			}
			
			// Create the user
			User user = new User();
			user.setEmail(email);
			user.setPassword(password);
			user.setName(name);
			
			userDao.setConnection();
			Boolean created = userDao.createAccount(user);
			
			if(created) {
				System.out.println("Your account has been created " + user.getName() +"!\n");
			} else {
				System.out.println("Could not create account\n");
				return false;
			}
			
			return true;
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return false;
		
	}
	
	static public User login(Scanner scan) {
		
		String username = null;
		String password = null;
		
		// Get user input for login
	

		System.out.println("====================================");
		System.out.println("Login to Movie Mania");
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
				throw new Exception("**Invalid Credentials**");
			}
			
			validUser = currUser.get();
			
			return validUser;
			
		} catch(Exception e){
			System.out.println(e.getMessage());
		}
		
		return validUser;
	}
	
	static public void loadMovies(User user) {
		
		UserDao userDao = new UserDaoSql();
		try {
			
			userDao.setConnection();
			userDao.getMovies();
			
			
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	static public Movie selectMovie(Scanner scan, User user) {
		
		UserDao userDao = new UserDaoSql();
		int movieId = -1;
		Movie movie = new Movie();
		try {
			
			System.out.println("Please select a movie to rate:");
			movieId = scan.nextInt();

			// Option to quit
			if(movieId == 0) {
				return movie;
			}
			
			userDao.setConnection();
			movie = userDao.getMovieById(movieId);
			return movie;
			
		} catch(InputMismatchException e) {
			e.printStackTrace();
//			System.out.println(e.getMessage());
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return movie;
	}
	
	static public void rateMovie(Scanner scan, Movie movie, User user) {
		
		System.out.println("====================================");
		System.out.println("Rate the following movie: " + movie.getTitle());
		System.out.println("====================================\n");
		System.out.println(movie.getDescript() + "\n");
		System.out.println("Please enter your rating (1-5):");
		
		UserDao userDao = new UserDaoSql();
		
		try {
			
			int rating = scan.nextInt();
			if(rating < 1 || rating > 5) {
				throw new Exception("Invalid input. Please enter a number between 1 and 5");
			}
			
			
			userDao.setConnection();
			userDao.rateMovie(movie, user, rating);
		} catch(InputMismatchException e) {
			System.out.println(e.getMessage());
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}
	
	static public void viewPreviousRatings(User user) {
		
		UserDao userDao = new UserDaoSql();
		
		try {
			
			userDao.setConnection();
			boolean success = userDao.getUserRatings(user.getUserId());
			if(!success) {
				throw new Exception("Failed to obtain user ratings\n");
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	static public void editPreviousRatings(Scanner scan, User user) {
		
		viewPreviousRatings(user);
		System.out.println("What would you like to do?\n");
		System.out.println("1. Edit Rating");
		System.out.println("2. Delete Rating");
		
		int editDelete = 0;
		UserDao userDao = new UserDaoSql();
		try {
			
			editDelete = scan.nextInt();
			if(editDelete < 1 || editDelete >2) {
				throw new Exception("Invalid input. Please enter a valid input(1-2)");
			}
			
			if(editDelete == 1) {
				int movieId = 0;
				int rating = 0;
				System.out.println("Which movie would you like to edit?\n");
				movieId = scan.nextInt();
				System.out.println("What rating would you like to give(1-5)?\n");
				rating = scan.nextInt();
				if(rating < 1 || rating > 5) {
					throw new Exception("Invalid input. Please enter a number between 1 and 5");
				}
				
				userDao.setConnection();
				boolean updated = userDao.editRating(movieId, user.getUserId(), rating);
				if(!updated) {
					throw new Exception("Failed to update movie with ID " + movieId + " with rating of " + rating);
				}
				
				System.out.println("Movie of ID " + movieId + " updated with rating of " + rating + "\n");
			}
			
			
			// Delete rating
			if(editDelete == 2) {
				int movieId = 0;
				System.out.println("Which movie rating would you like to delete?\n");
				movieId = scan.nextInt();
				userDao.setConnection();
				boolean deleted = userDao.deleteRating(movieId, user.getUserId());
				if(!deleted) {
					throw new Exception("Failed to delete movie with ID " + movieId);
				}
				System.out.println("Successfully deleted movie with ID " + movieId);
			}
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	
}
