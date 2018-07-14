package it.unical.dao;

import it.unical.entities.QuizQuestion;

public class QuizQuestionDAOImpl implements QuizQuestionDAO {
	
	private DatabaseHandler databaseHandler;
	
	public QuizQuestionDAOImpl() {
		databaseHandler = null;
	}
	
	public void setDatabaseHandler(DatabaseHandler databaseHandler) {
		this.databaseHandler = databaseHandler;
	}
	
	public DatabaseHandler getDatabaseHandler() {
		return databaseHandler;
	}
	
	@Override
	public void create(QuizQuestion quizQuestion) {
		databaseHandler.create(quizQuestion);
	}

	@Override
	public void delete(QuizQuestion quizQuestion) {
		databaseHandler.delete(quizQuestion);
	}

	@Override
	public void update(QuizQuestion quizQuestion) {
		databaseHandler.update(quizQuestion);
	}

}
