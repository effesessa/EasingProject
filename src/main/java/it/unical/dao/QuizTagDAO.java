package it.unical.dao;

import java.util.List;

import it.unical.entities.QuizTag;

public interface QuizTagDAO {
	
	void create(QuizTag quizTag);

	void delete(QuizTag quizTag);

	List<QuizTag> getAllTagsByQuiz(Integer problem);

	List<String> getAllTagValues();

	List<String> getAllTagValuesByQuiz(Integer problem);

	List<String> getMostPopularTags();

	void update(QuizTag quizTag);

	List<QuizTag> getByValue(String value);

	void deleteAllTagsByQuiz(Integer idQuiz);
}
