package it.unical.forms;

public class AddQuizForm {
	
	private String contestName;
	
	private String name;
	
	private int points;
	
	public AddQuizForm() {
		points = -1;
		name = null;
		contestName = null;
	}

	public String getContestName() {
		return contestName;
	}

	public void setContestName(final String contestName) {
		this.contestName = contestName;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(final int points) {
		this.points = points;
	}
	
}
