package pl.coderslab.model;

import java.time.LocalDateTime;

public class Solution {
	
	private long id;
	private LocalDateTime created;
	private LocalDateTime updated;
	private String description;
	private long exerciseId;
	private long userId;
	
	public Solution(long id, LocalDateTime created, LocalDateTime updated, String description, long exerciseId,
			long userId) {
		this.id = id;
		this.created = created;
		this.updated = updated;
		this.description = description;
		this.exerciseId = exerciseId;
		this.userId = userId;
	}

	public Solution() {
		this.id = 0L;
		this.created = LocalDateTime.now();
		this.updated = LocalDateTime.now();
		this.description = "";
		this.exerciseId = 0L;
		this.userId = 0L;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	@Override
	public String toString() {
		return "Solution [id=" + id + ", created=" + created + ", updated=" + updated + ", desc=" + description
				+ ", exerciseId=" + exerciseId + ", userId=" + userId + "]";
	}
	
	
	
	

}
