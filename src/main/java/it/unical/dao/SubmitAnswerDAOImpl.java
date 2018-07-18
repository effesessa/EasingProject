package it.unical.dao;

import org.hibernate.Query;
import org.hibernate.Session;

import it.unical.entities.SubmitAnswer;
import it.unical.entities.SubmitQuiz;

public class SubmitAnswerDAOImpl implements SubmitAnswerDAO {

	private DatabaseHandler databaseHandler;

	public SubmitAnswerDAOImpl() {
		databaseHandler = null;
	}

	public void setDatabaseHandler(DatabaseHandler databaseHandler) {
		this.databaseHandler = databaseHandler;
	}
	
	public DatabaseHandler getDatabaseHandler() {
		return databaseHandler;
	}
	
	@Override
	public void create(SubmitAnswer submitAnswer) {
		databaseHandler.create(submitAnswer);
	}

	@Override
	public void delete(SubmitAnswer submitAnswer) {
		databaseHandler.delete(submitAnswer);
	}

	@Override
	public void update(SubmitAnswer submitAnswer) {
		databaseHandler.update(submitAnswer);
	}

	@Override
	public SubmitAnswer get(Integer id) {
		final Session session = databaseHandler.getSessionFactory().openSession();
		final Query query = session.createQuery("from SubmitAnswer where id = :id");
		query.setParameter("id", id);
		final SubmitAnswer submitAnswer = (SubmitAnswer) query.uniqueResult();
		session.close();
		return submitAnswer;
	}

	@Override
	public SubmitAnswer getBySubmitQuiz(Integer idSubmitQuiz) {
		final Session session = databaseHandler.getSessionFactory().openSession();
		final Query query = session.createQuery("from SubmitAnswer where idsubmitquiz = :idSubmitQuiz");
		query.setParameter("idSubmitQuiz", idSubmitQuiz);
		final SubmitAnswer submitAnswer = (SubmitAnswer) query.uniqueResult();
		session.close();
		return submitAnswer;
	}

	@Override
	public SubmitAnswer getBySubmitQuiz(SubmitQuiz submitQuiz) {
		final Session session = databaseHandler.getSessionFactory().openSession();
		final Query query = session.createQuery("from SubmitAnswer where idsubmitquiz = :idSubmitQuiz");
		query.setParameter("idSubmitQuiz", submitQuiz.getId());
		final SubmitAnswer submitAnswer = (SubmitAnswer) query.uniqueResult();
		session.close();
		return submitAnswer;
	}

}
