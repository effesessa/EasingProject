package it.unical.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import it.unical.entities.Membership;
import it.unical.entities.Registration;
import it.unical.entities.SubjectId;


@SuppressWarnings("unused")
public class RegistrationDAOImpl implements RegistrationDAO {

	private DatabaseHandler databaseHandler;

	public RegistrationDAOImpl() {
		databaseHandler = null;
	}

	public DatabaseHandler getDatabaseHandler() {
		return databaseHandler;
	}

	public void setDatabaseHandler(DatabaseHandler databaseHandler) {
		this.databaseHandler = databaseHandler;
	}

	@Override
	public void create(Registration registration) {
		databaseHandler.create(registration);
	}

	@Override
	public void delete(Registration registration) {
		databaseHandler.delete(registration);
	}

	@Override
	public void update(Registration registration) {
		databaseHandler.update(registration);
	}


	@Override
	public Registration get(Integer id) {
		Session session = databaseHandler.getSessionFactory().openSession();
		Query query = session.createQuery("from registration where id = :id");
		query.setParameter("id", id);
		Registration registration = (Registration) query.uniqueResult();
		session.close();
		return registration;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Registration> getRegistrationByStudent(Integer student)
	{
		Session session = databaseHandler.getSessionFactory().openSession();
		Query query = session.createQuery("from Registration where user_matricola = :student");
		query.setParameter("student", student);
		List<Registration> registrations = (List<Registration>) query.list();
		session.close();
		return registrations;
	}
	
	@Override
	public Registration getRegistration(Integer student, SubjectId subjectId) {
		Session session = databaseHandler.getSessionFactory().openSession();
		Query query = session.createQuery("from Registration where user_matricola = :student and subject_idsubject = :subject and year = :year");
		query.setParameter("student", student);
		query.setParameter("subject", subjectId.getId_subject());
		query.setParameter("year", Integer.parseInt(subjectId.getYear()));
		Registration registration = (Registration) query.uniqueResult();
		session.close();
		return registration;
	}
}