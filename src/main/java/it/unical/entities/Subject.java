package it.unical.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "subject")
public class Subject
{
	@EmbeddedId
	private SubjectId subjectId;

	@Column(name = "name")
	private String name;

	@Column(name = "password")
	private String password;

	@Column(name = "url")
	private String url;

	@ManyToOne
	@JoinColumn(name = "user_professor")
	private User professor;

	@OneToMany(mappedBy = "subject")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private List<Contest> contest;

	public Subject()
	{
		this.subjectId = null;
		this.name = null;
		this.professor = null;
		this.url = null;

	}

	public List<Contest> getContest()
	{
		return contest;
	}

	public User getId_professor()
	{
		return professor;
	}

	public String getName()
	{
		return name;
	}

	public String getPassword()
	{
		return password;
	}

	public User getProfessor()
	{
		return professor;
	}

	public SubjectId getSubjectId()
	{
		return subjectId;
	}

	public String getUrl()
	{
		return url;
	}

	public void setContest(List<Contest> contest)
	{
		this.contest = contest;
	}

	public void setId_professor(User professor)
	{
		this.professor = professor;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public void setProfessor(User professor)
	{
		this.professor = professor;
	}

	public void setSubjectId(SubjectId subjectId)
	{
		this.subjectId = subjectId;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}
}