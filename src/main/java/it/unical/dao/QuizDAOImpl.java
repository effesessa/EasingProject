package it.unical.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;

import it.unical.entities.Question;
import it.unical.entities.Quiz;

public class QuizDAOImpl implements QuizDAO
{

	private DatabaseHandler databaseHandler;

	public QuizDAOImpl()
	{
		databaseHandler = null;
	}

	@Override
	public void create(final Quiz quiz)
	{
		databaseHandler.create(quiz);
	}

	@Override
	public void delete(final Quiz quiz)
	{
		databaseHandler.delete(quiz);
	}

	@Override
	public Quiz get(final Integer id)
	{
		final Session session = databaseHandler.getSessionFactory().openSession();
		final Query query = session.createQuery("from Quiz where id = :id");
		query.setParameter("id", id);
		final Quiz quiz = (Quiz) query.uniqueResult();
		session.close();
		return quiz;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Quiz> getAllQuizByContest(final Integer contest)
	{
		final Session session = databaseHandler.getSessionFactory().openSession();
		final Query query = session.createQuery(
				"select Q from Quiz Q join fetch Q.questions QE left join fetch QE.answers where Q.contest.idcontest = :contest");
		query.setParameter("contest", contest);
		final List<Quiz> quizs = query.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		for (final Question quest : quizs.get(0).getQuestions())
			System.out.println(quest.getText());
		session.close();
		return quizs;
		// return new HashSet<Quiz>(quizs);
	}

	@Override
	public Quiz getByName(final String name)
	{
		final Session session = databaseHandler.getSessionFactory().openSession();
		final Query query = session.createQuery("from Quiz where name = :name");
		query.setParameter("name", name);
		final Quiz quiz = (Quiz) query.uniqueResult();
		session.close();
		return quiz;
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
	public void update(final Quiz quiz)
	{
		databaseHandler.update(quiz);
	}

}
