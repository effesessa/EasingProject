package it.unical.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "submit_answer")
public class SubmitAnswer implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
 	@Column(name = "id")
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "idsubmitquiz", nullable = false)
	private SubmitQuiz submitQuiz;
	
	@ManyToOne
	@JoinColumn(name = "idquestion", nullable = false)
	private Question question;
	
	@ManyToOne
	@JoinColumn(name = "idanswer", nullable = true)
	private Answer answer;
	
	@Column(name = "open_answer", nullable = true)
	private String openAnswer;
	
	@Column(name = "points", nullable = false, columnDefinition = "TINYINT default 0")
	private Integer points;
	
	public SubmitAnswer() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public SubmitQuiz getSubmitQuiz() {
		return submitQuiz;
	}

	public void setSubmitQuiz(SubmitQuiz submitQuiz) {
		this.submitQuiz = submitQuiz;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public Answer getAnswer() {
		return answer;
	}

	public void setAnswer(Answer answer) {
		this.answer = answer;
	}

	public String getOpenAnswer() {
		return openAnswer;
	}

	public void setOpenAnswer(String openAnswer) {
		this.openAnswer = openAnswer;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}
	
}
