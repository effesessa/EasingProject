package it.unical.dao;

import it.unical.entities.Quiz;

public interface QuizDAO {
	
	void create(Quiz quiz);

	void delete(Quiz quiz);
	
	void update(Quiz quiz);

	Quiz get(Integer id);
	
	Quiz getByContest(Integer id);
}
