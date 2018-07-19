package it.unical.dao;

import java.util.List;

import it.unical.entities.Quiz;
import it.unical.entities.SubmitQuiz;
import it.unical.entities.Team;

public interface SubmitQuizDAO {
	
	void create(SubmitQuiz submitQuiz);

	void delete(SubmitQuiz submitQuiz);
	
	void update(SubmitQuiz submitQuiz);
	
	SubmitQuiz get(Integer id);
	
	SubmitQuiz getByTeamAndQuiz(Integer idTeam, Integer idQuiz);
	
	SubmitQuiz getByTeamAndQuiz(Team team, Quiz quiz);
	
	List<SubmitQuiz> getAllByQuiz(Integer idQuiz);
	
	List<SubmitQuiz> getAllByQuiz(Quiz quiz);

	List<SubmitQuiz> getAllToBeCorrectionByQuiz(Quiz quiz);
}
