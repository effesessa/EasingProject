package it.unical.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import it.unical.entities.JuryMember;
import it.unical.entities.Subject;

@SuppressWarnings("unused")
public class JuryMemberDAOImpl implements JuryMemberDAO {

	private DatabaseHandler databaseHandler;

	public JuryMemberDAOImpl() {
		databaseHandler = null;
	}

	public DatabaseHandler getDatabaseHandler() {
		return databaseHandler;
	}

	public void setDatabaseHandler(DatabaseHandler databaseHandler) {
		this.databaseHandler = databaseHandler;
	}

	@Override
	public void create(JuryMember juryMember) {
		databaseHandler.create(juryMember);
	}

	@Override
	public void delete(JuryMember juryMember) {
		databaseHandler.delete(juryMember);
	}

	@Override
	public void update(JuryMember juryMember) {
		databaseHandler.update(juryMember);
	}


	@Override
	public JuryMember get(Integer id) {
		Session session = databaseHandler.getSessionFactory().openSession();
		Query query = session.createQuery("from JuryMember where id = :id");
		query.setParameter("id", id);
		JuryMember juryMember = (JuryMember) query.uniqueResult();
		session.close();
		return juryMember;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<JuryMember> getJurysFromProfessor(Integer professor) {
		Session session = databaseHandler.getSessionFactory().openSession();
		Query query = session.createQuery("from JuryMember where user_iduser = :professor");
		query.setParameter("professor", professor);
		List<JuryMember> juries = (List<JuryMember>) query.list();
		session.close();
		return juries;
	}
}