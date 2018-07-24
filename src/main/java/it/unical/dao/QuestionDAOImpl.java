package it.unical.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import it.unical.entities.Question;

public class QuestionDAOImpl implements QuestionDAO
{

	private DatabaseHandler databaseHandler;

	public QuestionDAOImpl()
	{
		databaseHandler = null;
	}

	@Override
	public void create(Question question)
	{
		databaseHandler.create(question);
	}

	@Override
	public void delete(Question question)
	{
		databaseHandler.delete(question);
	}

	@Override
	public Question get(Integer id)
	{
		final Session session = databaseHandler.getSessionFactory().openSession();
		final Query query = session.createQuery("from Question where id =: id");
		query.setParameter("id", id);
		final Question question = (Question) query.uniqueResult();
		session.close();
		return question;
	}

	public DatabaseHandler getDatabaseHandler()
	{
		return databaseHandler;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Question> getQuestionsByQuiz(Integer idQuiz)
	{
		final Session session = databaseHandler.getSessionFactory().openSession();
		final Query query = session.createQuery("select que from Question as que, QuizQuestion as qq, Quiz as qui "
				+ "where qui.id = :idQuiz and que.id = qq.question and que.id = qq.question");
		query.setParameter("idQuiz", idQuiz);
		final List<Question> questions = query.list();
		session.close();
		return questions;
	}

	/*
	 * funzionante su mysql select q.* from question q, question_tag qt where
	 * q.id = qt.question and qt.value = 'java' group by q.text order by rand()
	 * limit 4;
	 *
	 * da provare con hibernate hql
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Question> getRandomQuestions(String tagValue, Integer limit)
	{
		final Session session = databaseHandler.getSessionFactory().openSession();
		System.out.println(tagValue);
		System.out.println(limit);
		final String hql = "select Q from Question Q left join fetch Q.tags T left join fetch Q.answers where T.value = :tagValue group by Q.text order by rand()";
		final Query query = session.createQuery(hql);
		query.setParameter("tagValue", tagValue);
		query.setMaxResults(limit);
		final List<Question> questions = query.list();
		session.close();
		return questions;
	}

	public void setDatabaseHandler(DatabaseHandler databaseHandler)
	{
		this.databaseHandler = databaseHandler;
	}

	@Override
	public void update(Question question)
	{
		databaseHandler.update(question);
	}

}