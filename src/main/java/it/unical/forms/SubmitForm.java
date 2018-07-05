package it.unical.forms;

import org.springframework.web.multipart.MultipartFile;

public class SubmitForm
{

	private Integer idProblem;
	private MultipartFile solution;
	private String team;

	public SubmitForm()
	{
		this.idProblem = null;
		this.solution = null;
		this.team = "";
	}

	public Integer getIdProblem()
	{
		return idProblem;
	}

	public MultipartFile getSolution()
	{
		return solution;
	}

	public String getTeam()
	{
		return team;
	}

	public void setIdProblem(Integer idProblem)
	{
		this.idProblem = idProblem;
	}

	public void setSolution(MultipartFile solution)
	{
		this.solution = solution;
	}

	public void setTeam(String team)
	{
		this.team = team;
	}
}
