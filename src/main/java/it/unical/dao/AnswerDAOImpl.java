package it.unical.dao;

import org.hibernate.Query;
import org.hibernate.Session;

import it.unical.entities.Answer;

public class AnswerDAOImpl implements AnswerDAO
{

	private DatabaseHandler databaseHandler;

	public AnswerDAOImpl()
	{
		databaseHandler = null;
	}

	@Override
	public void create(final Answer answer)
	{
		databaseHandler.create(answer);
	}

	@Override
	public void delete(final Answer answer)
	{
		databaseHandler.delete(answer);
	}

	@Override
	public boolean exists(String textAnswer)
	{
		final Session session = databaseHandler.getSessionFactory().openSession();
		final Query query = session.createQuery("from Answer where text = :text");
		query.setParameter("text", textAnswer);
		Answer existingAnswer = (Answer) query.uniqueResult();
		session.close();
		if (existingAnswer == null)
			return false;
		return true;
	}
	
	@Override
	public Answer getByText(String text) {
		final Session session = databaseHandler.getSessionFactory().openSession();
		final Query query = session.createQuery("from Answer where text = :text");
		query.setParameter("text", text);
		final Answer answer = (Answer) query.uniqueResult();
		session.close();
		return answer;
	}

	@Override
	public Answer get(final Integer id)
	{
		final Session session = databaseHandler.getSessionFactory().openSession();
		final Query query = session.createQuery("from Answer where id = :id");
		query.setParameter("id", id);
		final Answer answer = (Answer) query.uniqueResult();
		session.close();
		return answer;
	}

	public DatabaseHandler getDatabaseHandler()
	{
		return databaseHandler;
	}

	public void setDatabaseHandler(final DatabaseHandler databaseHandler)
	{
		this.databaseHandler = databaseHandler;
	}

	@Override
	public void update(final Answer answer)
	{
		databaseHandler.update(answer);
	}

}
