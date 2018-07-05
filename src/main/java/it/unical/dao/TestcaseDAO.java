package it.unical.dao;

import it.unical.entities.Testcase;

public interface TestcaseDAO {

	public void create(Testcase testcase);

	public void delete(Testcase testcase);

	public void update(Testcase testcase);

	Testcase get(Integer id);
}