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
@Table(name = "registration")
public class Registration
{
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "user_matricola")
	private User user;

	@ManyToOne
	@JoinColumns({
	@JoinColumn(name = "subject_idsubject"),
	@JoinColumn(name = "year")})
	private Subject subject;
	
	public Registration()
	{
		this.id = DatabaseHandler.NO_ID;
		this.user = null;
		this.subject = null;
	}

	public Registration(Integer id, User user, Subject subject) {
		this.id = id;
		this.user = user;
		this.subject = subject;
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

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	
	
}