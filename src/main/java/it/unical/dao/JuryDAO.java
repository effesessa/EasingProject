package it.unical.dao;

import it.unical.entities.Jury;

public interface JuryDAO {

	public void create(Jury jury);

	public void delete(Jury jury);

	public void update(Jury jury);

	Jury get(Integer id);
}