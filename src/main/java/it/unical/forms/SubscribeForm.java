package it.unical.forms;

public class SubscribeForm
{

	private String team;
	private String contest;
	private String password;

	public SubscribeForm()
	{
		this.team = "";
		this.contest = "";
	}

	public String getContest()
	{
		return contest;
	}

	public String getPassword()
	{
		return password;
	}

	public String getTeam()
	{
		return team;
	}

	public void setContest(String contest)
	{
		this.contest = contest;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public void setTeam(String team)
	{
		this.team = team;
	}

}