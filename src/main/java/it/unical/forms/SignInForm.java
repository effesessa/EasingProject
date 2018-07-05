package it.unical.forms;


public class SignInForm {

	private String email;
	private String password;
	private String name;
	private String surname;
	private String id;
	
	public SignInForm() {
		this.email = "";
		this.password = "";
		this.name = "";
		this.surname = "";
		this.id = "";
	}
	
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
}
