package it.unical.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import it.unical.dao.DatabaseHandler;

@Embeddable
public class SubjectId implements Serializable
{
	@Column(name = "id_subject")
	private Integer id_subject;

	@Column(name = "year")
	private String year;

	public SubjectId()
	{
		this.id_subject = DatabaseHandler.NO_ID;
		this.year = null;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (!(o instanceof SubjectId))
			return false;
		final SubjectId that = (SubjectId) o;
		return Objects.equals(getId_subject(), that.getId_subject()) && Objects.equals(getYear(), that.getYear());
	}

	public Integer getId_subject()
	{
		return id_subject;
	}

	public String getYear()
	{
		return year;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(getId_subject(), getYear());
	}

	public void setId_subject(Integer id_subject)
	{
		this.id_subject = id_subject;
	}

	public void setYear(String year)
	{
		this.year = year;
	}
}