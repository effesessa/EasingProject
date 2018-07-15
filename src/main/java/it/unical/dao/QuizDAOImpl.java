package it.unical.dao;

import org.hibernate.Query;
import org.hibernate.Session;

import it.unical.entities.Quiz;

public class QuizDAOImpl implements QuizDAO {

	private DatabaseHandler databaseHandler;
	
	public QuizDAOImpl() {
		databaseHandler = null;
	}
	
	public void setDatabaseHandler(final DatabaseHandler databaseHandler) {
		this.databaseHandler = databaseHandler;
	}
	
	public DatabaseHandler getDatabaseHandler() {
		return databaseHandler;
	}
	
	@Override
	public void create(final Quiz quiz) {
		databaseHandler.create(quiz);
	}

	@Override
	public void delete(final Quiz quiz) {
		databaseHandler.delete(quiz);
	}

	@Override
	public void update(final Quiz quiz) {
		databaseHandler.update(quiz);
	}

	@Override
	public Quiz get(final Integer id) {
		final Session session = databaseHandler.getSessionFactory().openSession();
		final Query query = session.createQuery("from Quiz where id = :id");
		query.setParameter("id",id);
		final Quiz quiz = (Quiz) query.uniqueResult();
		session.close();
		return quiz;
	}

	@Override
	public Quiz getByContest(final Integer contest) {
		final Session session = databaseHandler.getSessionFactory().openSession();
		final Query query = session.createQuery("from Quiz where idcontest = :contest");
		query.setParameter("contest", contest);
		final Quiz quiz = (Quiz) query.uniqueResult();
		session.close();
		return quiz;
	}

	@Override
	public Quiz getByName(final String name) {
		final Session session = databaseHandler.getSessionFactory().openSession();
		final Query query = session.createQuery("from Quiz where name = :name");
		query.setParameter("name", name);
		final Quiz quiz = (Quiz) query.uniqueResult();
		session.close();
		return quiz;
	}

}
