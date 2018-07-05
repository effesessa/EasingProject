package it.unical.dao;

import org.hibernate.Query;
import org.hibernate.Session;


import it.unical.entities.Team;



@SuppressWarnings("unused")
public class TeamDAOImpl implements TeamDAO {

	private DatabaseHandler databaseHandler;

	public TeamDAOImpl() {
		databaseHandler = null;
	}

	public DatabaseHandler getDatabaseHandler() {
		return databaseHandler;
	}

	public void setDatabaseHandler(DatabaseHandler databaseHandler) {
		this.databaseHandler = databaseHandler;
	}

	@Override
	public void create(Team team) {
		databaseHandler.create(team);
	}

	@Override
	public void delete(Team team) {
		databaseHandler.delete(team);
	}

	@Override
	public void update(Team team) {
		databaseHandler.update(team);
	}


	@Override
	public Team get(Integer id) {
		Session session = databaseHandler.getSessionFactory().openSession();
		Query query = session.createQuery("from Team where id_team = :id");
		query.setParameter("id", id);
		Team team = (Team) query.uniqueResult();
		session.close();
		return team;
	}

	@Override
	public Team getByName(String name) {
		Session session = databaseHandler.getSessionFactory().openSession();
		Query query = session.createQuery("from Team where name = :name");
		query.setParameter("name", name);
		Team team = (Team) query.uniqueResult();
		session.close();
		return team;
	}
}