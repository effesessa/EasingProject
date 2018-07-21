package it.unical.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import it.unical.entities.Question;

public class QuestionDAOImpl implements QuestionDAO {

	private DatabaseHandler databaseHandler;
	
	public QuestionDAOImpl() {
		databaseHandler = null;
	}
	
	public void setDatabaseHandler(DatabaseHandler databaseHandler) {
		this.databaseHandler = databaseHandler;
	}
	
	public DatabaseHandler getDatabaseHandler() {
		return databaseHandler;
	}
	
	@Override
	public void create(Question question) {
		databaseHandler.create(question);
	}

	@Override
	public void delete(Question question) {
		databaseHandler.delete(question);
	}

	@Override
	public void update(Question question) {
		databaseHandler.update(question);
	}

	@Override
	public Question get(Integer id) {
		final Session session = databaseHandler.getSessionFactory().openSession();
		final Query query = session.createQuery("from Question where id =: id");
		query.setParameter("id",id);
		final Question question = (Question) query.uniqueResult();
		session.close();
		return question;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Question> getQuestionsByQuiz(Integer idQuiz) {
		final Session session = databaseHandler.getSessionFactory().openSession();
		final Query query = session.createQuery("select que from Question as que, QuizQuestion as qq, Quiz as qui "
				+ "where qui.id = :idQuiz and que.id = qq.question and que.id = qq.question");
		query.setParameter("idQuiz",idQuiz);
		final List<Question> questions = query.list();
		session.close();
		return questions;
	}
	
	public List<Question> getRandomQuestions(String tagValue, Integer limit) {
		final Session session = databaseHandler.getSessionFactory().openSession();
		String hql = "from Question Q group by Q.text order by rand()";
		final Query query = session.createQuery(hql);
		query.setMaxResults(limit);
		return null;
	}

}