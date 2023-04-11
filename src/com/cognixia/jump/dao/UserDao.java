package com.cognixia.jump.dao;

import java.util.List;
import java.util.Optional;

public interface UserDao {
	
	public void setConnection();
	
	// Authenticate a user
	public Optional<User> authenticate(String email, String password);
	
	// Obtain the list of movies
	public List<Movie> getMovies();
	
	// Obtain the users favorite list
	public List<Movie> getFavorites(int userId);
	
	// Obtain the list of user's movie ratings
	public List<UserMovie> getUserMovie(int userId, int movieId);
	
	// Obtain a movie by Id
	public Optional<Movie> getMovieById(int movieId);
	
	// Rate a show
	public boolean rateMovie(int movieId);
	
	// Add a movie to favorites
	public boolean favoriteMovie(int userId, int movieId);
	
}
