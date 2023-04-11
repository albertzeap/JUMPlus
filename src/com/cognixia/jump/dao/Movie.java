package com.cognixia.jump.dao;

public class Movie {

	// movie attributes
	private int movieId;
	private String title;
	private String descript;
	
	public Movie() {
		
	}
	
	public Movie(int movieId, String title, String descript) {
		super();
		this.movieId = movieId;
		this.title = title;
		this.descript = descript;
	}

	public int getMovieId() {
		return movieId;
	}

	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescript() {
		return descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}

	@Override
	public String toString() {
		return "Movie [movieId=" + movieId + ", title=" + title + ", descript=" + descript + "]";
	}
	
}
