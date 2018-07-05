package it.unical.dao;

import org.hibernate.Query;
import org.hibernate.Session;

import it.unical.entities.Jury;

@SuppressWarnings("unused")
public class JuryDAOImpl implements JuryDAO {

	private DatabaseHandler databaseHandler;

	public JuryDAOImpl() {
		databaseHandler = null;
	}

	public DatabaseHandler getDatabaseHandler() {
		return databaseHandler;
	}

	public void setDatabaseHandler(DatabaseHandler databaseHandler) {
		this.databaseHandler = databaseHandler;
	}

	@Override
	public void create(Jury jury) {
		databaseHandler.create(jury);
	}

	@Override
	public void delete(Jury jury) {
		databaseHandler.delete(jury);
	}

	@Override
	public void update(Jury jury) {
		databaseHandler.update(jury);
	}


	@Override
	public Jury get(Integer id) {
		Session session = databaseHandler.getSessionFactory().openSession();
		Query query = session.createQuery("from Jury where id = :id");
		query.setParameter("id", id);
		Jury jury = (Jury) query.uniqueResult();
		session.close();
		return jury;
	}
}