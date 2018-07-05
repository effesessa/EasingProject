package it.unical.forms;

public class AddTeamForm
{

	private String name;
	private String member2;
	private String member3;

	public AddTeamForm()
	{
		this.name = "";
		this.member2 = "";
		this.member3 = "";
	}

	public String getMember2()
	{
		return member2;
	}

	public String getMember3()
	{
		return member3;
	}

	public String getName()
	{
		return name;
	}

	public void setMember2(String member2)
	{
		this.member2 = member2;
	}

	public void setMember3(String member3)
	{
		this.member3 = member3;
	}

	public void setName(String name)
	{
		this.name = name;
	}

}
