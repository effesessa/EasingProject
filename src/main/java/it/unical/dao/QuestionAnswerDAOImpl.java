package it.unical.dao;

import it.unical.entities.QuestionAnswer;

public class QuestionAnswerDAOImpl implements QuestionAnswerDAO {
	
	private DatabaseHandler databaseHandler;
	
	public QuestionAnswerDAOImpl() {
		databaseHandler = null;
	}
	
	public void setDatabaseHandler(DatabaseHandler databaseHandler) {
		this.databaseHandler = databaseHandler;
	}
	
	public DatabaseHandler getDatabaseHandler() {
		return databaseHandler;
	}
	
	@Override
	public void create(QuestionAnswer questionAnswer) {
		databaseHandler.create(questionAnswer);
	}

	public void delete(QuestionAnswer questionAnswer) {
		databaseHandler.delete(questionAnswer);
	}
	
	public void update(QuestionAnswer questionAnswer) {
		databaseHandler.update(questionAnswer);
	}
	
}
