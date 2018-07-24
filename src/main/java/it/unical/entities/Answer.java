package it.unical.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "answer")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Answer implements Serializable
{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "type", nullable = true)
	private String type;

	@Column(name = "text")
	private String text;

	@ManyToMany(mappedBy = "answers")
	private List<Question> questions = new ArrayList<>();

	public Answer()
	{

	}

	public Integer getId()
	{
		return id;
	}

	public List<Question> getQuestions()
	{
		return questions;
	}

	public String getText()
	{
		return text;
	}

	public String getType()
	{
		return type;
	}

	public void setId(final Integer id)
	{
		this.id = id;
	}

	public void setQuestions(List<Question> questions)
	{
		this.questions = questions;
	}

	public void setText(final String text)
	{
		this.text = text;
	}

	public void setType(final String type)
	{
		this.type = type;
	}

}
