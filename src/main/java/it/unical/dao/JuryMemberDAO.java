package it.unical.dao;

import java.util.List;

import it.unical.entities.JuryMember;

public interface JuryMemberDAO {

	public void create(JuryMember juryMember);

	public void delete(JuryMember juryMember);

	public void update(JuryMember juryMember);

	JuryMember get(Integer id);

	List<JuryMember> getJurysFromProfessor(Integer professor);
}