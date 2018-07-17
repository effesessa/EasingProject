package it.unical.dao;

import it.unical.entities.Answer;

public interface AnswerDAO {
	
	void create(Answer answer);

	void delete(Answer answer);
	
	void update(Answer answer);

	Answer get(Integer id);
	
	boolean exists(String textAnswer);
	
	Answer getByText(String text);
}
