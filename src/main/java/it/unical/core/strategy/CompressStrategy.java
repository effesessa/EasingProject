package it.unical.core.strategy;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import it.unical.core.Engine;
import it.unical.entities.Problem;
import it.unical.forms.AddProblemForm;
import it.unical.utils.FFileUtils;
import it.unical.utils.Status;
import it.unical.utils.StringUtils;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

/**
 * @author Fabrizio
 */

public class CompressStrategy extends AbstractStrategy {

	@Override
	public void manageInputFile(AddProblemForm problemDTO, Problem problem) throws IOException {
		if(problemDTO.getTestcase().getBytes() != null)
			problem.setTest(problemDTO.getTestcase().getBytes());
		problem.setType(StringUtils.getExtension(problemDTO.getTestcase().getOriginalFilename()));
	}
	
	@Override
	public void manageOutputFile(AddProblemForm problemDTO, Problem problem) throws IOException {
		return;
	}

	@Override
	public String generateOutput(AddProblemForm problemDTO, Problem problem) {
		return Status.SUCCESS;
	}
	
	@Override
	public String process(Problem problem, File submittedFile, File testCaseFile, String teamName) throws IOException {
		try {
			ZipFile archive = new ZipFile(testCaseFile);
			archive.extractAll(System.getProperty(Engine.WORKING_DIRECTORY));
		} 
		catch (ZipException e) {
			e.printStackTrace();
		}
		String nameArchive = StringUtils.getBaseName(testCaseFile.getName());
		File directory = new File(System.getProperty(Engine.WORKING_DIRECTORY) + nameArchive);
		File[] files = directory.listFiles();
		Arrays.sort(files);
		for (File file : files) {
			if(Engine.compile(submittedFile.getName()).equals(Status.COMPILE_ERROR))
				return Status.COMPILE_ERROR;
			long timeLimit = TimeUnit.SECONDS.toMillis((long)(float)problem.getTimelimit());
			String input = FileUtils.readFileToString(file, "UTF-8");
			String response = Engine.run(submittedFile.getName(), timeLimit, input);
			if(Status.statusList.contains(response))
				return response;
			String correctSolution = new String(problem.getSol(), "UTF-8");
			if(!Engine.match(response,correctSolution).equals(Status.WRONG_ANSWER))
				return Status.WRONG_ANSWER;
		}
		FFileUtils.deleteDirectory(directory);
		return Status.SUCCESS;
	}

}
