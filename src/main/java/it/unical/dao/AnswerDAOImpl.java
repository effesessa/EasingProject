package it.unical.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import it.unical.entities.Answer;

public class AnswerDAOImpl implements AnswerDAO {
	
	private DatabaseHandler databaseHandler;
	
	public AnswerDAOImpl() {
		databaseHandler = null;
	}
	
	public void setDatabaseHandler(DatabaseHandler databaseHandler) {
		this.databaseHandler = databaseHandler;
	}
	
	public DatabaseHandler getDatabaseHandler() {
		return databaseHandler;
	}
	
	@Override
	public void create(Answer answer) {
		databaseHandler.create(answer);
	}

	@Override
	public void delete(Answer answer) {
		databaseHandler.delete(answer);
	}

	@Override
	public void update(Answer answer) {
		databaseHandler.update(answer);
	}

	@Override
	public Answer get(Integer id) {
		final Session session = databaseHandler.getSessionFactory().openSession();
		final Query query = session.createQuery("from Answer where id =: id");
		query.setParameter("id", id);
		final Answer answer = (Answer) query.uniqueResult();
		session.close();
		return answer;
	}

}
