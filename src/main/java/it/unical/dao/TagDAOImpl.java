package it.unical.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import it.unical.entities.Tag;

/**
 * @author Fabrizio
 */

public class TagDAOImpl implements TagDAO
{

	private static final int POPULAR_TAGS = 5;
	private DatabaseHandler databaseHandler;

	public TagDAOImpl()
	{
		databaseHandler = null;
	}

	@Override
	public void create(Tag tag)
	{
		databaseHandler.create(tag);
	}

	@Override
	public void delete(Tag tag)
	{
		databaseHandler.delete(tag);
	}

	@Override
	public void deleteAllTagsByProblem(Integer id_problem)
	{
		final Session session = databaseHandler.getSessionFactory().openSession();
		final Query query = session.createQuery("DELETE FROM Tag T WHERE T.problem.id_problem = :id_problem");
		query.setParameter("id_problem", id_problem);
		query.executeUpdate();
		session.close();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tag> getAllTagsByProblem(Integer problem)
	{
		final Session session = databaseHandler.getSessionFactory().openSession();
		final Query query = session.createQuery("from Tag T where T.problem.id_problem = :problem order by value asc");
		query.setParameter("problem", problem);
		final List<Tag> tags = query.list();
		session.close();
		return tags;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAllTagValues()
	{
		final Session session = databaseHandler.getSessionFactory().openSession();
		final List<String> tagValues = session.createQuery("select distinct(value) from Tag order by value asc").list();
		session.close();
		return tagValues;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAllTagValuesByProblem(Integer problem)
	{
		final Session session = databaseHandler.getSessionFactory().openSession();
		final Query query = session.createQuery("select values from Tag where problem = :problem order by value asc");
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
				"SELECT value, COUNT(value) AS value_occurrence FROM Tag GROUP BY value ORDER BY value_occurrence DESC");
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
	public void update(Tag tag)
	{
		databaseHandler.update(tag);
	}

}
