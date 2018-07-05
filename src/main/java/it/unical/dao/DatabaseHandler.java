package it.unical.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class DatabaseHandler {

	public static final Integer NO_ID = -5;

	private SessionFactory sessionFactory;

	private static enum Operation {
		CREATE, UPDATE, DELETE
	};

	public DatabaseHandler() {
		sessionFactory = null;
	}

	private void performOperation(Object object, Operation operation) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();
			switch (operation) {
			case CREATE:
				session.save(object);
				break;
			case UPDATE:
				session.update(object);
				break;
			case DELETE:
				session.delete(object);
				break;
			}

			transaction.commit();
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
		} finally {
			session.close();
		}
	}

	protected void create(Object object) {
		performOperation(object, Operation.CREATE);
	}

	protected void delete(Object object) {
		
		performOperation(object, Operation.DELETE);
	}

	protected void update(Object object) {
		performOperation(object, Operation.UPDATE);
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}