package it.unical.core.strategy;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import it.unical.core.Engine;
import it.unical.core.Verdict;
import it.unical.entities.Problem;
import it.unical.forms.AddProblemForm;
import it.unical.forms.SubmitForm;
import it.unical.utils.FFileUtils;
import it.unical.utils.MultipartFileUtils;
import it.unical.utils.Status;
import it.unical.utils.StringUtils;

/**
 * @author Fabrizio
 */

public abstract class AbstractStrategy
{

	private Verdict verdict;

	public abstract Verdict generateOutput(AddProblemForm problemDTO, Problem problem);

	public Verdict getVerdict()
	{
		return verdict;
	}

	public abstract void manageInputFile(AddProblemForm problemDTO, Problem problem) throws IOException;

	public abstract void manageOutputFile(AddProblemForm problemDTO, Problem problem) throws IOException;

	// template method prepareToSave
	public Problem prepareToSave(AddProblemForm problemDTO)
	{
		final Problem problem = new Problem();
		try
		{
			if (problemDTO.getDownload() != null)
				problem.setDownload(problemDTO.getDownload().getBytes());
			problem.setDescription(problemDTO.getDescription());
			problem.setTimelimit((float) TimeUnit.SECONDS.toMillis(problemDTO.getTimeout()));
			problem.setName(problemDTO.getName());
			problem.setShow_testcase(problemDTO.isShow_testcase());
			if (problemDTO.getTestcase() != null)
				problem.setType(StringUtils.getExtension(problemDTO.getTestcase().getOriginalFilename()));
			manageInputFile(problemDTO, problem);
			manageOutputFile(problemDTO, problem);
			verdict = generateOutput(problemDTO, problem);
		}
		catch (final IOException e)
		{
			e.printStackTrace();
		}
		return problem;
	}

	public abstract Verdict process(Problem problem, File submittedFile, File testCaseFile, String teamName)
			throws IOException;

	/**
	 * if present in DB, testcase file can be txt, dat, zip for archives there
	 * can be more test cases with relative outputs otherwise file is null and
	 * the submission is without input(testcase) the output is always present on
	 * DB
	 */
	// template method submit
	public Verdict submit(Problem problem, SubmitForm submitDTO)
	{
		final File submittedFile = MultipartFileUtils.convert(submitDTO.getSolution());
		File testCaseFile = null;
		if (problem.getTest() != null)
		{
			final String fileName = Engine.BASE_NAME_INPUT + submitDTO.getTeam() + Engine.DOT + problem.getType();
			System.out.println(fileName);
			testCaseFile = FFileUtils.createNewFile(fileName);
			System.out.println(testCaseFile.getName());
			FFileUtils.writeByteArrayToFile(testCaseFile, problem.getTest());
		}
		try
		{
			return process(problem, submittedFile, testCaseFile, submitDTO.getTeam());
		}
		catch (final IOException e)
		{
			e.printStackTrace();
		}
		return new Verdict().setStatus(Status.UNKNOWN_ERROR);
	}
}
