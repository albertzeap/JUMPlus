package com.cognixia.jump;

import java.util.Scanner;

import com.cognixia.jump.dao.User;
import com.cognixia.jump.util.HelpfulFunctions;

public class MovieRatingDriver {

	public static void main(String[] args) {
		
		System.out.println("Welcome to Movie Rater!");
		Scanner scan = new Scanner(System.in);
		int choice = HelpfulFunctions.menu(scan);
		User activeUser = new User();
		
		
		if(choice == 1) {
			Boolean success = false;
			
			do {
				
				success = HelpfulFunctions.register(scan);
			} while (!success);
			
		}
		
		if(choice == 2) {
			
			// Keep running the login page in case incorrect credentials are given
			do {
				activeUser = HelpfulFunctions.login(scan);
			} while(activeUser.getName() == null);
			
			// Ensures that null does not print 
			if(activeUser.getName() != null) {
				System.out.println("Welcome " + activeUser.getName());
				HelpfulFunctions.loadMovies(activeUser);
			}
			
		}
		
		
	}

}
