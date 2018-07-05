package it.unical.dao;

import java.util.List;

import it.unical.entities.Membership;

public interface MembershipDAO {

	public void create(Membership membership);

	public void delete(Membership membership);

	public void update(Membership membership);

	Membership get(Integer id);

	List<Membership> getTeamByStudent(Integer student);

	List<Membership> getStudentsByTeam(Integer team);
}