package it.unical.dao;

import java.util.List;

import it.unical.entities.Contest;
import it.unical.entities.Subject;
import it.unical.entities.User;

public interface SubjectDAO
{

	public void create(Subject subject);

	public void delete(Subject subject);

	Subject get(Integer id);

	Subject get(String name);

	public List<Subject> getAll();

	List<Subject> getAllSubjectFromProfessor(Integer professor);

	public List<User> getAllUserByProblem(Contest contest);

	long getLastID();

	public void update(Subject subject);
}