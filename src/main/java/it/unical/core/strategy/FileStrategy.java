package it.unical.core.strategy;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import it.unical.core.DirFilesManager;
import it.unical.core.Engine;
import it.unical.core.Verdict;
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
	public Verdict generateOutput(AddProblemForm problemDTO, Problem problem) {
		return new Verdict().setStatus(Status.SUCCESS);
	}
	
	@Override
	public Verdict process(Problem problem, DirFilesManager dirFilesManager) throws IOException {
		Verdict verdict = Engine.compile(dirFilesManager);
		if(verdict.getStatus().equals(Status.COMPILE_ERROR))
			return verdict;
		long timeLimit = TimeUnit.SECONDS.toMillis((long)(float)problem.getTimelimit());
		verdict = Engine.run(dirFilesManager, timeLimit,
				dirFilesManager.getTestCaseFile().getName());
		if(Status.statusList.contains(verdict.getStatus()))
			return verdict;
		String correctSolution = new String(problem.getSol(),"UTF-8");
		correctSolution = StringUtils.checkAndRemoveUTF8BOM(correctSolution);
		Engine.match(verdict,correctSolution);
		return verdict;
	}
}
