package it.unical.core.strategy;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.springframework.web.multipart.MultipartFile;

import it.unical.core.Engine;
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
	public String generateOutput(AddProblemForm problemDTO, Problem problem) {
		MultipartFile multipartFile = problemDTO.getTestcase();
		File algorithmFile = new File(System.getProperty(Engine.WORKING_DIRECTORY) + multipartFile.getOriginalFilename());
		try {
			algorithmFile.createNewFile();
			multipartFile.transferTo(algorithmFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String response = "";
		if(Engine.compile(algorithmFile.getName()) == Status.COMPILE_ERROR)
			return Status.COMPILE_ERROR;
		response = Engine.run(algorithmFile.getName(), TimeUnit.SECONDS.toMillis(problemDTO.getTimeout()));
		System.out.println(response);
		if(Status.statusList.contains(response))
			return response;
		problem.setSol(response.getBytes());
		algorithmFile.delete();
		return Status.SUCCESS;
	}
	
	@Override
	public String process(Problem problem, File submittedFile, File testCaseFile, String teamName) throws IOException {
		return "";
	}
}
