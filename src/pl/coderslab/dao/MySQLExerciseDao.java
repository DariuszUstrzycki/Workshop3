package pl.coderslab.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.mysql.jdbc.Statement;

import pl.coderslab.db.DbUtil;
import pl.coderslab.model.Exercise;

public class MySQLExerciseDao implements ExerciseDao {
	/**
	 * @return newly created exercise's id or a -1 on error/failure
	 */
	@Override
	public int save(Exercise ex) {
		
		
		String sql = "INSERT INTO exercise( title, description, user_id ) " + "VALUES (?, ?, ?)";

		if (ex.getId() == 0) {
			
			try (Connection conn = DbUtil.getConn(); 
					PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
				
				ps.setString(1, ex.getTitle());
				ps.setString(2, ex.getDescription());
				ps.setLong(3, ex.getUserId());	
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
	@Override
	public boolean update(Exercise ex) {
		String sql = "UPDATE exercise " + " SET title = ?, description = ? user_id = ? "
				+ " WHERE id = ?";

		if (ex.getId() != 0) {
			
			try (Connection conn = DbUtil.getConn(); 
					PreparedStatement ps = conn.prepareStatement(sql)) {
				
				ps.setString(1, ex.getTitle());
				ps.setString(2, ex.getDescription());	
				ps.setLong(3, ex.getUserId());
				ps.setLong(4, ex.getId());
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
	 * @return the Exercise with the given id or null on error or if not found
	 */
	@Override
	public Exercise loadExerciseById(int id) {
		String sql = "SELECT * FROM	exercise WHERE id = ?";
		
		try (Connection conn = DbUtil.getConn(); 
				PreparedStatement ps = conn.prepareStatement(sql)){
		
			ps.setInt(1, id);
			
			try(ResultSet rs = ps.executeQuery()){
				if (rs.next()) {
					return extractExerciseFromRS(rs);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	
	
	
	@Override
	public Collection<Exercise> loadAllExercises() {
		Collection<Exercise> exercises = null;
		String sql = "SELECT * FROM	exercise";
		
		try (Connection conn = DbUtil.getConn(); 
				PreparedStatement ps = conn.prepareStatement(sql)){
			
			try(ResultSet rs = ps.executeQuery()){
				exercises = new ArrayList<>();
				while (rs.next()) {
					exercises.add(extractExerciseFromRS(rs));
				}				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return exercises;
	}
	
	
	/**
	 * @return true on success, false on failure
	 */
	@Override
	public boolean delete(long id) {
	//  object is not in the db
			if (id == 0)
				return false;

			
			try (Connection conn = DbUtil.getConn();
					PreparedStatement ps = conn.prepareStatement("DELETE FROM exercise WHERE id = ?")) {

				ps.setLong(1, id);
				int rowCount = ps.executeUpdate();
				return (rowCount == 0) ? false : true;
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return false;  
	}

	//////////////////////// helper methods ///////////////////////////////////

	private static Exercise extractExerciseFromRS(ResultSet rs) throws SQLException {

		Exercise ex = new Exercise();
		ex.setId(rs.getInt("id"));
		ex.setTitle(rs.getString("title"));
		ex.setDescription(rs.getString("description"));
		ex.setUserId(rs.getLong("user_id"));
		return ex;
	}

}

