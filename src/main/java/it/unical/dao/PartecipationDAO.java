package it.unical.dao;

import java.util.List;

import it.unical.entities.Partecipation;

public interface PartecipationDAO {

	public void create(Partecipation partecipation);

	public void delete(Partecipation partecipation);

	public void update(Partecipation partecipation);

	Partecipation get(Integer id);

	public List<Partecipation> getContestByTeam(Integer team);

	Partecipation getTeamPartecipation(Integer team, Integer contest);
}