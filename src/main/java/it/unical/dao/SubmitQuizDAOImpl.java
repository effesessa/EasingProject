package it.unical.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import it.unical.entities.Quiz;
import it.unical.entities.SubmitQuiz;
import it.unical.entities.Team;

public class SubmitQuizDAOImpl implements SubmitQuizDAO {

	private DatabaseHandler databaseHandler;

	public SubmitQuizDAOImpl() {
		databaseHandler = null;
	}

	public void setDatabaseHandler(DatabaseHandler databaseHandler) {
		this.databaseHandler = databaseHandler;
	}
	
	public DatabaseHandler getDatabaseHandler() {
		return databaseHandler;
	}
	
	@Override
	public void create(SubmitQuiz submitQuiz) {
		databaseHandler.create(submitQuiz);
	}

	@Override
	public void delete(SubmitQuiz submitQuiz) {
		databaseHandler.delete(submitQuiz);
	}
	
	@Override
	public void update(SubmitQuiz submitQuiz) {
		databaseHandler.update(submitQuiz);
	}

	@Override
	public SubmitQuiz get(final Integer id) {
		final Session session = databaseHandler.getSessionFactory().openSession();
		final Query query = session.createQuery("from SubmitQuiz where id = :id");
		query.setParameter("id", id);
		final SubmitQuiz submitQuiz = (SubmitQuiz) query.uniqueResult();
		session.close();
		return submitQuiz;
	}

	@Override
	public SubmitQuiz getByTeamAndQuiz(final Integer idTeam, final Integer idQuiz) {
		final Session session = databaseHandler.getSessionFactory().openSession();
		final Query query = session.createQuery("from SubmitQuiz where idteam = :idTeam and idquiz = :idQuiz");
		query.setParameter("idTeam", idTeam);
		query.setParameter("idQuiz", idQuiz);
		final SubmitQuiz submitQuiz = (SubmitQuiz) query.uniqueResult();
		session.close();
		return submitQuiz;
	}

	@Override
	public SubmitQuiz getByTeamAndQuiz(final Team team, final Quiz quiz) {
		final Session session = databaseHandler.getSessionFactory().openSession();
		final Query query = session.createQuery("from SubmitQuiz where idteam = :idTeam and idquiz = :idQuiz");
		query.setParameter("idTeam", team.getId());
		query.setParameter("idQuiz", quiz.getId());
		final SubmitQuiz submitQuiz = (SubmitQuiz) query.uniqueResult();
		session.close();
		return submitQuiz;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SubmitQuiz> getAllByQuiz(Integer idQuiz) {
		final Session session = databaseHandler.getSessionFactory().openSession();
		final Query query = session.createQuery("from SubmitQuiz where idquiz = :idQuiz");
		query.setParameter("idQuiz", idQuiz);
		final List<SubmitQuiz> submitQuizs =  query.list();
		session.close();
		return submitQuizs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SubmitQuiz> getAllByQuiz(Quiz quiz) {
		final Session session = databaseHandler.getSessionFactory().openSession();
		final Query query = session.createQuery("from SubmitQuiz where idquiz = :idQuiz");
		query.setParameter("idQuiz", quiz.getId());
		final List<SubmitQuiz> submitQuizs =  query.list();
		session.close();
		return submitQuizs;
	}
}
