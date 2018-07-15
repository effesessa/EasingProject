package it.unical.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "question")
public class Question implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public Question() {

	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
 	@Column(name = "id")
	private Integer id;
	
	@Column(name = "text", nullable = false)
	private String text;

	@Column(name = "points")
	private Integer points;
	
	@ManyToOne
	@JoinColumn(name = "correctAnswer", nullable = true)
	private Answer correctAnswer;
	
	@Column(name = "type", nullable = false)
	@Enumerated(EnumType.STRING)
	public Type type = Type.OPEN;

	public static enum Type {
		OPEN,
		MULTIPLE
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(final String text) {
		this.text = text;
	}

	public Answer getCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(final Answer correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

	public Type getType() {
		return type;
	}

	public void setType(final Type type) {
		this.type = type;
	}
	
	public Integer getPoints() {
		return points;
	}
	
	public void setPoints(final Integer points) {
		this.points = points;
	}

}
