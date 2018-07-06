package it.unical.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Fabrizio
 */

@Entity 
@Table(name = "tag")
public class Tag {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name = "id")
	private Integer id;
	
	@ManyToOne 
	@JoinColumn(name = "problem")
	private Problem problem;
	
	@Column(name = "value")
	private String value;

	public Tag() {
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Problem getProblem() {
		return problem;
	}

	public void setProblem(Problem problem) {
		this.problem = problem;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
