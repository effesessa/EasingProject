package it.unical.dao;

import java.util.List;

import it.unical.entities.Submit;

public interface SubmitDAO
{

	public void create(Submit submit);

	public void delete(Submit submit);

	Submit get(Integer id);

	public List<Submit> getAllSubmitByProblem(Integer problem);

	public List<Submit> getAllSubmitByProblemAndTeam(Integer problem, Integer team);

	Submit getAllSubmitByProblemAndTeamFake(Integer problem, Integer team);

	public List<Submit> getAllSubmitByTeam(Integer team);

	public List<Submit> getAllSubmitTeam(Integer team);

	public void update(Submit submit);
}