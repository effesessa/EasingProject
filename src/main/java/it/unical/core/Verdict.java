package it.unical.core;

/**
 * @author Fabrizio
 */

public class Verdict {
	
	private String status;

	private String executionTime;
	
	private String testCaseFailed;
	
	private String errorText;
	
	public Verdict() {
		errorText = "";
		testCaseFailed = "";
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

	public String getTestCaseFailed() {
		return testCaseFailed;
	}

	public Verdict setTestCaseFailed(String testCaseFailed) {
		this.testCaseFailed = testCaseFailed;
		return this;
	}

	public String getErrorText() {
		return errorText;
	}

	public Verdict setErrorText(String errorText) {
		this.errorText = errorText;
		return this;
	}
	
}
