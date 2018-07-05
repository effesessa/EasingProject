package it.unical.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import it.unical.dao.DatabaseHandler;

@Entity
@Table(name = "testcase")
public class Testcase
{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
 	@Column(name = "id_testcase")
	private Integer id_testcase;
	
	@Column(name = "input")
	private String input;
	
	@Column(name = "output")
	private String output;
	
	//relazione con problem
	@Column(name = "problem_idproblem")
	private Integer idProblem;
	
	public Testcase()
	{
		this.id_testcase = DatabaseHandler.NO_ID;
		this.input = null;
		this.output = null;
		this.idProblem = DatabaseHandler.NO_ID;
	}

	public Testcase(Integer id_testcase, String input, String output, Integer idProblem) {
		this.id_testcase = id_testcase;
		this.input = input;
		this.output = output;
		this.idProblem = idProblem;
	}

	public Integer getId_testcase() {
		return id_testcase;
	}

	public void setId_testcase(Integer id_testcase) {
		this.id_testcase = id_testcase;
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	public Integer getIdProblem() {
		return idProblem;
	}

	public void setIdProblem(Integer idProblem) {
		this.idProblem = idProblem;
	}
	
}