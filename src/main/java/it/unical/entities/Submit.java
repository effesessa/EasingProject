package it.unical.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import it.unical.dao.DatabaseHandler;

@Entity
@Table(name = "submit")
public class Submit
{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
 	@Column(name = "id")
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "id_team")
	private Team team;
	
	@ManyToOne
	@JoinColumn(name = "id_problem")
	private Problem problem;
	
	@Column(name = "score")
	private String score;
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	@Column(name = "info")
	private String info;
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "date")
	private String date;
	
	@Column(name = "solution")
	private byte[] solution;
	
	@Column(name = "error", nullable = true, columnDefinition = "TEXT")
	private String error;
	
	@Column(name = "test_case_failed", nullable = true, columnDefinition = "MEDIUMTEXT")
	private String testCaseFailed;
	
	@Column(name = "show_tcf", nullable = false, columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean showTcf = false;
	
	public Submit()
	{
		this.id = DatabaseHandler.NO_ID;
		this.problem = null;
		this.team = null;
		this.score = null;
		this.info = null;
		this.solution = null;
		this.testCaseFailed = null;
		this.error = null;
		this.showTcf = false;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Team getTeam() {
		return team;
	}

	public void setIdTeam(Team team) {
		this.team = team;
	}

	public Problem getProblem() {
		return problem;
	}

	public void setProblem(Problem problem) {
		this.problem = problem;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public byte[] getSolution() {
		return solution;
	}

	public void setSolution(byte[] solution) {
		this.solution = solution;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getTestCaseFailed() {
		return testCaseFailed;
	}

	public void setTestCaseFailed(String testCaseFailed) {
		this.testCaseFailed = testCaseFailed;
	}

	public boolean isShowTcf() {
		return showTcf;
	}

	public void setShowTcf(boolean showTcf) {
		this.showTcf = showTcf;
	}
	
}