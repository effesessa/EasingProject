package it.unical.dao;

import it.unical.entities.QuizQuestion;

public interface QuizQuestionDAO {
	
	void create(QuizQuestion quizQuestion);

	void delete(QuizQuestion quizQuestion);
	
	void update(QuizQuestion quizQuestion);
}
