package it.unical.dao;

import it.unical.entities.QuizConstraint;

public interface QuizConstraintDAO {
	
	void create(QuizConstraint quizConstraint);
	
	void delete(QuizConstraint quizConstraint);
	
	void update(QuizConstraint quizConstraint);
}
