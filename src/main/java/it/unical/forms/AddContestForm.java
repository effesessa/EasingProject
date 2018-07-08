package it.unical.forms;

public class AddContestForm
{

	private String name;
	private String rankable;

	private String jury;
	private String subjectId;

	private String deadline;

	public AddContestForm()
	{
		this.name = "";
		this.rankable = "";
		this.subjectId = null;
		this.deadline = null;
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

	public String getRankable()
	{
		return rankable;
	}

	public String getSubjectId()
	{
		return subjectId;
	}

	public void setDeadline(String deadline)
	{
		this.deadline = deadline;
	}

	public void setJury(String jury)
	{
		this.jury = jury;
	}

	public void setName(String name)
	{
		this.name = name;
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