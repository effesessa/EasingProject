package it.unical.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import it.unical.entities.User;


@SuppressWarnings("unused")
public class UserDAOImpl implements UserDAO {

	private DatabaseHandler databaseHandler;

	public UserDAOImpl() {
		databaseHandler = null;
	}

	public DatabaseHandler getDatabaseHandler() {
		return databaseHandler;
	}

	public void setDatabaseHandler(DatabaseHandler databaseHandler) {
		this.databaseHandler = databaseHandler;
	}

	@Override
	public void create(User user) {
		databaseHandler.create(user);
	}

	@Override
	public void delete(User user) {
		databaseHandler.delete(user);
	}

	@Override
	public void update(User user) {
		databaseHandler.update(user);
	}


	@Override
	public User get(String email) {
		Session session = databaseHandler.getSessionFactory().openSession();
		Query query = session.createQuery("from User where email = :email");
		query.setParameter("email", email);
		User user = (User) query.uniqueResult();
		session.close();
		return user;
	}

	@Override
	public User get(Integer id) {
		Session session = databaseHandler.getSessionFactory().openSession();
		Query query = session.createQuery("from User where id = :id");
		query.setParameter("id", id);
		User user = (User) query.uniqueResult();
		session.close();
		return user;
	}

	@Override
	public List<User> getAll() {
		Session session = databaseHandler.getSessionFactory().openSession();
		List<User> users = session.createQuery("from User").list();
		session.close();
		return users;
	}
}