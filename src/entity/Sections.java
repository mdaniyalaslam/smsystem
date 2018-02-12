package entity;

import java.io.Serializable;

public class Sections implements Serializable{
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private char status;
	private int created_by;
	private String value;
	public Sections(String name, char status, int created_by, String value) {
		this.name = name;
		this.status = status;
		this.created_by = created_by;
		this.value = value;
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

	public char getStatus() {
		return status;
	}

	public void setStatus(char status) {
		this.status = status;
	}

	public int getCreated_by() {
		return created_by;
	}

	public void setCreated_by(int created_by) {
		this.created_by = created_by;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	
}
