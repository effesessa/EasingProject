package it.unical.forms;

import java.util.List;
import java.util.Map;

public class AddQuestionsForm {
	
	private List<String> questions;

	private Map<String, String> question_types;

	private Map<String, String> question_points;

	private Map<String, String> question_correctAnswer;

	private Map<String, List<String>> question_answers;

	private Map<String, String> question_tags;
	
	public AddQuestionsForm() {
		questions = null;
		question_types = null;
		question_points = null;
		question_correctAnswer = null;
		question_answers = null;
		question_tags = null;
	}

	public List<String> getQuestions() {
		return questions;
	}

	public void setQuestions(List<String> questions) {
		this.questions = questions;
	}

	public Map<String, String> getQuestion_types() {
		return question_types;
	}

	public void setQuestion_types(Map<String, String> question_types) {
		this.question_types = question_types;
	}

	public Map<String, String> getQuestion_points() {
		return question_points;
	}

	public void setQuestion_points(Map<String, String> question_points) {
		this.question_points = question_points;
	}

	public Map<String, String> getQuestion_correctAnswer() {
		return question_correctAnswer;
	}

	public void setQuestion_correctAnswer(Map<String, String> question_correctAnswer) {
		this.question_correctAnswer = question_correctAnswer;
	}

	public Map<String, List<String>> getQuestion_answers() {
		return question_answers;
	}

	public void setQuestion_answers(Map<String, List<String>> question_answers) {
		this.question_answers = question_answers;
	}

	public Map<String, String> getQuestion_tags() {
		return question_tags;
	}

	public void setQuestion_tags(Map<String, String> question_tags) {
		this.question_tags = question_tags;
	}
}
