package it.unical.dao;

import org.hibernate.Query;
import org.hibernate.Session;

import it.unical.entities.ProblemConstraint;

public class ProblemConstraintDAOImpl implements ProblemConstraintDAO
{

	private DatabaseHandler databaseHandler;

	@Override
	public void create(ProblemConstraint problemConstraint)
	{
		databaseHandler.create(problemConstraint);
	}

	@Override
	public void delete(ProblemConstraint problemConstraint)
	{
		databaseHandler.delete(problemConstraint);
	}

	@Override
	public void deleteByContest(Integer idContest)
	{
		final Session session = databaseHandler.getSessionFactory().openSession();
		final Query query = session
				.createQuery("delete from ProblemConstraint PC WHERE PC.contest.idcontest = :idContest");
		query.setParameter("idContest", idContest);
		query.executeUpdate();
		session.close();
	}

	public DatabaseHandler getDatabaseHandler()
	{
		return databaseHandler;
	}

	public void setDatabaseHandler(DatabaseHandler databaseHandler)
	{
		this.databaseHandler = databaseHandler;
	}

	@Override
	public void update(ProblemConstraint problemConstraint)
	{
		databaseHandler.update(problemConstraint);
	}

}
