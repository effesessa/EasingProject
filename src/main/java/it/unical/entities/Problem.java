package it.unical.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;

import it.unical.dao.DatabaseHandler;

@Entity
@Table(name = "problem")
public class Problem
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_problem")
	private Integer id_problem;

	@Column(name = "name")
	private String name;

	@Column(name = "test", columnDefinition = "mediumblob")
	private byte[] test;

	@Column(name = "timelimit")
	private Float timelimit;

	@Column(name = "type")
	private String type;

	@Column(name = "sol", columnDefinition = "blob")
	private byte[] sol;

	@Column(name = "description")
	private String description;

	@Column(name = "download", columnDefinition = "mediumblob")
	private byte[] download;

	@Column(name = "show_testcase", columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean show_testcase;

	@ManyToOne
	@JoinColumn(name = "id_jury")
	private Jury jury;

	@ManyToOne
	@JoinColumn(name = "contest_idcontest")
	private Contest id_contest;

	@OneToMany(mappedBy = "problem", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Submit> submits;

	public Problem()
	{
		this.id_problem = DatabaseHandler.NO_ID;
		this.name = null;
		this.test = null;
		this.timelimit = null;
		this.type = null;
		this.sol = null;
		this.description = null;
		this.show_testcase = false;
	}

	public String getDescription()
	{
		return description;
	}

	public byte[] getDownload()
	{
		return download;
	}

	public Contest getId_contest()
	{
		return id_contest;
	}

	public Integer getId_problem()
	{
		return id_problem;
	}

	public Jury getJury()
	{
		return jury;
	}

	public String getName()
	{
		return name;
	}

	public byte[] getSol()
	{
		return sol;
	}

	public List<Submit> getSubmits()
	{
		return submits;
	}

	public byte[] getTest()
	{
		return test;
	}

	public Float getTimelimit()
	{
		return timelimit;
	}

	public String getType()
	{
		return type;
	}

	public boolean isShow_testcase()
	{
		return show_testcase;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public void setDownload(byte[] download)
	{
		this.download = download;
	}

	public void setId_contest(Contest id_contest)
	{
		this.id_contest = id_contest;
	}

	public void setId_problem(Integer id_problem)
	{
		this.id_problem = id_problem;
	}

	public void setJury(Jury jury)
	{
		this.jury = jury;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setShow_testcase(boolean show_testcase)
	{
		this.show_testcase = show_testcase;
	}

	public void setSol(byte[] sol)
	{
		this.sol = sol;
	}

	public void setSubmits(List<Submit> submits)
	{
		this.submits = submits;
	}

	public void setTest(byte[] test)
	{
		this.test = test;
	}

	public void setTimelimit(Float timelimit)
	{
		this.timelimit = timelimit;
	}

	public void setType(String type)
	{
		this.type = type;
	}

}