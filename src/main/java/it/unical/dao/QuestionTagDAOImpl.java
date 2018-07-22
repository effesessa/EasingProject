package it.unical.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import it.unical.entities.QuestionTag;

public class QuestionTagDAOImpl implements QuestionTagDAO {
	
private static final int POPULAR_TAGS = 5;
	
	private DatabaseHandler databaseHandler;

	public QuestionTagDAOImpl() {
		databaseHandler = null;
	}

	@Override
	public void create(QuestionTag questionTag) {
		databaseHandler.create(questionTag);
	}

	@Override
	public void delete(QuestionTag questionTag) {
		databaseHandler.delete(questionTag);
	}
	
	@Override
	public void update(QuestionTag questionTag) {
		databaseHandler.update(questionTag);
	}
	
	public void setDatabaseHandler(DatabaseHandler databaseHandler) {
		this.databaseHandler = databaseHandler;
	}
	
	public DatabaseHandler getDatabaseHandler() {
		return databaseHandler;
	}

	@Override
	public void deleteAllTagsByQuestion(Integer idQuestion) {
		final Session session = databaseHandler.getSessionFactory().openSession();
		final Query query = session.createQuery("delete from QuestionTag T WHERE T.question.id = :idQuestion");
		query.setParameter("idQuestion", idQuestion);
		query.executeUpdate();
		session.close();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<QuestionTag> getAllTagsByQuestion(Integer idQuestion) {
		final Session session = databaseHandler.getSessionFactory().openSession();
		final Query query = session.createQuery("from QuestionTag T where T.question.id = :idQuestion order by value asc");
		query.setParameter("idQuestion", idQuestion);
		final List<QuestionTag> tags = query.list();
		session.close();
		return tags;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAllTagValues() {
		final Session session = databaseHandler.getSessionFactory().openSession();
		final List<String> tagValues = session.createQuery("select distinct(value) from QuestionTag order by value asc").list();
		session.close();
		return tagValues;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAllTagValuesByQuestion(Integer idQuestion) {
		final Session session = databaseHandler.getSessionFactory().openSession();
		final Query query = session.createQuery("select values from QuestionTag T where T.question.id = :idQuestion order by value asc");
		query.setParameter("idQuestion", idQuestion);
		final List<String> tagValues = query.list();
		session.close();
		return tagValues;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getMostPopularTags() {
		final Session session = databaseHandler.getSessionFactory().openSession();
		String hql = "SELECT value, COUNT(value) AS value_occurrence FROM QuestionTag GROUP BY value ORDER BY value_occurrence DESC";
		final Query query = session.createQuery(hql);
		query.setMaxResults(POPULAR_TAGS);
		final List<String> tagValues = query.list();
		session.close();
		return tagValues;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<QuestionTag> getByValue(String value) {
		final Session session = databaseHandler.getSessionFactory().openSession();
		final Query query = session.createQuery("from QuestionTag T where T.value = :value");
		query.setParameter("value", value);
		List<QuestionTag> questionTags = query.list();
		session.close();
		return questionTags;
	}
}
