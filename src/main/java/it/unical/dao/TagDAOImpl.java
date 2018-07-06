package it.unical.dao;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import it.unical.entities.Tag;

/**
 * @author Fabrizio
 */

public class TagDAOImpl implements TagDAO {

	private DatabaseHandler databaseHandler;
	
	public TagDAOImpl() {
		databaseHandler = null;
	}

	public DatabaseHandler getDatabaseHandler() {
		return databaseHandler;
	}

	public void setDatabaseHandler(DatabaseHandler databaseHandler) {
		this.databaseHandler = databaseHandler;
	}
	
	@Override
	public void create(Tag tag) {
		databaseHandler.create(tag);
	}

	@Override
	public void delete(Tag tag) {
		databaseHandler.delete(tag);
	}

	@Override
	public void update(Tag tag) {
		databaseHandler.update(tag);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAllTagValues() {
		Session session = databaseHandler.getSessionFactory().openSession();
		List<String> tagValues = session.createQuery("select distinct(value) from Tag order by value asc").list();
		session.close();
		return tagValues;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tag> getAllTagsByProblem(Integer problem) {
		Session session = databaseHandler.getSessionFactory().openSession();
		Query query = session.createQuery("from Tag where problem = :problem order by value asc");
		query.setParameter("problem", problem);
		List<Tag> tags = query.list();
		session.close();
		return tags;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAllTagValuesByProblem(Integer problem) {
		Session session = databaseHandler.getSessionFactory().openSession();
		Query query = session.createQuery("select values from Tag where problem = :problem order by value asc");
		query.setParameter("problem", problem);
		List<String> tagValues = query.list();
		session.close();
		return tagValues;
	}

}
