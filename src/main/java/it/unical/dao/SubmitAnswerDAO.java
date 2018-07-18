package it.unical.dao;

import it.unical.entities.SubmitAnswer;
import it.unical.entities.SubmitQuiz;

public interface SubmitAnswerDAO {
	
	void create(SubmitAnswer submitAnswer);

	void delete(SubmitAnswer submitAnswer);
	
	void update(SubmitAnswer submitAnswer);
	
	SubmitAnswer get(Integer id);
	
	SubmitAnswer getBySubmitQuiz(Integer idSubmitQuiz);
	
	SubmitAnswer getBySubmitQuiz(SubmitQuiz submitQuiz);
}
