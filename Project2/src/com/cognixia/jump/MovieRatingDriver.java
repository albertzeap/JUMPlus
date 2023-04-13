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
					
					if(userAction == 2) {
						HelpfulFunctions.viewPreviousRatings(activeUser);
					}
				} while (choice != 0);	
				
			}
				

		
		
		
		// Just to view the movies
		if(choice == 3) {
			
			HelpfulFunctions.loadMovies(activeUser);
		}
		
		if(choice == 0) {
			System.out.println("Thank you for using our movie rating program. Goodbye!");
		}
		
		
	}

}
