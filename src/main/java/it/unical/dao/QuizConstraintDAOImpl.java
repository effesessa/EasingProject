package it.unical.dao;

import it.unical.entities.QuizConstraint;

public class QuizConstraintDAOImpl implements QuizConstraintDAO {

	private DatabaseHandler databaseHandler;
	
	public void setDatabaseHandler(DatabaseHandler databaseHandler) {
		this.databaseHandler = databaseHandler;
	}
	
	public DatabaseHandler getDatabaseHandler() {
		return databaseHandler;
	}
	
	@Override
	public void create(QuizConstraint quizConstraint) {
		databaseHandler.create(quizConstraint);
	}

	@Override
	public void delete(QuizConstraint quizConstraint) {
		databaseHandler.delete(quizConstraint);
	}

	@Override
	public void update(QuizConstraint quizConstraint) {
		databaseHandler.update(quizConstraint);
	}

}
