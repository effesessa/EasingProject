package it.unical.forms;

import java.util.List;
import java.util.Map;

public class QuizDTO
{
	private String contestName;

	private int quizPoints;

	private String quizName;

	private List<String> questions;

	private List<String> types;

	private List<Integer> points;

	private List<String> correctAnswers;

	private Map<String, List<String>> questions_answers;

	public String getContestName()
	{
		return contestName;
	}

	public List<String> getCorrectAnswers()
	{
		return correctAnswers;
	}

	public List<Integer> getPoints()
	{
		return points;
	}

	public List<String> getQuestions()
	{
		return questions;
	}

	public Map<String, List<String>> getQuestions_answers()
	{
		return questions_answers;
	}

	public String getQuizName()
	{
		return quizName;
	}

	public int getQuizPoints()
	{
		return quizPoints;
	}

	public List<String> getTypes()
	{
		return types;
	}

	public void setContestName(String contestName)
	{
		this.contestName = contestName;
	}

	public void setCorrectAnswers(List<String> correctAnswers)
	{
		this.correctAnswers = correctAnswers;
	}

	public void setPoints(List<Integer> points)
	{
		this.points = points;
	}

	public void setQuestions(List<String> questions)
	{
		this.questions = questions;
	}

	public void setQuestions_answers(Map<String, List<String>> questions_answers)
	{
		this.questions_answers = questions_answers;
	}

	public void setQuizName(String quizName)
	{
		this.quizName = quizName;
	}

	public void setQuizPoints(int quizPoints)
	{
		this.quizPoints = quizPoints;
	}

	public void setTypes(List<String> types)
	{
		this.types = types;
	}
}
