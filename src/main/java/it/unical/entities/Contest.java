package it.unical.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import it.unical.dao.DatabaseHandler;

@Entity
@Table(name = "contest")
public class Contest
{
	@Id
	@Column(name = "idcontest")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idcontest;

	@Column(name = "restriction")
	private Integer restriction;

	@Column(name = "name")
	private String name;

	@Column(name = "deadline")
	private String deadline;

	@Column(name = "url_image")
	private String url_image;

	@Column(name = "rankable")
	private String rankable;

	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "id_subject"), @JoinColumn(name = "year") })
	private Subject subject;

	@ManyToOne
	@JoinColumn(name = "id_jury")
	private Jury jury;

	public Contest()
	{
		this.idcontest = DatabaseHandler.NO_ID;
		this.name = null;
		this.deadline = null;
		this.url_image = null;
		this.rankable = null;
		this.subject = null;
		this.jury = null;
	}

	public Contest(String name, String deadline, String url_image, String rankable, Subject subject, Jury jury)
	{
		this.name = name;
		this.deadline = deadline;
		this.url_image = url_image;
		this.rankable = rankable;
		this.subject = subject;
		this.jury = jury;
	}

	public String getDeadline()
	{
		return deadline;
	}

	public Integer getIdcontest()
	{
		return idcontest;
	}

	public Jury getJury()
	{
		return jury;
	}

	public String getName()
	{
		return name;
	}

	public String getRankable()
	{
		return rankable;
	}

	public Integer getRestriction()
	{
		return restriction;
	}

	public Subject getSubject()
	{
		return subject;
	}

	public String getUrl_image()
	{
		return url_image;
	}

	public void setDeadline(String deadline)
	{
		this.deadline = deadline;
	}

	public void setIdcontest(Integer idcontest)
	{
		this.idcontest = idcontest;
	}

	public void setJury(Jury jury)
	{
		this.jury = jury;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setRankable(String rankable)
	{
		this.rankable = rankable;
	}

	public void setRestriction(Integer restriction)
	{
		this.restriction = restriction;
	}

	public void setSubject(Subject subject)
	{
		this.subject = subject;
	}

	public void setUrl_image(String url_image)
	{
		this.url_image = url_image;
	}

}