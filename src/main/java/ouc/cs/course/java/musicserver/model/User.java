package ouc.cs.course.java.musicserver.model;

public class User {

	private int id;
	private String name;
	private String passMd5value;

	public User() {
	}

	public User(String passMd5value, String name) {
		this.setPassMd5value(passMd5value);
		this.name = name;

	}

	public User(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public User(int id, String name, String passMd5value) {
		this.id = id;
		this.name = name;
		this.passMd5value = passMd5value;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassMd5value() {
		return passMd5value;
	}

	public void setPassMd5value(String passMd5value) {
		this.passMd5value = passMd5value;
	}

	
}