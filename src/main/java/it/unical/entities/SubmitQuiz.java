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
@Table(name = "submit_quiz")
public class SubmitQuiz implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
 	@Column(name = "id")
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "idteam")
	private Team team;
	
	@ManyToOne
	@JoinColumn(name = "idquiz")
	private Quiz quiz;
	
	@Column(name = "total_score", nullable = true, columnDefinition = "TINYINT")
	private Integer totalScore;
	
	@Column(name = "open_score", nullable = true, columnDefinition = "TINYINT")
	private Integer openScore;
	
	@Column(name = "multiple_score", nullable = true, columnDefinition = "TINYINT")
	private Integer multipleScore;
	
	public SubmitQuiz() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public Quiz getQuiz() {
		return quiz;
	}

	public void setQuiz(Quiz quiz) {
		this.quiz = quiz;
	}
	
}
