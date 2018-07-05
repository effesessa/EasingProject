package it.unical.forms;

public class SubscribeForm{
	
	private String team;
	private String contest;

	public SubscribeForm()
	{
		this.team="";
		this.contest="";
	}
	
	public String getContest() {
		return contest;
	}

	public void setContest(String contest) {
		this.contest = contest;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}
	
	
}