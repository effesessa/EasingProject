package it.unical.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import it.unical.entities.Partecipation;

@SuppressWarnings("unused")
public class PartecipationDAOImpl implements PartecipationDAO {

	private DatabaseHandler databaseHandler;

	public PartecipationDAOImpl() {
		databaseHandler = null;
	}

	public DatabaseHandler getDatabaseHandler() {
		return databaseHandler;
	}

	public void setDatabaseHandler(DatabaseHandler databaseHandler) {
		this.databaseHandler = databaseHandler;
	}

	@Override
	public void create(Partecipation partecipation) {
		databaseHandler.create(partecipation);
	}

	@Override
	public void delete(Partecipation partecipation) {
		databaseHandler.delete(partecipation);
	}

	@Override
	public void update(Partecipation partecipation) {
		databaseHandler.update(partecipation);
	}


	@Override
	public Partecipation get(Integer id) {
		Session session = databaseHandler.getSessionFactory().openSession();
		Query query = session.createQuery("from Partecipation where id = :id");
		query.setParameter("id", id);
		Partecipation partecipation = (Partecipation) query.uniqueResult();
		session.close();
		return partecipation;
	}
	
	@Override
	public Partecipation getTeamPartecipation(Integer team, Integer contest) {
		Session session = databaseHandler.getSessionFactory().openSession();
		Query query = session.createQuery("from Partecipation where contest_idcontest = :contest and team_idteam = :team");
		query.setParameter("contest", contest);
		query.setParameter("team", team);
		Partecipation partecipation = (Partecipation) query.uniqueResult();
		session.close();
		return partecipation;
	}

	@Override
	public List<Partecipation> getContestByTeam(Integer team) {
		Session session = databaseHandler.getSessionFactory().openSession();
		Query query = session.createQuery("from Partecipation where team_idteam = :team");
		query.setParameter("team", team);
		List<Partecipation> partecipation = (List<Partecipation>) query.list();
		session.close();
		return partecipation;
	}
}