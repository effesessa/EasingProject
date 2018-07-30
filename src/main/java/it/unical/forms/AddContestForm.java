package it.unical.forms;

public class AddContestForm
{

	private String name;
	private String rankable;

	private String jury;
	private String subjectId;

	private String deadline;
	private String password;
	private boolean exam;

	public AddContestForm()
	{
		this.name = "";
		this.rankable = "";
		this.subjectId = null;
		this.deadline = null;
		this.password = "";
		this.exam = false;
	}

	public String getDeadline()
	{
		return deadline;
	}

	public String getJury()
	{
		return jury;
	}

	public String getName()
	{
		return name;
	}

	public String getPassword()
	{
		return password;
	}

	public String getRankable()
	{
		return rankable;
	}

	public String getSubjectId()
	{
		return subjectId;
	}

	public boolean isExam()
	{
		return exam;
	}

	public void setDeadline(String deadline)
	{
		this.deadline = deadline;
	}

	public void setExam(boolean exam)
	{
		this.exam = exam;
	}

	public void setJury(String jury)
	{
		this.jury = jury;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public void setRankable(String rankable)
	{
		this.rankable = rankable;
	}

	public void setSubjectId(String subjectId)
	{
		this.subjectId = subjectId;
	}
}