package it.unical.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name = "question")
public class Question implements Serializable
{

	public static enum Type
	{
		OPEN, MULTIPLE
	}

	private static final long serialVersionUID = 1L;

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

	@ManyToMany(mappedBy = "questions")
	private List<Quiz> quizs = new ArrayList<>();

	@ManyToMany
	@JoinTable(name = "question_answer", joinColumns = { @JoinColumn(name = "idquestion") }, inverseJoinColumns = {
			@JoinColumn(name = "idanswer") })
	@OrderBy("id DESC")
	private Set<Answer> answers = new LinkedHashSet<>();

	public Question()
	{

	}

	public Set<Answer> getAnswers()
	{
		return answers;
	}

	public Answer getCorrectAnswer()
	{
		return correctAnswer;
	}

	public Integer getId()
	{
		return id;
	}

	public Integer getPoints()
	{
		return points;
	}

	public List<Quiz> getQuizs()
	{
		return quizs;
	}

	public String getText()
	{
		return text;
	}

	public Type getType()
	{
		return type;
	}

	public void setAnswers(Set<Answer> answers)
	{
		this.answers = answers;
	}

	public void setCorrectAnswer(final Answer correctAnswer)
	{
		this.correctAnswer = correctAnswer;
	}

	public void setId(final Integer id)
	{
		this.id = id;
	}

	public void setPoints(final Integer points)
	{
		this.points = points;
	}

	public void setQuizs(List<Quiz> quizs)
	{
		this.quizs = quizs;
	}

	public void setText(final String text)
	{
		this.text = text;
	}

	public void setType(final Type type)
	{
		this.type = type;
	}

}
