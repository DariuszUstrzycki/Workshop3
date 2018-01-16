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
import pl.coderslab.model.Attachment;
import pl.coderslab.model.Solution;

public class MySQLAttachmentDao implements AttachmentDao {

	@Override
	public int save(Attachment attachment, String attachedTo) {
		
		String foreignKey = "";
		
		switch(attachedTo) {
		case "solution":
			foreignKey = "solution_id";
			break;
		case "exercise":
			foreignKey = "exercise_id";
			// TODO
			break;		
		}
		
		String sql = "INSERT INTO attachment (" + foreignKey + ", attachment_name,  mime_content_type, contents ) " 
				+ "VALUES (?, ?, ?, ?)";
		

		if (attachment.getId() == 0) {
			
			try (Connection conn = DbUtil.getConn(); 
					PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
				
				ps.setLong(1, attachment.getAttachedToId());
				ps.setString(2, attachment.getName());
				ps.setString(3, attachment.getMimeType());
				ps.setBytes(4, attachment.getContents());
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
	 * @return the Solution with the given id or null on error or if not found
	 */
	@Override
	public Attachment loadAttachmentById(int attachementId, String attachedToTableName) {
		
		String foreignKey = "";

		switch (attachedToTableName) {
		case "solution":
			foreignKey = "solution_id";
			break;
		case "exercise":
			foreignKey = "exercise_id";
			// TODO
			break;
		}
		
		String sql = "SELECT * FROM	attachment WHERE id= ?";
		
		try (Connection conn = DbUtil.getConn(); 
				PreparedStatement ps = conn.prepareStatement(sql)){
		
			ps.setLong(1, attachementId);
			
			try(ResultSet rs = ps.executeQuery()){
				if (rs.next()) {
					return extractAttachmentFromRS(rs, foreignKey);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	

	@Override
	public Collection<Attachment> loadAttachmentByAttachedToId(long attachedToId, String attachedToTableName) {
		
		String foreignKey = "";

		switch (attachedToTableName) {
		case "solution":
			foreignKey = "solution_id";
			break;
		case "exercise":
			foreignKey = "exercise_id";
			// TODO
			break;
		}
		////////////
		
		Collection<Attachment> attachments = null;
		String sql = "SELECT * FROM attachment " + 
				     " WHERE " + foreignKey + "= ? "
				     + " ORDER BY attachment_name";
		
		try (Connection conn = DbUtil.getConn(); 
				PreparedStatement ps = conn.prepareStatement(sql)){
			
			ps.setLong(1, attachedToId);
			try(ResultSet rs = ps.executeQuery()){
				attachments = new ArrayList<>();
				while (rs.next()) {
					attachments.add(extractAttachmentFromRS(rs, foreignKey));
				}				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return attachments;
	}

	@Override
	public boolean delete(long id) {
	//  object is not in the db
					if (id == 0)
						return false;

					
					try (Connection conn = DbUtil.getConn();
							PreparedStatement ps = conn.prepareStatement("DELETE FROM attachment WHERE id = ?")) {

						ps.setLong(1, id);
						int rowCount = ps.executeUpdate();
						return (rowCount == 0) ? false : true;
						
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
					return false;  
	}
	
////////////////////////helper methods ///////////////////////////////////

	private static Attachment extractAttachmentFromRS(ResultSet rs, String foreigKey) throws SQLException {

		Attachment attachment = new Attachment();
		attachment.setId(rs.getInt("id"));
		attachment.setAttachedToId(rs.getInt(foreigKey));
		attachment.setName(rs.getString("attachment_name"));
		attachment.setMimeType(rs.getString("mime_content_type"));
		attachment.setContents(rs.getBytes("contents"));

		return attachment;
	}

}
