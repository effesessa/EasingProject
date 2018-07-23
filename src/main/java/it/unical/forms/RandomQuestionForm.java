package it.unical.forms;

import java.util.List;

public class RandomQuestionForm {
	
	private List<String> questionTagValues;
	
	private List<Integer> numberOfQuestions;
	
//	Ale if you pass tag like string separated by comma
//	private String questionTagValues;

	public RandomQuestionForm() {
		questionTagValues = null;
		numberOfQuestions = null;
	}
//	Ale if you pass tag like string separated by comma
//	public String getQuestionTagValues() {
//		return questionTagValues;
//	}
//	Ale if you pass tag like string separated by comma
//	public void setQuestiontagValues(String questionTagValues) {
//		this.questionTagValues = questionTagValues;
//	}
	
	public List<String> getQuestionTagValues() {
		return questionTagValues;
	}
	
	public void setQuestionTagValues(List<String> questionTagValues) {
		this.questionTagValues = questionTagValues;
	}

	public List<Integer> getNumberOfQuestions() {
		return numberOfQuestions;
	}

	public void setNumberOfQuestions(List<Integer> numberOfQuestions) {
		this.numberOfQuestions = numberOfQuestions;
	}
	
}
