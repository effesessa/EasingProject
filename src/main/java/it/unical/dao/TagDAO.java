package it.unical.dao;

import java.util.List;

import it.unical.entities.Tag;

/**
 * @author Fabrizio
 */

public interface TagDAO
{

	void create(Tag tag);

	void delete(Tag tag);

	void deleteAllTagsByProblem(Integer id_problem);

	List<Tag> getAllTagsByProblem(Integer problem);

	List<String> getAllTagValues();

	List<String> getAllTagValuesByProblem(Integer problem);

	List<String> getMostPopularTags();

	void update(Tag tag);
}
