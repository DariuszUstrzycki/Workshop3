package pl.coderslab.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.mysql.jdbc.Statement;

import pl.coderslab.db.DbUtil;
import pl.coderslab.model.UserGroup;

public class MySQLUserGroupDao implements UserGroupDao{

	/**
	 * @return newly created group's id or a -1 on error/failure
	 */
	@Override
	public int save(UserGroup group) {
		String sql = "INSERT INTO user_group( name ) " + "VALUES (?)";

		if (group.getId() == 0) {
			
			try (Connection conn = DbUtil.getConn(); 
					PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
				
				ps.setString(1, group.getName());
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
	public boolean update(UserGroup group) {
		String sql = "UPDATE user_group " + " SET name = ? "
				+ " WHERE id = ?";

		if (group.getId() != 0) {
			
			try (Connection conn = DbUtil.getConn(); 
					PreparedStatement ps = conn.prepareStatement(sql)) {
				
				ps.setString(1, group.getName());
				ps.setLong(2, group.getId());
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
	 * @return the UserGroup with the given id or null on error or if not found
	 */
	@Override
	public UserGroup loadUserGroupById(int id) {
		String sql = "SELECT * FROM	user_group WHERE id = ?";

		try (Connection conn = DbUtil.getConn(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, id);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return extractUserGroupFromRS(rs);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public Collection<UserGroup> loadAllUserGroups() {
		Collection<UserGroup> groups = null;
		String sql = "SELECT * FROM	user_group";
		
		try (Connection conn = DbUtil.getConn(); 
				PreparedStatement ps = conn.prepareStatement(sql)){
			
			try(ResultSet rs = ps.executeQuery()){
				groups = new ArrayList<>();
				while (rs.next()) {
					groups.add(extractUserGroupFromRS(rs));
				}				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return groups;
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
					PreparedStatement ps = conn.prepareStatement("DELETE FROM user_group WHERE id = ?")) {

				ps.setLong(1, id);
				int rowCount = ps.executeUpdate();
				return (rowCount == 0) ? false : true;
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return false;  
	}

	//////////////////////// helper methods ///////////////////////////////////

	private static UserGroup extractUserGroupFromRS(ResultSet rs) throws SQLException {

		UserGroup group = new UserGroup();
		group.setId(rs.getInt("id"));
		group.setName(rs.getString("name"));

		return group;
}

}
