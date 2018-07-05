package it.unical.forms;

import org.springframework.web.multipart.MultipartFile;

public class AddProblemForm
{

	private String contestName;
	private String name;
	private String type;
	private String description;
	private String domain;

	private int timeout;
	private MultipartFile download;
	private MultipartFile testcase;
	private MultipartFile output;

	// TODO Da eliminare una volta cambiato ProblemController
	private String pathTest;
	private String pathSol;
	private String pathZip;
	private String pathAlgorithm;

	public AddProblemForm()
	{
		this.contestName = "";
		this.name = "";
		this.type = "";
		this.description = "";
		this.timeout = 1000;
		this.download = null;
		this.testcase = null;
		this.output = null;

		this.pathTest = "";
		this.pathSol = "";
	}

	public String getContestName()
	{
		return contestName;
	}

	public String getDescription()
	{
		return description;
	}

	public String getDomain()
	{
		return domain;
	}

	public MultipartFile getDownload()
	{
		return download;
	}

	public String getName()
	{
		return name;
	}

	public MultipartFile getOutput()
	{
		return output;
	}

	public String getPathAlgorithm()
	{
		return pathAlgorithm;
	}

	public String getPathSol()
	{
		return pathSol;
	}

	public String getPathTest()
	{
		return pathTest;
	}

	public String getPathZip()
	{
		return pathZip;
	}

	public MultipartFile getTestcase()
	{
		return testcase;
	}

	public int getTimeout()
	{
		return timeout;
	}

	public String getType()
	{
		return type;
	}

	public void setContestName(String name)
	{
		this.contestName = name;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public void setDomain(String domain)
	{
		this.domain = domain;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setPathAlgorithm(String pathAlgorithm)
	{
		this.pathAlgorithm = pathAlgorithm;
	}

	public void setPathSol(String pathSol)
	{
		this.pathSol = pathSol;
	}

	public void setPathTest(String pathTest)
	{
		this.pathTest = pathTest;
	}

	public void setPathZip(String pathZip)
	{
		this.pathZip = pathZip;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public void setDownload(MultipartFile download) {
		this.download = download;
	}

	public void setTestcase(MultipartFile testcase) {
		this.testcase = testcase;
	}

	public void setOutput(MultipartFile output) {
		this.output = output;
	}
}