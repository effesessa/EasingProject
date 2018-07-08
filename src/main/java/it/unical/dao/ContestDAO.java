package it.unical.dao;

import java.util.List;

import it.unical.entities.Contest;

public interface ContestDAO
{

	public void create(Contest contest);

	public void delete(Contest contest);

	public Contest get(Integer id);

	public List<Contest> getAll();

	public List<Contest> getContestByJury(Integer jury);

	public Contest getContestByName(String name);

	public List<Contest> getContestBySubject(Integer subject, Integer year);

	public List<Contest> getContestsByProfessor(Integer id);

	public List<String> getContestsNamesByProfessor(Integer id);

	public Integer getIdByName(String name);

	public void update(Contest contest);
}