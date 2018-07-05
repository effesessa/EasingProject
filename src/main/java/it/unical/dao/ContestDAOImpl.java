package it.unical.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import it.unical.entities.Contest;

@SuppressWarnings("unused")
public class ContestDAOImpl implements ContestDAO {

	private DatabaseHandler databaseHandler;

	public ContestDAOImpl() {
		databaseHandler = null;
	}

	public DatabaseHandler getDatabaseHandler() {
		return databaseHandler;
	}

	public void setDatabaseHandler(DatabaseHandler databaseHandler) {
		this.databaseHandler = databaseHandler;
	}

	@Override
	public void create(Contest contest) {
		databaseHandler.create(contest);
	}

	@Override
	public void delete(Contest contest) {
		databaseHandler.delete(contest);
	}

	@Override
	public void update(Contest contest) {
		databaseHandler.update(contest);
	}


	@Override
	public Contest get(Integer id) {
		final Session session = databaseHandler.getSessionFactory().openSession();
		final Query query = session.createQuery("from Contest where idcontest = :idcontest");
		query.setParameter("idcontest", id);
		final Contest contest = (Contest) query.uniqueResult();
		session.close();
		return contest;
	}
	
	@Override
	public Integer getIdByName(String name) {
		final Session session = databaseHandler.getSessionFactory().openSession();
		final Query query = session.createQuery("select idcontest from Contest where name = :name");
		query.setParameter("name", name);
		final Integer id = (Integer) query.uniqueResult();
		session.close();
		return id;
	}

	@Override
	public List<Contest> getAll() {
		final Session session = databaseHandler.getSessionFactory().openSession();
		final List<Contest> contests = session.createQuery("from Contest").list();
		session.close();
		return contests;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Contest> getContestBySubject(Integer subject, Integer year) {
		final Session session = databaseHandler.getSessionFactory().openSession();
		final Query query = session.createQuery("from Contest where id_subject = :subject and year = :year");
		query.setParameter("subject", subject);
		query.setParameter("year", year);
		final List<Contest> contests =  query.list();
		session.close();
		return contests;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Contest> getContestByJury(Integer jury) {
		final Session session = databaseHandler.getSessionFactory().openSession();
		final Query query = session.createQuery("from Contest where id_jury= :jury");
		query.setParameter("jury", jury);
		final List<Contest> contests = query.list();
		session.close();
		return contests;
	}

	@Override
	public Contest getContestByName(String name) {
		final Session session = databaseHandler.getSessionFactory().openSession();
		final Query query = session.createQuery("from Contest where name = :name");
		query.setParameter("name", name);
		final Contest contests =  (Contest) query.uniqueResult();
		session.close();
		return contests;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getContestsNamesByProfessor(Integer id)
	{
		final Session session = databaseHandler.getSessionFactory().openSession();
		final Query query = session.createQuery("SELECT co.name FROM Contest co, JuryMember WHERE id_jury = jury_idjury AND user_iduser = :id");
		query.setParameter("id", id);
		final List<String> contests = query.list();
		session.close();
		return contests;
	}
}
