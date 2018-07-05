package it.unical.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import it.unical.dao.DatabaseHandler;

@Entity
@Table(name = "user")
public class User {

	@Id
	@Column(name = "id")
	private Integer id;

	@Column(name = "name")
	private String name;

	@Column(name = "surname")
	private String surname;

	@Column(name = "password")
	private String password;

	@Column(name = "email")
	private String email;

	@Column(name = "professor", columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean professor;

	public User()
	{
		this.id=DatabaseHandler.NO_ID;
		this.name="";
		this.surname="";
		this.password="";
		this.email = "";
		this.professor = false;
	}

	public User(Integer matricola, String name, String surname, String password, String email, boolean professor)
	{
		this.id = matricola;
		this.name = name;
		this.surname = surname;
		this.password = password;
		this.email = email;
		this.professor = professor;
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

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isProfessor() {
		return professor;
	}

	public void setProfessor(boolean professor) {
		this.professor = professor;
	}


}
