package com.cognixia.jump.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface UserDao {
	
	public void setConnection() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException;
	
	// Create an account 
	public boolean createAccount(User newUser);
	
	// Authenticate a user
	public Optional<User> authenticate(String email, String password);
	
	// Obtain the list of movies
	public boolean getMovies();
	
	// Obtain the users favorite list
	public List<Movie> getFavorites(int userId);
	
	// Obtain the list of user's movie ratings
	public boolean getUserRatings(int userId);
	
	// Obtain a movie by Id
	public Movie getMovieById(int movieId);
	
	// Rate a show
	public boolean rateMovie(Movie movie, User user, int rating);
	
	// Add a movie to favorites
	public boolean favoriteMovie(int userId, int movieId);
	
	// Edit movie rating
	public boolean editRating(int movieId, int userId, int rating);
	
	public boolean deleteRating(int movieId, int userId);
	
}
