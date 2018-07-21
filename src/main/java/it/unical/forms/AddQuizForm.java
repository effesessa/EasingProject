package it.unical.forms;

import java.util.List;
import java.util.Map;

public class AddQuizForm
{

	private String contestName;

	// total points of a quiz (example 30)
	private int quizPoints;

	private String quizName;

	private List<String> questions;

	// type of questions
	private Map<String, String> question_types;

	// points of questions
	private List<Integer> points;

	// correct answers of questions
	private Map<String, String> correctAnswers;

	// map questions with more answers
	private Map<String, List<String>> questions_answers;

	private Map<String, String> questions_tags;

	public AddQuizForm()
	{
		questions = null;
		question_types = null;
		points = null;
		correctAnswers = null;
		questions_answers = null;
		quizName = null;
		quizPoints = -1;
		quizName = null;
		contestName = null;
	}

	public String getContestName()
	{
		return contestName;
	}

	public Map<String, String> getCorrectAnswers()
	{
		return correctAnswers;
	}

	public List<Integer> getPoints()
	{
		return points;
	}

	public Map<String, String> getQuestion_types()
	{
		return question_types;
	}

	public List<String> getQuestions()
	{
		return questions;
	}

	public Map<String, List<String>> getQuestions_answers()
	{
		return questions_answers;
	}

	public Map<String, String> getQuestions_tags()
	{
		return questions_tags;
	}

	public String getQuizName()
	{
		return quizName;
	}

	public int getQuizPoints()
	{
		return quizPoints;
	}

	public void setContestName(String contestName)
	{
		this.contestName = contestName;
	}

	public void setCorrectAnswers(Map<String, String> correctAnswers)
	{
		this.correctAnswers = correctAnswers;
	}

	public void setPoints(final List<Integer> points)
	{
		this.points = points;
	}

	public void setQuestion_types(Map<String, String> question_types)
	{
		this.question_types = question_types;
	}

	public void setQuestions(final List<String> questions)
	{
		this.questions = questions;
	}

	public void setQuestions_answers(final Map<String, List<String>> questions_answers)
	{
		this.questions_answers = questions_answers;
	}

	public void setQuestions_tags(Map<String, String> questions_tags)
	{
		this.questions_tags = questions_tags;
	}

	public void setQuizName(final String quizName)
	{
		this.quizName = quizName;
	}

	public void setQuizPoints(int quizPoints)
	{
		this.quizPoints = quizPoints;
	}
}
