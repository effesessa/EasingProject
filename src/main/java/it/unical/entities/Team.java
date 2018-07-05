package it.unical.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import it.unical.dao.DatabaseHandler;

@Entity
@Table(name = "team")
public class Team
{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
 	@Column(name = "id_team")
	private Integer id;

	@Column(name = "name")
	private String name;
 	
 	public Team()
 	{
 		this.id = DatabaseHandler.NO_ID;
 		this.name = null;
 	//	this.submit = null;
 	}

	public Team(Integer id, String name, List<Submit> submit) {
		this.id= id;
		this.name = name;
	//	this.submit = submit;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
 	
}