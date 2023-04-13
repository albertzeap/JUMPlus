package com.cognixia.jump.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.cognixia.jump.connection.BetterConnectionManager;

public class UserDaoSql implements UserDao {
	
	// Connection used to connect to the database
	private Connection conn;

	@Override
	public void setConnection() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException {
		conn = BetterConnectionManager.getConnection();
	}

	@Override
	public Optional<User> authenticate(String email, String password){
		
		try(PreparedStatement pstmnt = conn.prepareStatement("SELECT * from users where email = ? and password = ?")){
			
			// Fill in the parameters
			pstmnt.setString(1, email);
			pstmnt.setString(2, password);
			
			// Execute the query
			ResultSet rs = pstmnt.executeQuery();
			
			//Declare the attributes we need here
			int id = 0; 
			String uEmail = null;
			String pwd = null;
			String name = null;
			int roleType = 0;
			
			// While the query is reading the values
			while(rs.next()) {
				
				id = rs.getInt("userId");
				uEmail = rs.getString("email");
				pwd = rs.getString("password");
				name = rs.getString("name");
				roleType = rs.getInt("roleType");
			}
			
			rs.close();
			
			User user = new User(id, uEmail, pwd, name, roleType);
			
			Optional<User> userFound = Optional.of(user);
			
			return userFound;
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return Optional.empty();
	}

	@Override
	public List<Movie> getMovies() {
		
		try(PreparedStatement pstmnt = conn.prepareStatement("SELECT m.movieId, m.title, m.descript, COUNT(um.rating) AS num_ratings, ROUND(AVG(um.rating), 1) AS avg_rating FROM Movie m LEFT JOIN User_Movie um ON m.movieId = um.movieId GROUP BY m.movieId, m.title")) {
			
			// Execute the query
			ResultSet rs = pstmnt.executeQuery();
			
			// Movie Attributes
			int movieId = 0;
			String title = null;
			String description = null;
			
			//UserMovie attributes
			int userMovieId = 0;
			int userId = 0;
			int rating = 0;
			boolean favorite = false;
			int numRatings = 0;
			double avgRating = 0d;
			
			System.out.printf("%-5s %-40s %-15s %-15s\n", "ID", "Title", "# Ratings", "Avg Rating");
			System.out.println("\n----------------------------------------------------------------------------------------------------------------");
			while(rs.next()) {
			
				 movieId = rs.getInt("movieId");
				 title = rs.getString("title");
				 numRatings = rs.getInt("num_ratings");
				 avgRating = rs.getDouble("avg_rating");
	
				   
				System.out.printf("%-5d %-40s %-15d %-15.1f\n", movieId, title, numRatings, avgRating);
			}
			System.out.println();
		
			return null;
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
		return null;
	}

	@Override
	public List<Movie> getFavorites(int userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean getUserRatings(int userId) {
		
		try(PreparedStatement pstmnt = conn.prepareStatement("SELECT m.movieId, m.title, m.descript, um.rating FROM Movie m LEFT JOIN User_Movie um ON m.movieId = um.movieId LEFT JOIN Users u ON u.userId = um.userId WHERE u.userId = ?")){
			
			pstmnt.setInt(1, userId);
			ResultSet rs = pstmnt.executeQuery();
			
			
			int movieId = 0;
			String title = null;
			String descript = null;
			int rating = 0;
			
			System.out.println(String.format("| %-8s | %-40s | %-7s |", "Movie ID", "Title", "Rating"));
			System.out.println("-----------------------------------------------------");
			while(rs.next()) {
				
				movieId = rs.getInt("m.movieId");
				title = rs.getString("m.title");
				descript = rs.getString("m.descript");
				rating = rs.getInt("um.rating");
				System.out.println(String.format("| %-8s | %-40s | %-7s |", movieId, title, rating));
			}
			System.out.println();
			rs.close();
			
			return true;
			
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		
		
		return false;
	}

	@Override
	public Movie getMovieById(int id) {
	
		try(PreparedStatement pstmnt = conn.prepareStatement("select * from movie where movieId = ?")) {
			
			String myId = String.valueOf(id);		
			pstmnt.setInt(1, id);
			
			ResultSet rs = pstmnt.executeQuery();
			
			int movieId = -1;
			String title = null;
			String descript = null;
			
			while(rs.next()) {
				
				movieId = rs.getInt("movieId");
				title = rs.getString("title");
				descript = rs.getNString("descript");
				
				
			}
			rs.close();
			
			// constructing the object
			Movie movie = new Movie(movieId, title, descript);
			
			if(movie.getTitle() == null) {
				throw new Exception("Please enter a movie ID that is present in the table\n");
			}
			
			return movie;
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		
		return null;
	}

	@Override
	public boolean rateMovie(Movie movie, User user, int rating) {
		
		try(PreparedStatement pstmnt = conn.prepareStatement("insert into user_movie (userId, movieId, rating) values (?,?, ?)")) {
			
			pstmnt.setInt(1, user.getUserId() );
			pstmnt.setInt(2, movie.getMovieId());
			pstmnt.setInt(3, rating);
			
			pstmnt.executeUpdate();
			
			return true;
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		
		return false;
	}

	@Override
	public boolean favoriteMovie(int userId, int movieId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean createAccount(User newUser) {
		
		try(PreparedStatement pstmnt = conn.prepareStatement("INSERT INTO users (email, password, name) values (?,?,?)")) {
			
			pstmnt.setString(1, newUser.getEmail());
			pstmnt.setString(2, newUser.getPassword());
			pstmnt.setString(3, newUser.getName());
			
			pstmnt.executeUpdate();
			
			return true;
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		return false;
	}

}
