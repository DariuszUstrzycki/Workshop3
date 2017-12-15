package pl.coderslab.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.mysql.jdbc.Statement;

import pl.coderslab.db.DbUtil;
import pl.coderslab.model.User;

public class MySQLUserDao implements UserDao{
	
	/**
	 * @return newly created user's id or a -1 on error/failure
	 */
	public int save(User user) {
		
		String sql = "INSERT INTO user(username, email, password, user_group_id) " + "VALUES (?, ?,	?, ?)";

		if (user.getId() == 0) {
			
			try (Connection conn = DbUtil.getConn(); 
					PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
				
				ps.setString(1, user.getUsername());
				ps.setString(2, user.getEmail());
				ps.setString(3, user.getPassword());
				ps.setInt(4, user.getUserGroupId());
				ps.executeUpdate();

				ResultSet rs = ps.getGeneratedKeys();
				if (rs.next()) {
					return rs.getInt(1);
				} 
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return -1;
	}
	
	/**
	 * @return true on success, false on failure or error
	 */
	public boolean update(User user) {
		String sql = "UPDATE user " + " SET username = ?, email = ?, password = ?, user_group_id = ? "
				+ " WHERE id = ?";

		if (user.getId() != 0) {
			
			try (Connection conn = DbUtil.getConn(); 
					PreparedStatement ps = conn.prepareStatement(sql)) {
				
				ps.setString(1, user.getUsername());
				ps.setString(2, user.getEmail());
				ps.setString(3, user.getPassword());
				ps.setInt(4, user.getUserGroupId());
				ps.setLong(5, user.getId());
				int rowCount = ps.executeUpdate();

				if (rowCount != 0) {
					return true;
				} 
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return false;
	  }
	

	/**
	 * @return the User with the given id or null on error or if not found
	 */
	public  User loadUserById(int id) {
		String sql = "SELECT * FROM	user WHERE id = ?";
		
		try (Connection conn = DbUtil.getConn(); 
				PreparedStatement ps = conn.prepareStatement(sql)){
		
			ps.setInt(1, id);
			
			try(ResultSet rs = ps.executeQuery()){
				if (rs.next()) {
					return extractUserFromRS(rs);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	
	public  Collection<User> loadAllUsers() {
		
		Collection<User> users = null;
		String sql = "SELECT * FROM	user";
		
		try (Connection conn = DbUtil.getConn(); 
				PreparedStatement ps = conn.prepareStatement(sql)){
			
			try(ResultSet rs = ps.executeQuery()){
				users = new ArrayList<>();
				while (rs.next()) {
					users.add(extractUserFromRS(rs));
				}				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return users;
	}
	
	/**
	 * @return true on success, false on failure
	 */
	public boolean delete(long id) {

		//  object is not in the db
		if (id == 0)
			return false;

		
		try (Connection conn = DbUtil.getConn();
				PreparedStatement ps = conn.prepareStatement("DELETE FROM user WHERE id = ?")) {

			ps.setLong(1, id);
			int rowCount = ps.executeUpdate();
			return (rowCount == 0) ? false : true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;  
	}
	//////////////////////// helper methods ///////////////////////////////////
	
	private static User extractUserFromRS(ResultSet rs) throws SQLException {
		
		User user = new User();
		user.setId(rs.getInt("id"));
		user.setUsername(rs.getString("username"));
		user.setPassword(rs.getString("password"));
		user.setUserGroupId(rs.getInt("user_group_id"));
		
		return user;
	}

}
