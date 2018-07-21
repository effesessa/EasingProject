package it.unical.dao;

import java.util.List;

import it.unical.entities.ProblemTag;

/**
 * @author Fabrizio
 */

public interface ProblemTagDAO
{

	void create(ProblemTag tag);

	void delete(ProblemTag tag);

	void deleteAllTagsByProblem(Integer id_problem);

	List<ProblemTag> getAllTagsByProblem(Integer problem);

	List<String> getAllTagValues();

	List<String> getAllTagValuesByProblem(Integer problem);

	List<String> getMostPopularTags();

	void update(ProblemTag tag);

	List<ProblemTag> getByValue(String value);
}
