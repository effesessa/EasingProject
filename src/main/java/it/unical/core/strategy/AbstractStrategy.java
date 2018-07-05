package it.unical.core.strategy;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import it.unical.core.Engine;
import it.unical.entities.Problem;
import it.unical.forms.AddProblemForm;
import it.unical.forms.SubmitForm;
import it.unical.utils.FFileUtils;
import it.unical.utils.MultipartFileUtils;
import it.unical.utils.StringUtils;

/**
 * @author Fabrizio
 */

public abstract class AbstractStrategy {

	private String status;
	
	//template method prepareToSave
	public Problem prepareToSave(AddProblemForm problemDTO) {
		Problem problem = new Problem();
		try {
			if(problemDTO.getDownload() != null)
				problem.setDownload(problemDTO.getDownload().getBytes());
			problem.setDescription(problemDTO.getDescription());
			problem.setTimelimit((float) TimeUnit.SECONDS.toMillis(problemDTO.getTimeout()));
			problem.setName(problemDTO.getName());
			if(problemDTO.getTestcase() != null)
				problem.setType(StringUtils.getExtension(problemDTO.getTestcase().getOriginalFilename()));
			manageInputFile(problemDTO, problem);
			manageOutputFile(problemDTO, problem);
			status = generateOutput(problemDTO, problem);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		return problem;
	}
	
	/**
	 * if present in DB, testcase file can be txt, dat, zip, 7z, rar
	 * for archives there can be more test cases with relative outputs
	 * otherwise file is null and the submission is without input(testcase)
	 * the output is always present on DB
	 */
	//template method submit
	public String submit(Problem problem, SubmitForm submitDTO) {
		File submittedFile = MultipartFileUtils.convert(submitDTO.getSolution());
		File testCaseFile = null;
		if(problem.getTest() != null) {
			String fileName = Engine.BASE_NAME + 
					submitDTO.getTeam() + 
					Engine.DOT + 
					problem.getType();
			System.out.println(fileName);
			testCaseFile = FFileUtils.createNewFile(fileName);
			System.out.println(testCaseFile.getName());
			FFileUtils.writeByteArrayToFile(testCaseFile, problem.getTest());
		}
		else
			if(!(this instanceof AlgorithmStrategy))
				return "TESTCASEFILE IS NULL";
		try {
			return process(problem, submittedFile, testCaseFile, submitDTO.getTeam());
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return "INTERNAL ERROR";
	}
	
	public abstract String process(Problem problem, File submittedFile, File testCaseFile, String teamName) throws IOException;
	
	public abstract void manageInputFile(AddProblemForm problemDTO, Problem problem) throws IOException;

	public abstract void manageOutputFile(AddProblemForm problemDTO, Problem problem) throws IOException;
	
	public abstract String generateOutput(AddProblemForm problemDTO, Problem problem);
	
	
	public String getStatus() {
		return status;
	}
}
