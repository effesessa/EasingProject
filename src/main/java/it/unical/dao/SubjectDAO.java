package it.unical.dao;

import java.util.List;

import it.unical.entities.Subject;

public interface SubjectDAO {

	public void create(Subject subject);

	public void delete(Subject subject);

	public void update(Subject subject);

	Subject get(Integer id);

	public List<Subject> getAll();
	
	Subject get(String name);

	List<Subject> getAllSubjectFromProfessor(Integer professor);
}