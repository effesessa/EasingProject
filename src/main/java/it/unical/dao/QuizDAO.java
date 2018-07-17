package it.unical.dao;

import java.util.List;
import java.util.Set;

import it.unical.entities.Quiz;

public interface QuizDAO
{

	void create(Quiz quiz);

	void delete(Quiz quiz);

	Quiz get(Integer id);

	List<Quiz> getAllQuizByContest(Integer id);

	Quiz getByName(String name);

	void update(Quiz quiz);
}
