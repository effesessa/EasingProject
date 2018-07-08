package it.unical.dao;

import java.io.FileNotFoundException;
import java.util.List;

import it.unical.entities.Problem;

public interface ProblemDAO
{

	public void create(Problem problem);

	public void delete(Problem problem);

	Problem get(Integer id);

	public List<Problem> getByName(String word);

	List<Problem> getProblemOfAContest(Integer contest);

	List<Problem> getProblemsByProfessor(Integer id);

	public void update(Problem problem);

}