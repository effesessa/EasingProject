package it.unical.forms;

import java.util.Map;

//the point to calculate the total score of the quiz i will get in back-end controller by accessing DAO
//form with primitives and collections of primitive
//if you want use name, not Id
public class SubmitQuizForm {
	
	private String teamName;
	
	private String quizName;
	
	//question text and 'chosen answer or open answer' text
	private Map<String, String> question_answer;

	/* I commented the constructor, I don't think there is need, in case of error uncomment it.
	public SubmitQuizForm() {
		teamName = null;
		quizName = null;
		question_answer = null;
	}*/
	
	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getQuizName() {
		return quizName;
	}

	public void setQuizName(String quizName) {
		this.quizName = quizName;
	}

	public Map<String, String> getQuestion_answer() {
		return question_answer;
	}

	public void setQuestion_answer(Map<String, String> question_answer) {
		this.question_answer = question_answer;
	}
	
}
