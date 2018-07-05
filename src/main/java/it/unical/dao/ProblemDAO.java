package it.unical.dao;

import java.io.FileNotFoundException;
import java.util.List;

import it.unical.entities.Problem;

public interface ProblemDAO {

	public void create(Problem problem);

	public void delete(Problem problem);

	public void update(Problem problem);

	Problem get(Integer id);

	List<Problem> getProblemOfAContest(Integer contest);

	public List<Problem> getByName(String word);

	}