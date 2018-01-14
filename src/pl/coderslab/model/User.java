package pl.coderslab.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.mindrot.jbcrypt.BCrypt;

public class User {

	private long id;
	private String username;
	private String password;
	private String email;
	private int userGroupId;
	
	public User(long id, String username, String email, String password, int userGroupId) {
		this.id = id;
		this.username = username;
		this.email = email;
		setPassword(password);
		this.userGroupId = userGroupId;
	}

	public User(String username, String email, String password, int userGroupId) {
		this(0L, username, email, password, 0);
	}

	public User() {
		this(0L, "", "", "", 0);
	}

	public long getId() {
		return id;
	}

	
	 public void setId(long id) { 
		 this.id = id; }
	 

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;         //= BCrypt.hashpw(password, BCrypt.gensalt());
	}

	/*public boolean checkPassword(String candidate) {
		return BCrypt.checkpw(candidate, this.password);
	}
	
	public static String hashPassword(String password) {
		return BCrypt.hashpw(password, BCrypt.gensalt());
	}*/

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getUserGroupId() {
		return userGroupId;
	}

	public void setUserGroupId(int userGroupId) {
		this.userGroupId = userGroupId;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", email=" + email
				+ ", userGroupId=" + userGroupId + "]";
	}

	//////////////////////////// DAO methods
	//////////////////////////// ///////////////////////////////////////////

	public void saveToDB(Connection conn) throws SQLException {

		if (this.id == 0) {
			// Zapisujemy obiekt do bazy, tylko wtedy gdy jego id jest równe 0.
			String sql = "INSERT INTO user(username, email, password, user_group_id) " + "VALUES (?, ?,	?, ?)";
			String generatedColumns[] = { "ID" };

			PreparedStatement preparedStatement;
			preparedStatement = conn.prepareStatement(sql, generatedColumns);
			preparedStatement.setString(1, this.username);
			preparedStatement.setString(2, this.email);
			preparedStatement.setString(3, this.password);
			preparedStatement.setInt(4, this.userGroupId);
			preparedStatement.executeUpdate();

			ResultSet rs = preparedStatement.getGeneratedKeys();
			if (rs.next()) {
				this.id = rs.getInt(1);
			}
		} else { // this part updates an existing record

			String sql = "UPDATE user " + " SET username = ?, email = ?, password = ?, user_group_id = ? "
					+ " WHERE id = ?";
			PreparedStatement preparedStatement;
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, this.username);
			preparedStatement.setString(2, this.email);
			preparedStatement.setString(3, this.password);
			preparedStatement.setInt(4, this.userGroupId);
			preparedStatement.setLong(5, this.id);
			preparedStatement.executeUpdate();
		}
	}

	/*
	 * Wszystkie metody wczytujące obiekty z bazy danych będą statyczne – nie
	 * potrzebujemy przecież żadnego użytkownika (instancji obiektu), żeby wczytać
	 * innych użytkowników.
	 */

	static public User loadUserById(Connection conn, int id) throws SQLException {
		String sql = "SELECT * FROM	user WHERE id = ?";
		PreparedStatement preparedStatement;
		preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setInt(1, id);
		ResultSet resultSet = preparedStatement.executeQuery();

		if (resultSet.next()) {
			User loadedUser = new User();
			loadedUser.id = resultSet.getInt("id");
			loadedUser.username = resultSet.getString("username");
			loadedUser.password = resultSet.getString("password");
			loadedUser.email = resultSet.getString("email");
			return loadedUser;
		}

		return null;
	}

	static public User[] loadAllUsers(Connection conn) throws SQLException {
		ArrayList<User> users = new ArrayList<User>();
		String sql = "SELECT * FROM	user";
		PreparedStatement preparedStatement;
		preparedStatement = conn.prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();

		while (resultSet.next()) {
			User loadedUser = new User();
			loadedUser.id = resultSet.getInt("id");
			loadedUser.username = resultSet.getString("username");
			loadedUser.password = resultSet.getString("password");
			loadedUser.email = resultSet.getString("email");
			users.add(loadedUser);
		}
		// convert List to array
		User[] uArray = new User[users.size()];
		uArray = users.toArray(uArray);
		return uArray;
	}

	public void delete(Connection conn) throws SQLException {
		
		// do nothing if the object is not in the db
		if (this.id != 0) {
			String sql = "DELETE FROM Users	WHERE id = ?";
			PreparedStatement preparedStatement;
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setLong(1, this.id);
			preparedStatement.executeUpdate();
			// id re-set for an object NOT in db
			this.id = 0;
		}
	}

}
