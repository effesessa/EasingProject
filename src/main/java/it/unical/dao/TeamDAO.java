package it.unical.dao;

import it.unical.entities.Team;

public interface TeamDAO
{

	public void create(Team team);

	public void delete(Team team);

	Team get(Integer id);

	Team getByName(String name);

	public void update(Team team);
}