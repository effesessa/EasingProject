package it.unical.forms;

import java.util.Map;

//the point to calculate the total score of the quiz i will get in back-end controller by accessing DAO
//form with primitives and collections of primitive
//if you want use name, not Id
public class SubmitQuizForm
{

	private String teamName;

	private Integer quizID;

	// question text and 'chosen answer or open answer' text
	private Map<String, String> question_answer;

	/*
	 * I commented the constructor, I don't think there is need, in case of
	 * error uncomment it. public SubmitQuizForm() { teamName = null; quizName =
	 * null; question_answer = null; }
	 */

	public Map<String, String> getQuestion_answer()
	{
		return question_answer;
	}

	public Integer getQuizID()
	{
		return quizID;
	}

	public String getTeamName()
	{
		return teamName;
	}

	public void setQuestion_answer(Map<String, String> question_answer)
	{
		this.question_answer = question_answer;
	}

	public void setQuizID(Integer quizID)
	{
		this.quizID = quizID;
	}

	public void setTeamName(String teamName)
	{
		this.teamName = teamName;
	}

}
