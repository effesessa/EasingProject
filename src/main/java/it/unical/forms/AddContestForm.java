package it.unical.forms;

public class AddContestForm {

	private String name;
	private String year;
	private String month;
	private String day;
	private String rankable;

	private String jury;
	public String getJury() {
		return jury;
	}

	public void setJury(String jury) {
		this.jury = jury;
	}

	private String subjectId;

	public AddContestForm()
	{
		this.name="";
		this.year="";
		this.month="";
		this.day="";
		this.rankable="";
		this.subjectId=null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getRankable() {
		return rankable;
	}

	public void setRankable(String rankable) {
		this.rankable = rankable;
	}

	public String getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}
}