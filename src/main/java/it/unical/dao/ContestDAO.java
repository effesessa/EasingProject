package it.unical.dao;

import java.util.List;

import it.unical.entities.Contest;

public interface ContestDAO {

	public void create(Contest contest);

	public void delete(Contest contest);

	public void update(Contest contest);

	public Contest get(Integer id);

	public List<Contest> getAll();

	public Contest getContestByName(String name);

	List<Contest> getContestBySubject(Integer subject, Integer year);

	List<Contest> getContestByJury(Integer jury);

	List<String> getContestsNamesByProfessor(Integer id);

	Integer getIdByName(String name);
}