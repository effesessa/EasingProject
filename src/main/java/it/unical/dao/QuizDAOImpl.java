package it.unical.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import it.unical.entities.Quiz;

public class QuizDAOImpl implements QuizDAO {

	private DatabaseHandler databaseHandler;
	
	public QuizDAOImpl() {
		databaseHandler = null;
	}
	
	public void setDatabaseHandler(DatabaseHandler databaseHandler) {
		this.databaseHandler = databaseHandler;
	}
	
	public DatabaseHandler getDatabaseHandler() {
		return databaseHandler;
	}
	
	@Override
	public void create(Quiz quiz) {
		databaseHandler.create(quiz);
	}

	@Override
	public void delete(Quiz quiz) {
		databaseHandler.delete(quiz);
	}

	@Override
	public void update(Quiz quiz) {
		databaseHandler.update(quiz);
	}

	@Override
	public Quiz get(Integer id) {
		final Session session = databaseHandler.getSessionFactory().openSession();
		final Query query = session.createQuery("from Quiz where id = :id");
		query.setParameter("id",id);
		final Quiz quiz = (Quiz) query.uniqueResult();
		session.close();
		return quiz;
	}

	@Override
	public Quiz getByContest(Integer contest) {
		final Session session = databaseHandler.getSessionFactory().openSession();
		final Query query = session.createQuery("from Quiz where idcontest = :contest");
		query.setParameter("contest", contest);
		final Quiz quiz = (Quiz) query.uniqueResult();
		session.close();
		return quiz;
	}

}
