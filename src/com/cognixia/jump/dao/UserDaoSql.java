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
			int roleType = 0;
			
			// While the query is reading the values
			while(rs.next()) {
				
				id = rs.getInt("userId");
				uEmail = rs.getString("email");
				pwd = rs.getString("password");
				roleType = rs.getInt("roleType");
			}
			
			User user = new User(id, uEmail, pwd, roleType);
			
			Optional<User> userFound = Optional.of(user);
			
			return userFound;
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return Optional.empty();
	}

	@Override
	public List<Movie> getMovies() {
		// TODO Auto-generated method stub
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

}
