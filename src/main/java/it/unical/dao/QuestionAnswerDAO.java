package it.unical.dao;

import it.unical.entities.QuestionAnswer;

public interface QuestionAnswerDAO {
	
	void create(QuestionAnswer questionAnswer);

	void delete(QuestionAnswer questionAnswer);
	
	void update(QuestionAnswer questionAnswer);
}
