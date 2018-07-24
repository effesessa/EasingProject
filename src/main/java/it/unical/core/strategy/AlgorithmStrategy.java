package it.unical.core.strategy;

import java.io.IOException;

import it.unical.core.DirFilesManager;
import it.unical.core.Verdict;
import it.unical.entities.Problem;
import it.unical.forms.AddProblemForm;
import it.unical.utils.Status;

/**
 * @author Fabrizio
 */

public class AlgorithmStrategy extends AbstractStrategy {

	@Override
	public void manageInputFile(AddProblemForm problemDTO, Problem problem) throws IOException {
		return;
	}
	
	@Override
	public void manageOutputFile(AddProblemForm problemDTO, Problem problem) throws IOException {
		return;
	}

	@Override
	public Verdict generateOutput(AddProblemForm problemDTO, Problem problem) {
		/*MultipartFile multipartFile = problemDTO.getTestcase();
		File algorithmFile = new File(System.getProperty(Engine.WORKING_DIRECTORY) + multipartFile.getOriginalFilename());
		try {
			algorithmFile.createNewFile();
			multipartFile.transferTo(algorithmFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Verdict verdict = new Verdict();
		if(Engine.compile(algorithmFile.getName()).getStatus().equals(Status.COMPILE_ERROR))
			return verdict.setStatus(Status.COMPILE_ERROR);
		verdict = Engine.run(algorithmFile.getName(), TimeUnit.SECONDS.toMillis(problemDTO.getTimeout()));
		System.out.println(verdict.getStatus());
		if(Status.statusList.contains(verdict.getStatus()))
			return verdict;
		problem.setSol(verdict.getStatus().getBytes());
		algorithmFile.delete();
		*/
		return new Verdict().setStatus(Status.SUCCESS);
	}
	
	@Override
	public Verdict process(Problem problem, DirFilesManager dirFilesManager) throws IOException {
		return null;
	}
}
