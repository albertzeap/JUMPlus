package com.cognixia.jump.util;

import java.util.Optional;
import java.util.Scanner;

import com.cognixia.jump.dao.User;
import com.cognixia.jump.dao.UserDao;
import com.cognixia.jump.dao.UserDaoSql;

public class HelpfulFunctions {
	
	static public User login() {
		
		String username = null;
		String password = null;
		
		// Get user input for login
		try(Scanner scan = new Scanner(System.in)){
	
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
			
			if(currUser.isEmpty()) {
				throw new Exception("User does not exist");
			}
			
			validUser = currUser.get();
			
			return validUser;
			
		} catch(Exception e){
			e.printStackTrace();
		}
		
		return validUser;
	}
	
}
