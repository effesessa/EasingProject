package it.unical.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import it.unical.dao.DatabaseHandler;

@Entity
@Table(name = "language")
public class Language
{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
 	@Column(name = "id_language")
	private Integer id_language;
	
	@Column(name = "name")
	private String name;
	
	public Language()
	{
		this.id_language = DatabaseHandler.NO_ID;
		this.name = null;
	}
	
	public Language (Integer id_language, String name)
	{
		this.id_language = id_language;
		this.name = name;
	}

	public Integer getId_language() {
		return id_language;
	}

	public void setId_language(Integer id_language) {
		this.id_language = id_language;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}