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
@Table(name = "question_answer")
public class QuestionAnswer implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public QuestionAnswer() {
		
	}
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
 	@Column(name = "id")
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "idquestion")
	private Question question;
	
	@ManyToOne
	@JoinColumn(name = "idanswer")
	private Answer answer;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
	
}
