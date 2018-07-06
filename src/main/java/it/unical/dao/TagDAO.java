package it.unical.dao;

import java.util.List;

import it.unical.entities.Tag;

/**
 * @author Fabrizio
 */

public interface TagDAO {
	
	void create(Tag tag);

	void delete(Tag tag);

	void update(Tag tag);

	List<Tag> getAllTagsByProblem(Integer problem);
	
	List<String> getAllTagValuesByProblem(Integer problem);
	
	List<String> getAllTagValues();

}
