package pl.coderslab.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collection;

import com.mysql.jdbc.Statement;

import pl.coderslab.db.DbUtil;
import pl.coderslab.model.Solution;

public class MySQLSolutionDao implements SolutionDao{
	/**
	 * @return newly created solution's id or a -1 on error/failure
	 */
	@Override
	public int save(Solution sol) {
		
		String sql = "INSERT INTO solution ( created, updated, description, exercise_id, user_id ) " + "VALUES (?, ?, ?, ?, ?)";

		if (sol.getId() == 0) {
			
			try (Connection conn = DbUtil.getConn(); 
					PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
				
				ps.setTimestamp(1, Timestamp.valueOf(sol.getCreated()));
				ps.setTimestamp(2, Timestamp.valueOf(sol.getUpdated()));
				ps.setString(3, sol.getDescription());
				ps.setLong(4, sol.getExerciseId());
				ps.setLong(5, sol.getUserId());
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
	public boolean update(Solution sol) {
		String sql = "UPDATE solution " + " SET created = ?, updated = ?, description = ?, exercise_id = ?, user_id = ?  "
				+ " WHERE id = ?";

		if (sol.getId() != 0) {
			
			try (Connection conn = DbUtil.getConn(); 
					PreparedStatement ps = conn.prepareStatement(sql)) {
				
				ps.setTimestamp(1, Timestamp.valueOf(sol.getCreated()));
				ps.setTimestamp(2, Timestamp.valueOf(sol.getUpdated()));
				ps.setString(3, sol.getDescription());
				ps.setLong(4, sol.getExerciseId());
				ps.setLong(5, sol.getUserId());	
				ps.setLong(6, sol.getId());
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
	 * @return the Solution with the given id or null on error or if not found
	 */
	@Override
	public Solution loadSolutionById(int id) {
		
		String sql = "SELECT * FROM	solution WHERE id = ?";
		
		try (Connection conn = DbUtil.getConn(); 
				PreparedStatement ps = conn.prepareStatement(sql)){
		
			ps.setInt(1, id);
			
			try(ResultSet rs = ps.executeQuery()){
				if (rs.next()) {
					return extractSolutionFromRS(rs);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}
	

	@Override
	public Collection<Solution> loadSolutionsByExId(long exerciseId) {
		Collection<Solution> solutions = null;
		String sql = "SELECT * FROM solution " + 
				     " WHERE exercise_id=? "
				     + " ORDER BY created DESC";
		
		try (Connection conn = DbUtil.getConn(); 
				PreparedStatement ps = conn.prepareStatement(sql)){
			
			ps.setLong(1, exerciseId);
			try(ResultSet rs = ps.executeQuery()){
				solutions = new ArrayList<>();
				while (rs.next()) {
					solutions.add(extractSolutionFromRS(rs));
				}				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return solutions;
	}
	
	@Override
	public Collection<Solution> loadSolutionsByUserId(long userId) {
		Collection<Solution> solutions = null;
		String sql = "SELECT * FROM solution " + 
				     " WHERE user_id=? "
				     + " ORDER BY created DESC";
		
		try (Connection conn = DbUtil.getConn(); 
				PreparedStatement ps = conn.prepareStatement(sql)){
			
			ps.setLong(1, userId);
			try(ResultSet rs = ps.executeQuery()){
				solutions = new ArrayList<>();
				while (rs.next()) {
					solutions.add(extractSolutionFromRS(rs));
				}				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return solutions;
	}
	
	

	@Override
	public Collection<Solution> loadAllSolutions() {
		Collection<Solution> solutions = null;
		String sql = "SELECT * FROM	solution "
				+ " ORDER BY created DESC";
		
		try (Connection conn = DbUtil.getConn(); 
				PreparedStatement ps = conn.prepareStatement(sql)){
			
			try(ResultSet rs = ps.executeQuery()){
				solutions = new ArrayList<>();
				while (rs.next()) {
					solutions.add(extractSolutionFromRS(rs));
				}				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return solutions;
	}
	
	
	
	
	@Override
	public Collection<Solution> loadAllSolutions(int limit) {
		Collection<Solution> solutions = null;
		String sql = "SELECT * FROM	solution "
				+ " ORDER BY created DESC LIMIT ? ";
		
		try (Connection conn = DbUtil.getConn(); 
				PreparedStatement ps = conn.prepareStatement(sql)){
			
			ps.setInt(1, limit);
			try(ResultSet rs = ps.executeQuery()){
				solutions = new ArrayList<>();
				while (rs.next()) {
					solutions.add(extractSolutionFromRS(rs));
				}				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return solutions;
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
						PreparedStatement ps = conn.prepareStatement("DELETE FROM solution WHERE id = ?")) {

					ps.setLong(1, id);
					int rowCount = ps.executeUpdate();
					return (rowCount == 0) ? false : true;
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				return false;  
	}
	
	//////////////////////// helper methods ///////////////////////////////////

	private static Solution extractSolutionFromRS(ResultSet rs) throws SQLException {
		
		Solution sol = new Solution();
		sol.setId(rs.getInt("id"));
		sol.setCreated(LocalDateTime.ofInstant(rs.getTimestamp("created").toInstant(), ZoneOffset.ofHours(0)));
		sol.setUpdated(LocalDateTime.ofInstant(rs.getTimestamp("updated").toInstant(), ZoneOffset.ofHours(0)));
		sol.setDescription(rs.getString("description"));
		sol.setExerciseId(rs.getLong("exercise_id"));
		sol.setUserId(rs.getLong("user_id"));

		return sol;
	}

	

}
