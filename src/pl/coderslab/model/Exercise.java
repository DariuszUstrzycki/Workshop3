package pl.coderslab.model;

public class Exercise {

	private long id;
	private String title;
	private String description;
	private long userId;

	public Exercise(long id, String title, String description, long userId) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.userId = userId;
	}

	public Exercise(String title, String description, long userId) {
		this(0L, title, description, userId);
	}
	
	public Exercise() {
		this(0L, "", "", 0L);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "Exercise [id=" + id + ", title=" + title + ", description=" + description + ", userId=" + userId + "]";
	}

	

}
