package it.unical.dao;

import org.hibernate.Query;
import org.hibernate.Session;

import it.unical.entities.QuizConstraint;

public class QuizConstraintDAOImpl implements QuizConstraintDAO
{

	private DatabaseHandler databaseHandler;

	@Override
	public void create(QuizConstraint quizConstraint)
	{
		databaseHandler.create(quizConstraint);
	}

	@Override
	public void delete(QuizConstraint quizConstraint)
	{
		databaseHandler.delete(quizConstraint);
	}

	@Override
	public void deleteByContest(Integer idContest)
	{
		final Session session = databaseHandler.getSessionFactory().openSession();
		final Query query = session
				.createQuery("delete from QuizConstraint QC WHERE QC.contest.idcontest = :idContest");
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
	public void update(QuizConstraint quizConstraint)
	{
		databaseHandler.update(quizConstraint);
	}

}
