package com.cognixia.jump;

import java.util.Scanner;

import com.cognixia.jump.dao.Movie;
import com.cognixia.jump.dao.User;
import com.cognixia.jump.util.HelpfulFunctions;

public class MovieRatingDriver {

	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);
		int choice = -1;
		User activeUser = new User();
		
		// Loop through the initial menu until a correct input is given
		do {
			choice = HelpfulFunctions.menu(scan);
		} while (choice != 0 && choice != 1 && choice !=2 && choice != 3 );
		
		
		if(choice == 1) {
			Boolean success = false;
			
			while(!success) {
				success = HelpfulFunctions.register(scan);
			}
			
			choice = HelpfulFunctions.menu(scan);		
		}
		
		if(choice == 2) {
			int userAction = 0;
			// Keep running the login page in case incorrect credentials are given
			do {
				activeUser = HelpfulFunctions.login(scan);
			} while(activeUser.getName() == null);
			
			
			
//			System.out.println("====================================");
//			System.out.println("Welcome to the Movie Rating Console App, " + activeUser.getName()+ "!");
//			System.out.println("====================================\n");
//			System.out.println("What would you like to do?\n");
//
//			System.out.println("0. Exit");
//			System.out.println("1. View Previous Ratings");
//			System.out.println("2. Edit Ratings");
//			System.out.println("3. View Favorites\n");
			
		
	
				do {
					
					userAction = HelpfulFunctions.userMenu(scan, activeUser);
					// User Quits
					if(userAction == 0) choice = userAction;
					// User rates movies 
					if(userAction == 1){
						HelpfulFunctions.loadMovies(activeUser);
						Movie selected = HelpfulFunctions.selectMovie(scan, activeUser);
						
						if(selected != null) {
							choice = selected.getMovieId();
						}
						if(choice != 0 && selected != null) {
							HelpfulFunctions.rateMovie(scan, selected, activeUser);	
						}
					}
					
					
					
					
				} while (choice != 0);	
			}
				

		
		
		
		
		if(choice == 3) {
			
			HelpfulFunctions.loadMovies(activeUser);
		}
		
		if(choice == 0) {
			System.out.println("Thank you for using our movie rating program. Goodbye!");
		}
		
		
	}

}
