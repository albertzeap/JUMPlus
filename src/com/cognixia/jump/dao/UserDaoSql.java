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
		
		try(PreparedStatement pstmnt = conn.prepareStatement("select * from movie join user_movie on user_movie.movieId = movie.movieId")) {
			
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
			
			System.out.printf("%10s %20s %20s %-10s%n", "Movie ID","Title", "Description", "Rating");
			System.out.println("\n----------------------------------------------------------------------------------------------------------------");
			while(rs.next()) {
				
				// Movie attributes
				movieId = rs.getInt("movie.movieId");
				title = rs.getString("movie.title");
				description = rs.getString("movie.descript");
				
				//UserMovie attributes
				userMovieId = rs.getInt("user_movie.movieId");
				userId = rs.getInt("user_movie.userId");
				rating = rs.getInt("user_movie.rating");
				favorite = rs.getBoolean("user_movie.favorite");
				
				System.out.printf("%10s %20s %20s %-10s%n", movieId,title, description, rating);	
			}
			
//			System.out.print(movieId + " " + title + " " + description + rating +"\n");
		
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
	public List<UserMovie> getUserMovie(int userId, int movieId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Movie> getMovieById(int movieId) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public boolean rateMovie(int movieId) {
		// TODO Auto-generated method stub
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
