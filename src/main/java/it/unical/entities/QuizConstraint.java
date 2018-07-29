package it.unical.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "quiz_constraint")
public class QuizConstraint implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "idcontest")
	private Contest contest;
	
	@ManyToOne
	@JoinColumn(name = "idproblem")
	private Problem problem;
	
	@Column(name = "min_points", nullable = false)
	private Integer minPoints = 0;
	
	@Column(name = "min_corrects", nullable = false)
	private Integer minCorrects = 0;
	
	public QuizConstraint() {
		problem = null;
		contest = null;
		minCorrects = 0;
		minPoints = 0;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Contest getContest() {
		return contest;
	}

	public void setContest(Contest contest) {
		this.contest = contest;
	}

	public Problem getProblem() {
		return problem;
	}

	public void setProblem(Problem problem) {
		this.problem = problem;
	}

	public Integer getMinPoints() {
		return minPoints;
	}

	public void setMinPoints(Integer minPoints) {
		this.minPoints = minPoints;
	}

	public Integer getMinCorrects() {
		return minCorrects;
	}

	public void setMinCorrects(Integer minCorrects) {
		this.minCorrects = minCorrects;
	}
	
}