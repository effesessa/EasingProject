package it.unical.forms;

import java.util.List;
import java.util.Map;

public class ManageExamForm
{
	private int contestID;
	// private List<Integer> minProblems;
	// private List<Integer> minPoints;

	private Map<String, Integer> minProblems;
	private Map<String, Integer> minPoints;

	public ManageExamForm()
	{
	}

	public int getContestID()
	{
		return contestID;
	}

	public Map<String, Integer> getMinPoints()
	{
		return minPoints;
	}

	public Map<String, Integer> getMinProblems()
	{
		return minProblems;
	}

	public void setContestID(int contestID)
	{
		this.contestID = contestID;
	}

	public void setMinPoints(Map<String, Integer> minPoints)
	{
		this.minPoints = minPoints;
	}

	public void setMinProblems(Map<String, Integer> minProblems)
	{
		this.minProblems = minProblems;
	}
}
