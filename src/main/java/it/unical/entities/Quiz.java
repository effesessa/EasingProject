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
@Table(name = "quiz")
public class Quiz implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public Quiz() {
		
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
 	@Column(name = "id")
	private Integer id;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "points", nullable = true)
	private Integer points;
	
	@ManyToOne
	@JoinColumn(name = "idcontest", nullable = false)
	private Contest contest;
	
	public Integer getId() {
		return id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public Contest getContest() {
		return contest;
	}

	public void setContest(final Contest contest) {
		this.contest = contest;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(final Integer points) {
		this.points = points;
	}
	
}
