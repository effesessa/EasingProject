package it.unical.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;

import it.unical.entities.SubmitAnswer;
import it.unical.entities.SubmitQuiz;

public class SubmitAnswerDAOImpl implements SubmitAnswerDAO
{

	private DatabaseHandler databaseHandler;

	public SubmitAnswerDAOImpl()
	{
		databaseHandler = null;
	}

	@Override
	public void create(SubmitAnswer submitAnswer)
	{
		databaseHandler.create(submitAnswer);
	}

	@Override
	public void delete(SubmitAnswer submitAnswer)
	{
		databaseHandler.delete(submitAnswer);
	}

	@Override
	public SubmitAnswer get(Integer id)
	{
		final Session session = databaseHandler.getSessionFactory().openSession();
		final Query query = session.createQuery("from SubmitAnswer where id = :id");
		query.setParameter("id", id);
		final SubmitAnswer submitAnswer = (SubmitAnswer) query.uniqueResult();
		session.close();
		return submitAnswer;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SubmitAnswer> getBySubmitQuiz(Integer idSubmitQuiz)
	{
		final Session session = databaseHandler.getSessionFactory().openSession();
		final Query query = session.createQuery("from SubmitAnswer where idsubmitquiz = :idSubmitQuiz");
		query.setParameter("idSubmitQuiz", idSubmitQuiz);
		final List<SubmitAnswer> submitAnswers = query.list();
		session.close();
		return submitAnswers;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SubmitAnswer> getBySubmitQuiz(SubmitQuiz submitQuiz)
	{
		final Session session = databaseHandler.getSessionFactory().openSession();
		final Query query = session.createQuery(
				"select SA from SubmitAnswer SA join fetch SA.question SAQ left join fetch SAQ.answers where SA.submitQuiz.id = :idSubmitQuiz");
		query.setParameter("idSubmitQuiz", submitQuiz.getId());
		final List<SubmitAnswer> submitAnswers = query.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		session.close();
		return submitAnswers;
	}

	public DatabaseHandler getDatabaseHandler()
	{
		return databaseHandler;
	}

	public void setDatabaseHandler(DatabaseHandler databaseHandler)
	{
		this.databaseHandler = databaseHandler;
	}

	@Override
	public void update(SubmitAnswer submitAnswer)
	{
		databaseHandler.update(submitAnswer);
	}

}
