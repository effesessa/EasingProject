package it.unical.dao;

import java.util.List;

import it.unical.entities.Contest;

public interface ContestDAO
{

	void create(Contest contest);

	void delete(Contest contest);

	Contest get(Integer id);

	List<Contest> getAll();

	List<Contest> getContestByJury(Integer jury);

	Contest getContestByName(String name);

	List<Contest> getContestBySubject(Integer subject, Integer year);

	List<Contest> getContestsByProfessor(Integer id);

	List<String> getContestsNamesByProfessor(Integer id);

	Integer getIdByName(String name);

	void update(Contest contest);

	Contest getFetchJoinConstraints(Integer id);
}