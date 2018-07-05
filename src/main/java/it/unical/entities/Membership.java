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
@Table(name = "membership")
public class Membership
{
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "user_matricola")
	private User user;

	@ManyToOne
	@JoinColumn(name = "team_idteam")
	private Team team;
	
	public Membership()
	{
		this.id = DatabaseHandler.NO_ID;
		this.user = null;
		this.team = null;
	}

	public Membership(Integer id, User user, Team team) {
		this.id = id;
		this.user = user;
		this.team = team;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}
	
}