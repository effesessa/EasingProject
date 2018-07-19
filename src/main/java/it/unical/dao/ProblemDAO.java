package it.unical.dao;

import java.util.List;

import it.unical.entities.Problem;

public interface ProblemDAO {

	void create(Problem problem);

	void delete(Problem problem);

	Problem get(Integer id);

	Problem get_JoinFetch(Integer id);

	List<Problem> getByName(String word);

	List<Problem> getProblemOfAContest(Integer contest);

	List<Problem> getProblemsByProfessor(Integer id);

	void update(Problem problem);
	
	List<Problem> getAllProblemsByTagOrLikeName(String word);
}