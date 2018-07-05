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
@Table(name = "jury_member")
public class JuryMember
{
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "jury_idjury")
	private Jury jury;

	@ManyToOne
	@JoinColumn(name = "user_iduser")
	private User user;
	
	public JuryMember()
	{
		this.id = DatabaseHandler.NO_ID;
		this.jury = null;
		this.user = null;
	}

	public JuryMember(Integer id, Jury jury, User user) {
		this.id = id;
		this.jury = jury;
		this.user = user;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Jury getJury() {
		return jury;
	}

	public void setJury(Jury jury) {
		this.jury = jury;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}