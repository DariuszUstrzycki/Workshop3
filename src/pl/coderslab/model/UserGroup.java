package pl.coderslab.model;

public class UserGroup {

	private long id;
	private String name;

	public UserGroup() {
		this.id = 0L;
		this.name = "";
	}

	public UserGroup(long id, String name) {
		this.id = id;
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "UserGroup [id=" + id + ", name=" + name + "]";
	}
	
	

}
