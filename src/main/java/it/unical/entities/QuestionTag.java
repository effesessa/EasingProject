package it.unical.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity 
@Table(name = "question_tag")
public class QuestionTag {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name = "id")
	private Integer id;
	
	@ManyToOne 
	@JoinColumn(name = "question")
	private Question question;
	
	@Column(name = "value")
	private String value;

	public QuestionTag() {
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}
}
