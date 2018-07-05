package it.unical.entities;

import java.util.List;

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

import it.unical.dao.DatabaseHandler;

@Entity
@Table(name = "jury")
public class Jury
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_jury")
	private Integer id_jury;

	@ManyToOne
	@JoinColumn(name = "jury_leader")
	private User professor;

	public Jury()
	{
		this.id_jury = DatabaseHandler.NO_ID;
		this.professor = null;
	}

	public Jury(Integer id_jury, User professor, List<Contest> contest)
	{
		this.id_jury = id_jury;
		this.professor = professor;
	}

	public Integer getId_jury()
	{
		return id_jury;
	}

	public User getProfessor()
	{
		return professor;
	}

	public void setId_jury(Integer id_jury)
	{
		this.id_jury = id_jury;
	}

	public void setProfessor(User professor)
	{
		this.professor = professor;
	}

}