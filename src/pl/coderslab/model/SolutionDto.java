package pl.coderslab.model;

import java.time.LocalDateTime;

public class SolutionDto {
	private String excerciseTitle;
	private long excerciseId; // new
	//-------------
	private long attachementId;
	private String attachmentName;
	//-------------
	private long solutionId;
	private LocalDateTime created;
	private LocalDateTime updated;
	private String description;
	private long exerciseId;
	private long userId;
	// ------------
	private String userName;
	public String getExcerciseTitle() {
		return excerciseTitle;
	}
	public void setExcerciseTitle(String excerciseTitle) {
		this.excerciseTitle = excerciseTitle;
	}
	public long getExcerciseId() {
		return excerciseId;
	}
	public void setExcerciseId(long excerciseId) {
		this.excerciseId = excerciseId;
	}
	public long getAttachementId() {
		return attachementId;
	}
	public void setAttachementId(long attachementId) {
		this.attachementId = attachementId;
	}
	public String getAttachmentName() {
		return attachmentName;
	}
	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}
	public long getSolutionId() {
		return solutionId;
	}
	public void setSolutionId(long solutionId) {
		this.solutionId = solutionId;
	}
	public LocalDateTime getCreated() {
		return created;
	}
	public void setCreated(LocalDateTime created) {
		this.created = created;
	}
	public LocalDateTime getUpdated() {
		return updated;
	}
	public void setUpdated(LocalDateTime updated) {
		this.updated = updated;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public long getExerciseId() {
		return exerciseId;
	}
	public void setExerciseId(long exerciseId) {
		this.exerciseId = exerciseId;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	
}