package it.unical.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import it.unical.entities.Membership;

@SuppressWarnings("unused")
public class MembershipDAOImpl implements MembershipDAO {

	private DatabaseHandler databaseHandler;

	public MembershipDAOImpl() {
		databaseHandler = null;
	}

	public DatabaseHandler getDatabaseHandler() {
		return databaseHandler;
	}

	public void setDatabaseHandler(DatabaseHandler databaseHandler) {
		this.databaseHandler = databaseHandler;
	}

	@Override
	public void create(Membership membership) {
		databaseHandler.create(membership);
	}

	@Override
	public void delete(Membership membership) {
		databaseHandler.delete(membership);
	}

	@Override
	public void update(Membership membership) {
		databaseHandler.update(membership);
	}


	@Override
	public Membership get(Integer id) {
		Session session = databaseHandler.getSessionFactory().openSession();
		Query query = session.createQuery("from membership where id = :id");
		query.setParameter("id", id);
		Membership membership = (Membership) query.uniqueResult();
		session.close();
		return membership;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Membership> getTeamByStudent(Integer student)
	{
		Session session = databaseHandler.getSessionFactory().openSession();
		Query query = session.createQuery("from Membership where user_matricola = :student");
		query.setParameter("student", student);
		List<Membership> membership = (List<Membership>) query.list();
		session.close();
		return membership;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Membership> getStudentsByTeam(Integer team)
	{
		Session session = databaseHandler.getSessionFactory().openSession();
		Query query = session.createQuery("from Membership where team_idteam = :team");
		query.setParameter("team", team);
		List<Membership> students = (List<Membership>) query.list();
		session.close();
		return students;
	}
}