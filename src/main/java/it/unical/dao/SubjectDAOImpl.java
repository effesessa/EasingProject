package it.unical.dao;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.type.StandardBasicTypes;

import it.unical.entities.Registration;
import it.unical.entities.Subject;
import it.unical.entities.User;

@SuppressWarnings("unused")
public class SubjectDAOImpl implements SubjectDAO
{

	private DatabaseHandler databaseHandler;

	public SubjectDAOImpl()
	{
		databaseHandler = null;
	}

	@Override
	public void create(Subject subject)
	{
		databaseHandler.create(subject);
	}

	@Override
	public void delete(Subject subject)
	{
		databaseHandler.delete(subject);
	}

	@Override
	public Subject get(Integer id)
	{
		final Session session = databaseHandler.getSessionFactory().openSession();
		final Query query = session.createQuery("from Subject where id_subject = :id");
		query.setParameter("id", id);
		final Subject subject = (Subject) query.uniqueResult();
		session.close();
		return subject;
	}

	@Override
	public Subject get(String name)
	{
		final Session session = databaseHandler.getSessionFactory().openSession();
		final Query query = session.createQuery("from Subject where name = :name");
		query.setParameter("name", name);
		final Subject subject = (Subject) query.uniqueResult();
		session.close();
		return subject;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Subject> getAll()
	{
		final Session session = databaseHandler.getSessionFactory().openSession();
		final List<Subject> subjects = session.createQuery("from Subject").list();
		session.close();
		return subjects;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Subject> getAllSubjectFromProfessor(Integer professor)
	{
		final Session session = databaseHandler.getSessionFactory().openSession();
		final Query query = session.createQuery("from Subject where user_professor = :professor");
		query.setParameter("professor", professor);
		final List<Subject> subjects = query.list();
		session.close();
		return subjects;
	}

	public DatabaseHandler getDatabaseHandler()
	{
		return databaseHandler;
	}

	@Override
	public long getLastID()
	{
		final Session session = databaseHandler.getSessionFactory().openSession();
		final Query query = session.createQuery("SELECT max(s.subjectId.id_subject) FROM Subject s");
		final Integer result = (Integer) query.uniqueResult();
		session.close();
		return result;

	}

	public void setDatabaseHandler(DatabaseHandler databaseHandler)
	{
		this.databaseHandler = databaseHandler;
	}

	@Override
	public void update(Subject subject)
	{
		databaseHandler.update(subject);
	}
}