package pl.coderslab.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import pl.coderslab.db.DbUtil;
import pl.coderslab.model.Solution;
import pl.coderslab.model.SolutionDto;

/*private String excerciseTitle;
private String excerciseId; // new
//-------------
private long attachementId;
private String attachmentName;
//-------------
private long solutionId;
private LocalDateTime created;
private LocalDateTime updated;
private String content;
private long exerciseId;
private long userId;*/

public class SolutionDtoDao {  //dodac user name
	
	
	public static List<SolutionDto> loadSolutionDto() {
		
		ArrayList<SolutionDto> solutionsDto = null;
		
		String sql = "SELECT  " + 
				"    exercise.id, " + 
				"    exercise.title, " + 
				"    attachment.id, " + 
				"    attachment.attachment_name, " + 
				"    solution.id, " + 
				"    solution.created, " + 
				"    solution.updated, " + 
				"    solution.description, " + 
				"    solution.exercise_id, " + 
				"    solution.user_id, " + 
				"    user.username " + 
				"FROM solution " + 
				"LEFT JOIN  attachment ON attachment.solution_id = solution.id   " + 
				"LEFT JOIN user ON user.id =  solution.user_id " + 
				"LEFT JOIN exercise ON exercise.id = solution.exercise_id        " + 
				"ORDER BY solution.created DESC; ";
		
		try (Connection conn = DbUtil.getConn(); 
				PreparedStatement ps = conn.prepareStatement(sql)){
			
			try(ResultSet rs = ps.executeQuery()){
				solutionsDto = new ArrayList<>();
				while (rs.next()) {
					solutionsDto.add(extractSolutionDtoFromRS(rs));
				}				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println("solutionsDto size in SolutionDTO; " + solutionsDto.size());
		return solutionsDto;
		
	}
	
private static SolutionDto extractSolutionDtoFromRS(ResultSet rs) throws SQLException {
		
	SolutionDto loaded = new SolutionDto();
	loaded.setExcerciseId(rs.getLong("exercise.id"));
	loaded.setExcerciseTitle(rs.getString("exercise.title"));
	loaded.setAttachementId(rs.getLong("attachment.id"));
	loaded.setAttachmentName(rs.getString("attachment.attachment_name"));
	loaded.setSolutionId(rs.getLong("solution.id"));
	loaded.setCreated(LocalDateTime.ofInstant(rs.getTimestamp("solution.created").toInstant(), ZoneOffset.ofHours(0)));
	loaded.setUpdated(LocalDateTime.ofInstant(rs.getTimestamp("solution.updated").toInstant(), ZoneOffset.ofHours(0)));
	loaded.setDescription(rs.getString("solution.description"));
	loaded.setExcerciseId(rs.getLong("solution.exercise_id"));
	loaded.setUserId(rs.getLong("solution.user_id"));
	loaded.setUserName(rs.getString("user.username"));

		return loaded;
	}
	
	

}
