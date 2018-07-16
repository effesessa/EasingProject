package it.unical.forms;

import java.util.List;
import java.util.Map;

public class AddQuizForm {
	
	private int contestId;
	
	//total points of a quiz (example 30)
	private int quizPoints;
	
	private String quizName;
	
	private List<String> questions;
	
	//type of questions
	private List<String> types;
	
	//points of questions
	private List<Integer> points;
	
	//correct answers of questions
	private List<String> correctAnswers;
	
	//map questions with more answers
	private Map<String, List<String>> questions_answers;
	
	public AddQuizForm() {
		questions = null;
		types = null;
		points = null;
		correctAnswers = null;
		questions_answers = null;
		quizName = null;
		quizPoints = -1;
		quizName = null;
		contestId = -1;
	}
	
	public String getQuizName() {
		return quizName;
	}
	
	public void setQuizName(final String quizName) {
		this.quizName = quizName;
	}

	public List<String> getQuestions() {
		return questions;
	}

	public void setQuestions(final List<String> questions) {
		this.questions = questions;
	}

	public List<String> getTypes() {
		return types;
	}

	public void setTypes(final List<String> types) {
		this.types = types;
	}

	public List<Integer> getPoints() {
		return points;
	}

	public void setPoints(final List<Integer> points) {
		this.points = points;
	}

	public List<String> getCorrectAnswers() {
		return correctAnswers;
	}

	public void setCorrectAnswers(final List<String> correctAnswers) {
		this.correctAnswers = correctAnswers;
	}

	public Map<String, List<String>> getQuestions_answers() {
		return questions_answers;
	}

	public void setQuestions_answers(final Map<String, List<String>> questions_answers) {
		this.questions_answers = questions_answers;
	}
	
	public void setContestId(int contestId) {
		this.contestId = contestId;
	}
	
	public void setQuizPoints(int quizPoints) {
		this.quizPoints = quizPoints;
	}
	
	public int getContestId() {
		return contestId;
	}
	
	public int getQuizPoints() {
		return quizPoints;
	}
}
