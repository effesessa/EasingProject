package it.unical.dao;

import java.util.List;

import it.unical.entities.Answer;

public interface AnswerDAO
{

	void create(Answer answer);

	void delete(Answer answer);

	boolean exists(String textAnswer);

	Answer get(Integer id);

	List<Answer> getAnswersByQuestion(Integer id);

	Answer getByText(String text);

	void update(Answer answer);
}
