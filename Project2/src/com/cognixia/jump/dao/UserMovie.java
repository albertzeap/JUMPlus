package com.cognixia.jump.dao;

public class UserMovie {
	
	// user_movie attributes
	private int userId;
	private int movieId;
	private int rating;
	private boolean favorite;
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getMovieId() {
		return movieId;
	}
	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public boolean isFavorite() {
		return favorite;
	}
	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
	}
	@Override
	public String toString() {
		return "UserMovie [userId=" + userId + ", movieId=" + movieId + ", rating=" + rating + ", favorite=" + favorite
				+ "]";
	}
	

	
}
