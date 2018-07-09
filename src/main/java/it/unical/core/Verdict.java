package it.unical.core;

/**
 * @author Fabrizio
 */

public class Verdict {
	
	private String status;

	private String executionTime;
	
	public Verdict() {

	}

	public Verdict(String status, String executionTime) {
		this.status = status;
		this.executionTime = executionTime;
	}

	public String getStatus() {
		return status;
	}

	public Verdict setStatus(String status) {
		this.status = status;
		return this;
	}

	public String getExecutionTime() {
		return executionTime;
	}

	public Verdict setExecutionTime(String executionTime) {
		this.executionTime = executionTime;
		return this;
	}
	
	
}
