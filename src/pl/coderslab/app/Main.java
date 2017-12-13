package pl.coderslab.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;

import pl.coderslab.model.User;

public class Main {

	public static void main(String[] args) {
		Connection conn = null;
		try {
			conn = getConnection();
			
			// saveUserTest(conn);
			//loadUserByIDTest(conn);
			loadAllUsersTest(conn);
			updateUserTest(conn);
			loadAllUsersTest(conn);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}

	}

	private static Connection getConnection() throws SQLException {
		String dbName = "school";
		System.out.println("Connecting to database " + dbName);
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + dbName + "?useSSL=false", "root",
				"coderslab");
		System.out.println("Connected to database " + dbName + "\n");
		return conn;
	}

	private static void saveNewUserTest(Connection conn) {

		/*
		 * User user1 = new User(); user1.setUsername("darek");
		 * user1.setPassword("123"); user1.setEmail("ddd@wp.pl");
		 */

		User user1 = new User();
		user1.setUsername("john");
		user1.setPassword("123");
		user1.setEmail("john@wp.pl");
		try {
			user1.saveToDB(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private static void loadUserByIDTest(Connection conn) {

		try {
			User user = User.loadUserById(conn, 3);
			System.out.println(user);

			User nullUser = User.loadUserById(conn, 2000);
			System.out.println(nullUser);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

	}
	
	private static void loadAllUsersTest(Connection conn) {

		try {
			User[] users = User.loadAllUsers(conn);
			System.out.println("Expected number of users: 2. Actual:" +  users.length);
			
			for (int i = 0; i < users.length; i++) {
				System.out.println(users[i]);
			}
			/*Obiekty	mają	wszystkie	dane	takie	same
			jak	zapisane	w	bazie	danych	(wystarczy
					sprawdzić	jeden	losowy	obiekt)*/

		} catch (SQLException e1) {
			e1.printStackTrace();
		}

	}
	
	private static void updateUserTest(Connection conn) {

		/*
		 * User user1 = new User(); user1.setUsername("darek");
		 * user1.setPassword("123"); user1.setEmail("ddd@wp.pl");
		 */
		
		try {
			User existingUser = User.loadUserById(conn, 3); // User user = User.loadUserById(conn, 3);
			// the same id is preserved
			existingUser.setUsername("jo");
			existingUser.setEmail("jo@wp.pl");
			existingUser.setUserGroupId(2);
			existingUser.saveToDB(conn);
			
			/*Następnie	należy	sprawdzić,	czy:
				1.	 Czy	wpis	w	bazie	danych	ma	wszystkie
				dane	odpowiednio	ponastawiane?
				2.	 Czy	obiekt	ma	nastawione	poprawne	id? - czyli niezmienione, bo nie mamy settera dla id
				3.	 Czy	nie	dodał	się	nowy	wpis	w	bazie?*/
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
