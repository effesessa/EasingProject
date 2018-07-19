package it.unical.dao;

import java.util.List;

import it.unical.entities.SubmitAnswer;
import it.unical.entities.SubmitQuiz;

public interface SubmitAnswerDAO {
	
	void create(SubmitAnswer submitAnswer);

	void delete(SubmitAnswer submitAnswer);
	
	void update(SubmitAnswer submitAnswer);
	
	SubmitAnswer get(Integer id);
	
	List<SubmitAnswer> getBySubmitQuiz(Integer idSubmitQuiz);
	
	List<SubmitAnswer> getBySubmitQuiz(SubmitQuiz submitQuiz);
}
