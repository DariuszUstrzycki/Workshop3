package pl.coderslab.tests;

import static org.junit.jupiter.api.Assertions.fail;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pl.coderslab.dao.MySQLUserDao;
import pl.coderslab.db.DbUtil;
import pl.coderslab.model.User;

class UserDaoTest {
	
	private static final String TABLE = " user ";
	private static final String COLUMNS = " (id, username, email, password, user_group_id) ";

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		// create a new, empty user table
		
		
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		
	}

	@BeforeEach
	void setUp() throws Exception {
		//delete all entries from the user table
		
		String deleteAllSql = "DELETE FROM " +  TABLE ;
		
		try (Connection conn = getConnection(); 
				Statement stmt = conn.createStatement()) {
			
			stmt.executeUpdate(deleteAllSql);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}

	@AfterEach
	void tearDown() throws Exception {
		//delete all entries from the user table
	}

	//////////////////////////// test methods ////////////////////////////////
	
	
	@Test
	void testSave() {
		
		String insertUser1 = "INSERT INTO " + TABLE + COLUMNS
				+ " VALUES( default, 'ania', 'ania@wp.pl', '123', 1);";
		String insertUser2 = "INSERT INTO " + TABLE + COLUMNS
				+ " VALUES( default, 'kazek', 'kazek@wp.pl', 'abc', 2);";
		
		User user1 = new User();
		user1.setUsername("ania");
		user1.setEmail("ania@wp.pl");
		user1.setPassword("123");
		user1.setUserGroupId(1);
		
		MySQLUserDao dao = new MySQLUserDao();
		dao.save(user1);
		
		//int save(User user);
		
		
		
		fail("Not yet implemented");
	}
/*
	@Test
	void testUpdate() {
		fail("Not yet implemented");
	}

	@Test
	void testLoadUserById() {
		fail("Not yet implemented");
	}

	@Test
	void testLoadAllUsers() {
		fail("Not yet implemented");
	}

	@Test
	void testDelete() {
		fail("Not yet implemented");
	}

	@Test
	void testLoadUserByEmailAndPassword() {
		fail("Not yet implemented");
	}*/
	
	/////////////////// NON-TEST methods   ///////////////////////////////////////
	
	private static Connection getConnection() throws SQLException {
		String dbName = "school";
		System.out.println("JUNIT Connecting to database " + dbName);
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + dbName + "?useSSL=false", "root",
				"coderslab");
		System.out.println("JUNIT Connected to database " + dbName + "\n");
		return conn;
	}

}
