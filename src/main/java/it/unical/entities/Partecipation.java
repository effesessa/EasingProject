package it.unical.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import it.unical.dao.DatabaseHandler;

@Entity
@Table(name = "partecipation")
public class Partecipation
{
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "team_idteam")
	private Team team;

	@ManyToOne
	@JoinColumn(name = "contest_idcontest")
	private Contest contest;
	
	public Partecipation()
	{
		this.id = DatabaseHandler.NO_ID;
		this.team = null;
		this.contest = null;
	}

	public Partecipation(Integer id, Team team, Contest contest) {
		this.id = id;
		this.team = team;
		this.contest = contest;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public Contest getContest() {
		return contest;
	}

	public void setContest(Contest contest) {
		this.contest = contest;
	}
	
	
}