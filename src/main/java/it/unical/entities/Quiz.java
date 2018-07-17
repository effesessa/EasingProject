package it.unical.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "quiz")
public class Quiz implements Serializable
{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "points", nullable = true)
	private Integer points;

	@ManyToOne
	@JoinColumn(name = "idcontest", nullable = false)
	private Contest contest;

	@ManyToMany
	@JoinTable(name = "quiz_question", joinColumns = { @JoinColumn(name = "idquiz") }, inverseJoinColumns = {
			@JoinColumn(name = "idquestion") })
	private Set<Question> questions = new HashSet<>();

	public Quiz()
	{

	}

	public Contest getContest()
	{
		return contest;
	}

	public Integer getId()
	{
		return id;
	}

	public String getName()
	{
		return name;
	}

	public Integer getPoints()
	{
		return points;
	}

	public Set<Question> getQuestions()
	{
		return questions;
	}

	public void setContest(final Contest contest)
	{
		this.contest = contest;
	}

	public void setId(final Integer id)
	{
		this.id = id;
	}

	public void setName(final String name)
	{
		this.name = name;
	}

	public void setPoints(final Integer points)
	{
		this.points = points;
	}

	public void setQuestions(Set<Question> questions)
	{
		this.questions = questions;
	}

}
