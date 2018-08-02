package it.unical.dao;

import it.unical.entities.ProblemConstraint;

public class ProblemConstraintDAOImpl implements ProblemConstraintDAO {
	
	private DatabaseHandler databaseHandler;
	
	public void setDatabaseHandler(DatabaseHandler databaseHandler) {
		this.databaseHandler = databaseHandler;
	}
	
	public DatabaseHandler getDatabaseHandler() {
		return databaseHandler;
	}
	
	@Override
	public void create(ProblemConstraint problemConstraint) {
		databaseHandler.create(problemConstraint);
	}

	@Override
	public void delete(ProblemConstraint problemConstraint) {
		databaseHandler.delete(problemConstraint);
	}

	@Override
	public void update(ProblemConstraint problemConstraint) {
		databaseHandler.update(problemConstraint);
	}

}
