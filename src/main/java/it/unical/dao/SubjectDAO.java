package it.unical.dao;

import java.math.BigInteger;
import java.util.List;

import it.unical.entities.Subject;

public interface SubjectDAO
{

	public void create(Subject subject);

	public void delete(Subject subject);

	Subject get(Integer id);

	Subject get(String name);

	public List<Subject> getAll();

	List<Subject> getAllSubjectFromProfessor(Integer professor);

	long getLastID();

	public void update(Subject subject);
}