package com.cognixia.jump;

import java.util.Scanner;

import com.cognixia.jump.dao.Movie;
import com.cognixia.jump.dao.User;
import com.cognixia.jump.util.HelpfulFunctions;

public class MovieRatingDriver {

	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);
		int choice = HelpfulFunctions.menu(scan);
		User activeUser = new User();
		
		
		if(choice == 1) {
			Boolean success = false;
			
			while(!success) {
				success = HelpfulFunctions.register(scan);
			}
			
			choice = HelpfulFunctions.menu(scan);		
			
		}
		
		if(choice == 2) {
			
			// Keep running the login page in case incorrect credentials are given
			do {
				activeUser = HelpfulFunctions.login(scan);
			} while(activeUser.getName() == null);
			
			// Ensures that null does not print 
			if(activeUser.getName() != null) {
				System.out.println("====================================");
				System.out.println("Welcome to the Movie Rating Console App, " + activeUser.getName()+ "!");
				System.out.println("====================================\n");
				HelpfulFunctions.loadMovies(activeUser);
				Movie selected = HelpfulFunctions.selectMovie(scan, activeUser);
				HelpfulFunctions.rateMovie(scan, selected, activeUser);
				
			
			}
			
		}
		
		
	}

}
