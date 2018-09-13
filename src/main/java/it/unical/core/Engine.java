package it.unical.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.IOUtils;

import it.unical.core.processbuilder.CProcessBuilder;
import it.unical.core.processbuilder.CppProcessBuilder;
import it.unical.core.processbuilder.IProcessBuilder;
import it.unical.core.processbuilder.ProcessBuilderFactory;
import it.unical.utils.Status;

/**
 * @author Fabrizio
 */

public class Engine
{

	public static final String WORKING_DIRECTORY = "java.io.tmpdir";

	public static final String BASE_NAME_INPUT = "input";

	public static final String BASE_NAME_OUTPUT = "output";

	public static final String DOT = ".";

	public static final String EXECUTION_TIME = "EXECUTION_TIME";

	public static final String NO_EXECUTION_TIME = "NO_EXECUTION_TIME";

	public static final String BASE_NAME_SUBMIT = "submit";

	public static Verdict compile(DirFilesManager manager)
	{
		final Verdict verdict = new Verdict();
		final ProcessBuilderFactory processBuilderFactory = ProcessBuilderFactory.getInstance();
		final IProcessBuilder iProcessBuilder = processBuilderFactory
				.createIProcessBuilder(manager.getSubmittedFile().getName());
		if (!iProcessBuilder.compile())
			return verdict.setStatus(Status.COMPILE_SUCCESS);
		final ProcessBuilder processBuilder = iProcessBuilder
				.getCompileProcessBuilder(manager.getSubmittedFile().getName());
		processBuilder.directory(new File(System.getProperty(WORKING_DIRECTORY) + manager.separatorAndNameRandomDir()));
		processBuilder.redirectErrorStream(true);
		boolean compiled = true;
		try
		{
			final Process process = processBuilder.start();
			final InputStream inputStream = process.getInputStream();
			String line;
			try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream)))
			{
				while ((line = bufferedReader.readLine()) != null)
				{
					compiled = false;
					verdict.setErrorText(verdict.getErrorText() + line + System.getProperty("line.separator"));
					System.out.println(line);
				}
				process.waitFor();
				inputStream.close();
				process.destroy();
				if (!compiled)
					return verdict.setStatus(Status.COMPILE_ERROR);
				return verdict.setStatus(Status.COMPILE_SUCCESS);
			}
		}
		catch (IOException | InterruptedException e)
		{
			e.printStackTrace();
		}
		return verdict.setStatus(Status.COMPILE_ERROR);
	}

	private static void debugMatch(String r, String c)
	{
		System.out.println("*************match*************");
		System.out.println("-------------" + r + "-------------");
		System.out.println("-------------" + c + "-------------");
		System.out.println("*************match*************");
	}

	public static void match(Verdict verdict, String correctSolution)
	{
		debugMatch(verdict.getStatus(), correctSolution);
		if (!verdict.getStatus().equals(correctSolution))
			verdict.setStatus(Status.WRONG_ANSWER);
		else
			verdict.setStatus(Status.CORRECT);
	}

	public static Verdict run(DirFilesManager manager, long timeLimit, String... input)
	{
		final Verdict verdict = new Verdict();
		final ProcessBuilderFactory processBuilderFactory = ProcessBuilderFactory.getInstance();
		final IProcessBuilder iProcessBuilder = processBuilderFactory
				.createIProcessBuilder(manager.getSubmittedFile().getName());
		ProcessBuilder processBuilder;
		if (input.length > 0)
		{
			String maybeItWillFail = null;
			try
			{
				maybeItWillFail = IOUtils.toString(
						new FileInputStream(manager.getRandomDirectory() + manager.separator() + input[0]), "UTF-8");
			}
			catch (final IOException e)
			{
				e.printStackTrace();
			}
			verdict.setTestCaseFailed(maybeItWillFail);
			if (iProcessBuilder instanceof CppProcessBuilder)
				processBuilder = ((CppProcessBuilder) iProcessBuilder).getRunEXE(manager.getSubmittedFile().getName(),
						manager.nameRandomDir(), input[0]);
			else if (iProcessBuilder instanceof CProcessBuilder)
				processBuilder = ((CProcessBuilder) iProcessBuilder).getRunEXE(manager.getSubmittedFile().getName(),
						manager.nameRandomDir(), input[0]);
			else
				processBuilder = iProcessBuilder.getRunProcessBuilder(manager.getSubmittedFile().getName(), input[0]);
		}
		else
			processBuilder = iProcessBuilder.getRunProcessBuilder(manager.getSubmittedFile().getName());
		processBuilder.directory(new File(System.getProperty(WORKING_DIRECTORY) + manager.separatorAndNameRandomDir()));
		processBuilder.redirectErrorStream(false);
		String output = "";
		try
		{
			final long startTime = System.nanoTime();
			final Process process = processBuilder.start();
			if (!process.waitFor(timeLimit, TimeUnit.MILLISECONDS))
				return verdict.setStatus(Status.TIME_LIMIT_EXIT);
			final long endTime = System.nanoTime();
			final int exitCode = process.exitValue();
			// READ ERROR STREAM
			String errorText = "";
			String line = "";
			final BufferedReader bufferOutput = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			while ((line = bufferOutput.readLine()) != null)
				errorText = errorText + line + System.getProperty("line.separator");
			System.out.println(errorText);
			// END READ ERROR STREAM
			if (exitCode != 0)
				return verdict.setStatus(Status.RUN_TIME_ERROR).setErrorText(errorText);
			output = IOUtils.toString(process.getInputStream());
			final Long nanosTime = (endTime - startTime);
			final double secondsTime = (nanosTime.doubleValue() / 1000000000);
			final String executionTime = new DecimalFormat("#.###").format(secondsTime).replace(",", ".");
			verdict.setStatus(output).setExecutionTime(executionTime);
		}
		catch (final Exception e)
		{
			e.printStackTrace();
			return verdict.setStatus(Status.EXECUTION_ERROR);
		}
		return verdict;
	}

}
