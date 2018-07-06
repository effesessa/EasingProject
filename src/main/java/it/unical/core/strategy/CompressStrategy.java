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
		String nameArchive = StringUtils.getBaseName(testCaseFile.getName());
		try {
			ZipFile archive = new ZipFile(testCaseFile);
			archive.extractAll(System.getProperty(Engine.WORKING_DIRECTORY) + nameArchive);
		} 
		catch (ZipException e) {
			e.printStackTrace();
		}
		File directory = new File(System.getProperty(Engine.WORKING_DIRECTORY) + nameArchive);
		File[] files = directory.listFiles();
		Arrays.sort(files);
		for (int i = 0; i < files.length; i+=2) {
			File file = files[i];
			System.out.println(file.getAbsolutePath());
			if(Engine.compile(submittedFile.getName()).equals(Status.COMPILE_ERROR))
				return Status.COMPILE_ERROR;
			long timeLimit = TimeUnit.SECONDS.toMillis((long)(float)problem.getTimelimit());
			String response = Engine.run(submittedFile.getName(), timeLimit, nameArchive + "\\" + file.getName());
			if(Status.statusList.contains(response))
				return response;
			String correctSolution = FileUtils.readFileToString(files[i+1],"UTF-8");
			correctSolution = StringUtils.checkAndRemoveUTF8BOM(correctSolution);
			correctSolution = StringUtils.stripDiacritics(correctSolution);
			if((Engine.match(response,correctSolution)).equals(Status.WRONG_ANSWER))
				return Status.WRONG_ANSWER;
		}
		FFileUtils.deleteDirectory(directory);
		return Status.CORRECT;
	}

}
