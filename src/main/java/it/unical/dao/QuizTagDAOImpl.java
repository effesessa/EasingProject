package it.unical.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import it.unical.entities.QuizTag;

public class QuizTagDAOImpl implements QuizTagDAO {
	
private static final int POPULAR_TAGS = 5;
	
	private DatabaseHandler databaseHandler;

	public QuizTagDAOImpl() {
		databaseHandler = null;
	}

	@Override
	public void create(QuizTag quizTag) {
		databaseHandler.create(quizTag);
	}

	@Override
	public void delete(QuizTag quizTag) {
		databaseHandler.delete(quizTag);
	}
	
	@Override
	public void update(QuizTag quizTag) {
		databaseHandler.update(quizTag);
	}

	@Override
	public void deleteAllTagsByQuiz(Integer idQuiz) {
		final Session session = databaseHandler.getSessionFactory().openSession();
		final Query query = session.createQuery("delete from QuizTag T WHERE T.quiz.id = :idQuiz");
		query.setParameter("idQuiz", idQuiz);
		query.executeUpdate();
		session.close();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<QuizTag> getAllTagsByQuiz(Integer idQuiz) {
		final Session session = databaseHandler.getSessionFactory().openSession();
		final Query query = session.createQuery("from QuizTag T where T.quiz.id = :idQuiz order by value asc");
		query.setParameter("idQuiz", idQuiz);
		final List<QuizTag> tags = query.list();
		session.close();
		return tags;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAllTagValues() {
		final Session session = databaseHandler.getSessionFactory().openSession();
		final List<String> tagValues = session.createQuery("select distinct(value) from QuizTag order by value asc").list();
		session.close();
		return tagValues;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAllTagValuesByQuiz(Integer idQuiz) {
		final Session session = databaseHandler.getSessionFactory().openSession();
		final Query query = session.createQuery("select values from QuizTag T where T.quiz.id = :idQuiz order by value asc");
		query.setParameter("idQuiz", idQuiz);
		final List<String> tagValues = query.list();
		session.close();
		return tagValues;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getMostPopularTags() {
		final Session session = databaseHandler.getSessionFactory().openSession();
		String hql = "SELECT value, COUNT(value) AS value_occurrence FROM QuizTag GROUP BY value ORDER BY value_occurrence DESC";
		final Query query = session.createQuery(hql);
		query.setMaxResults(POPULAR_TAGS);
		final List<String> tagValues = query.list();
		session.close();
		return tagValues;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<QuizTag> getByValue(String value) {
		final Session session = databaseHandler.getSessionFactory().openSession();
		final Query query = session.createQuery("from QuizTag T where T.value = :value");
		query.setParameter("value", value);
		List<QuizTag> quizTags = query.list();
		session.close();
		return quizTags;
	}
	
}
