package it.unical.dao;

import java.util.List;

import it.unical.entities.QuestionTag;

public interface QuestionTagDAO {
	
	void create(QuestionTag questionTag);

	void delete(QuestionTag questionTag);

	List<QuestionTag> getAllTagsByQuestion(Integer idQuestion);

	List<String> getAllTagValues();

	List<String> getAllTagValuesByQuestion(Integer idQuestion);

	List<String> getMostPopularTags();

	void update(QuestionTag questionTag);

	List<QuestionTag> getByValue(String value);

	void deleteAllTagsByQuestion(Integer idQuestion);
}