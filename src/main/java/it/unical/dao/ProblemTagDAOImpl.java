package it.unical.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import it.unical.entities.ProblemTag;

/**
 * @author Fabrizio
 */

public class ProblemTagDAOImpl implements ProblemTagDAO
{

	private static final int POPULAR_TAGS = 5;
	
	private DatabaseHandler databaseHandler;

	public ProblemTagDAOImpl()
	{
		databaseHandler = null;
	}

	@Override
	public void create(ProblemTag tag)
	{
		databaseHandler.create(tag);
	}

	@Override
	public void delete(ProblemTag tag)
	{
		databaseHandler.delete(tag);
	}

	@Override
	public void deleteAllTagsByProblem(Integer id_problem)
	{
		final Session session = databaseHandler.getSessionFactory().openSession();
		final Query query = session.createQuery("DELETE FROM ProblemTag T WHERE T.problem.id_problem = :id_problem");
		query.setParameter("id_problem", id_problem);
		query.executeUpdate();
		session.close();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProblemTag> getAllTagsByProblem(Integer problem)
	{
		final Session session = databaseHandler.getSessionFactory().openSession();
		final Query query = session.createQuery("from ProblemTag T where T.problem.id_problem = :problem order by value asc");
		query.setParameter("problem", problem);
		final List<ProblemTag> tags = query.list();
		session.close();
		return tags;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ProblemTag> getByValue(String value)
	{
		final Session session = databaseHandler.getSessionFactory().openSession();
		final Query query = session.createQuery("from ProblemTag T where T.value = :value");
		query.setParameter("value", value);
		final List<ProblemTag> problemTags = query.list();
		session.close();
		return problemTags;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAllTagValues()
	{
		final Session session = databaseHandler.getSessionFactory().openSession();
		final List<String> tagValues = session.createQuery("select distinct(value) from ProblemTag order by value asc").list();
		session.close();
		return tagValues;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAllTagValuesByProblem(Integer problem)
	{
		final Session session = databaseHandler.getSessionFactory().openSession();
		final Query query = session.createQuery("select values from ProblemTag where problem = :problem order by value asc");
		query.setParameter("problem", problem);
		final List<String> tagValues = query.list();
		session.close();
		return tagValues;
	}

	public DatabaseHandler getDatabaseHandler()
	{
		return databaseHandler;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getMostPopularTags()
	{
		final Session session = databaseHandler.getSessionFactory().openSession();
		final Query query = session.createQuery(
				"SELECT value, COUNT(value) AS value_occurrence FROM ProblemTag GROUP BY value ORDER BY value_occurrence DESC");
		query.setMaxResults(POPULAR_TAGS);
		final List<String> tagValues = query.list();
		session.close();
		return tagValues;
	}

	public void setDatabaseHandler(DatabaseHandler databaseHandler)
	{
		this.databaseHandler = databaseHandler;
	}

	@Override
	public void update(ProblemTag tag)
	{
		databaseHandler.update(tag);
	}

}
