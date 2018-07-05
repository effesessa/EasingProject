package it.unical.forms;

public class SubjectPasswordForm{
	
	private String password;
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SubjectPasswordForm()
	{
		this.password="";
	}
	
	public String getpassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}