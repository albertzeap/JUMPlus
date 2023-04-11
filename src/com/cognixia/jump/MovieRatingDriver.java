package com.cognixia.jump;

import com.cognixia.jump.dao.User;
import com.cognixia.jump.util.HelpfulFunctions;

public class MovieRatingDriver {

	public static void main(String[] args) {
		
		System.out.println("Welcome to Movie Rater!");
		User activeUser = HelpfulFunctions.login();
		System.out.println("Welcome " + activeUser.getEmail());
	}

}
