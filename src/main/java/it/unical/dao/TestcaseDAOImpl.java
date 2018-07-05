package it.unical.dao;

import org.hibernate.Query;
import org.hibernate.Session;

import it.unical.entities.Testcase;



@SuppressWarnings("unused")
public class TestcaseDAOImpl implements TestcaseDAO {

	private DatabaseHandler databaseHandler;

	public TestcaseDAOImpl() {
		databaseHandler = null;
	}

	public DatabaseHandler getDatabaseHandler() {
		return databaseHandler;
	}

	public void setDatabaseHandler(DatabaseHandler databaseHandler) {
		this.databaseHandler = databaseHandler;
	}

	@Override
	public void create(Testcase testcase) {
		databaseHandler.create(testcase);
	}

	@Override
	public void delete(Testcase testcase) {
		databaseHandler.delete(testcase);
	}

	@Override
	public void update(Testcase testcase) {
		databaseHandler.update(testcase);
	}


	@Override
	public Testcase get(Integer id) {
		Session session = databaseHandler.getSessionFactory().openSession();
		Query query = session.createQuery("from testcase where id = :id");
		query.setParameter("id", id);
		Testcase testcase = (Testcase) query.uniqueResult();
		session.close();
		return testcase;
	}
}