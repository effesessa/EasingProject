package it.unical.dao;

import java.util.List;

import it.unical.entities.Question;

public interface QuestionDAO {
	
	void create(Question question);

	void delete(Question question);
	
	void update(Question question);

	Question get(Integer id);
	
	List<Question> getQuestionsByQuiz(Integer idQuiz);

	List<Question> getRandomQuestions(String tagValue, Integer limit);
}
