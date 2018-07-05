package it.unical.dao;

import java.util.List;

import it.unical.entities.Team;

public interface TeamDAO {

	public void create(Team team);

	public void delete(Team team);

	public void update(Team team);

	Team get(Integer id);
	
	Team getByName(String name);
}