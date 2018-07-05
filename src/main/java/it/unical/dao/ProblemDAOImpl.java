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
public class ProblemDAOImpl implements ProblemDAO {

	private DatabaseHandler databaseHandler;

	public ProblemDAOImpl() {
		databaseHandler = null;
	}

	public DatabaseHandler getDatabaseHandler() {
		return databaseHandler;
	}

	public void setDatabaseHandler(DatabaseHandler databaseHandler) {
		this.databaseHandler = databaseHandler;
	}

	@Override
	public void create(Problem problem) {
		databaseHandler.create(problem);
	}

	@Override
	public void delete(Problem problem) {
		databaseHandler.delete(problem);
	}

	@Override
	public void update(Problem problem) {
		databaseHandler.update(problem);
	}


	@Override
	public Problem get(Integer id) {
		Session session = databaseHandler.getSessionFactory().openSession();
		Query query = session.createQuery("from Problem where id = :id");
		query.setParameter("id", id);
		Problem problem = (Problem) query.uniqueResult();
		session.close();
		return problem;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Problem> getByName(String name) {
		Session session = databaseHandler.getSessionFactory().openSession();
		Query query = session.createQuery("from Problem where name = :name");
		query.setParameter("name", name);
		List<Problem> problem =  query.list();
		session.close();
		return problem;
	}
	
	@Override
	public List<Problem> getProblemOfAContest(Integer contest) {
		Session session = databaseHandler.getSessionFactory().openSession();
		Query query = session.createQuery("from Problem where contest_idcontest = :contest");
		query.setParameter("contest", contest);
		List<Problem> problems = query.list();
		session.close();
		return problems;
	}
	
	
	
}