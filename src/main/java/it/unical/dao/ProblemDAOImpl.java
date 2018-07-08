package it.unical.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;

import it.unical.entities.Contest;
import it.unical.entities.Problem;

@SuppressWarnings("unused")
public class ProblemDAOImpl implements ProblemDAO
{

	private DatabaseHandler databaseHandler;

	public ProblemDAOImpl()
	{
		databaseHandler = null;
	}

	@Override
	public void create(Problem problem)
	{
		databaseHandler.create(problem);
	}

	@Override
	public void delete(Problem problem)
	{
		databaseHandler.delete(problem);
	}

	@Override
	public Problem get(Integer id)
	{
		final Session session = databaseHandler.getSessionFactory().openSession();
		final Query query = session.createQuery("from Problem where id = :id");
		query.setParameter("id", id);
		final Problem problem = (Problem) query.uniqueResult();
		session.close();
		return problem;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Problem> getByName(String name)
	{
		final Session session = databaseHandler.getSessionFactory().openSession();
		final Query query = session.createQuery("from Problem where name = :name");
		query.setParameter("name", name);
		final List<Problem> problem = query.list();
		session.close();
		return problem;
	}

	public DatabaseHandler getDatabaseHandler()
	{
		return databaseHandler;
	}

	@Override
	public List<Problem> getProblemOfAContest(Integer contest)
	{
		final Session session = databaseHandler.getSessionFactory().openSession();
		final Query query = session.createQuery("from Problem where contest_idcontest = :contest");
		query.setParameter("contest", contest);
		final List<Problem> problems = query.list();
		session.close();
		return problems;
	}

	@Override
	public List<Problem> getProblemsByProfessor(Integer id)
	{
		final Session session = databaseHandler.getSessionFactory().openSession();
		final Query query = session.createQuery(
				"SELECT P FROM Problem AS P, Jury AS J WHERE P.jury = J.id_jury AND J.professor.id = :profID");
		// Query query = session.createQuery("from Problem where
		// contest_idcontest = :contest");
		// SELECT * FROM easingdb.problem AS P, jury AS J WHERE P.id_jury =
		// J.id_jury AND J.jury_leader = 222
		query.setParameter("profID", id);
		final List<Problem> problems = query.list();
		session.close();
		return problems;
	}

	public void setDatabaseHandler(DatabaseHandler databaseHandler)
	{
		this.databaseHandler = databaseHandler;
	}

	@Override
	public void update(Problem problem)
	{
		databaseHandler.update(problem);
	}

}