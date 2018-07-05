package it.unical.forms;

public class AddSubjectForm
{

	private String id;
	private String year;
	private String name;
	private String password;
	private String user_professor;
	private String url;

	public AddSubjectForm()
	{
		this.id = "";
		this.name = "";
		this.year = "";
		this.password = "";
		this.user_professor = "";
		this.url = "";
	}

	public String getId()
	{
		return id;
	}

	public String getName()
	{
		return name;
	}

	public String getPassword()
	{
		return password;
	}

	public String getUrl()
	{
		return url;
	}

	public String getUser_professor()
	{
		return user_professor;
	}

	public String getYear()
	{
		return year;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public void setUser_professor(String user_professor)
	{
		this.user_professor = user_professor;
	}

	public void setYear(String year)
	{
		this.year = year;
	}

}