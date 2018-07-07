package it.unical.core.strategy;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import it.unical.core.Engine;
import it.unical.entities.Problem;
import it.unical.forms.AddProblemForm;
import it.unical.utils.Status;
import it.unical.utils.StringUtils;

/**
 * @author Fabrizio
 */

public class FileStrategy extends AbstractStrategy {

	@Override
	public void manageInputFile(AddProblemForm problemDTO, Problem problem) throws IOException {
		if(problemDTO.getTestcase().getBytes() != null)
			problem.setTest(problemDTO.getTestcase().getBytes());
		problem.setType(StringUtils.getExtension(problemDTO.getTestcase().getOriginalFilename()));
	}
	
	@Override
	public void manageOutputFile(AddProblemForm problemDTO, Problem problem) throws IOException {
		if(problemDTO.getOutput().getBytes() != null)
			problem.setSol(problemDTO.getOutput().getBytes());
	}

	@Override
	public String generateOutput(AddProblemForm problemDTO, Problem problem) {
		return Status.SUCCESS;
	}
	
	@Override
	public String process(Problem problem, File submittedFile, File testCaseFile, String teamName) throws IOException {
		System.out.println(testCaseFile.getName());
		if(Engine.compile(submittedFile.getName()).equals(Status.COMPILE_ERROR))
			return Status.COMPILE_ERROR;
		long timeLimit = TimeUnit.SECONDS.toMillis((long)(float)problem.getTimelimit());
		String response = Engine.run(submittedFile.getName(), timeLimit, testCaseFile.getName());
		if(Status.statusList.contains(response))
			return response;
		String correctSolution = new String(problem.getSol(),"UTF-8");
		System.out.println("BOM: " + correctSolution);
		correctSolution = StringUtils.checkAndRemoveUTF8BOM(correctSolution);
		return Engine.match(response,correctSolution);
	}
}
