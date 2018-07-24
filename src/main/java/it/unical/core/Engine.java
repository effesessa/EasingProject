package it.unical.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.IOUtils;

import it.unical.core.processbuilder.IProcessBuilder;
import it.unical.core.processbuilder.ProcessBuilderFactory;
import it.unical.utils.Status;

/**
 * @author Fabrizio
 */

public class Engine {
	
	public static Verdict compile(DirFilesManager manager) {
		Verdict verdict = new Verdict();
		ProcessBuilderFactory processBuilderFactory = ProcessBuilderFactory.getInstance();
		IProcessBuilder iProcessBuilder = processBuilderFactory.createIProcessBuilder(manager.getSubmittedFile().getName());
		if(!iProcessBuilder.compile())
			return verdict.setStatus(Status.COMPILE_SUCCESS);
		ProcessBuilder processBuilder = iProcessBuilder.getCompileProcessBuilder(manager.getSubmittedFile().getName());
		processBuilder.directory(new File(System.getProperty(WORKING_DIRECTORY) + manager.separatorAndNameRandomDir()));
		processBuilder.redirectErrorStream(true);
		boolean compiled = true;
        try {
            Process process = processBuilder.start();
            InputStream inputStream = process.getInputStream();
            String line;
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
                while((line = bufferedReader.readLine()) != null) {
                    compiled = false;
                    verdict.setErrorText(verdict.getErrorText() + line + System.getProperty("line.separator"));
                    System.out.println(line);
                }
                process.waitFor();
                inputStream.close();
                process.destroy();
                if(!compiled)
                    return verdict.setStatus(Status.COMPILE_ERROR);
                return verdict.setStatus(Status.COMPILE_SUCCESS);
            }
        }
        catch(IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return verdict.setStatus(Status.COMPILE_ERROR);
	}
	
	public static Verdict run(DirFilesManager manager, long timeLimit, String...input) {
		Verdict verdict = new Verdict();
		ProcessBuilderFactory processBuilderFactory = ProcessBuilderFactory.getInstance();
		IProcessBuilder iProcessBuilder = processBuilderFactory.createIProcessBuilder(manager.getSubmittedFile().getName());
		ProcessBuilder processBuilder;
		if(input.length > 0) {
			verdict.setTestCaseFailed(input[0]);
			processBuilder = iProcessBuilder.getRunProcessBuilder(manager.getSubmittedFile().getName(), input[0]);
		}
		else
			processBuilder = iProcessBuilder.getRunProcessBuilder(manager.getSubmittedFile().getName());
		processBuilder.directory(new File(System.getProperty(WORKING_DIRECTORY) + manager.separatorAndNameRandomDir()));
        processBuilder.redirectErrorStream(false);
		String output = "";
        try {
        	long startTime = System.nanoTime();
            Process process = processBuilder.start();
            if (!process.waitFor(timeLimit, TimeUnit.MILLISECONDS)) 
                return verdict.setStatus(Status.TIME_LIMIT_EXIT);
            long endTime = System.nanoTime();
            int exitCode = process.exitValue();
            //READ ERROR STREAM
            String errorText = "";
            String line = "";
            BufferedReader bufferOutput = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            while ((line = bufferOutput.readLine()) != null) {
            	errorText = errorText + line + System.getProperty("line.separator");
            }
            System.out.println(errorText);
            //END READ ERROR STREAM
            if (exitCode != 0)
            	return verdict.setStatus(Status.RUN_TIME_ERROR).setErrorText(errorText);
            output = IOUtils.toString(process.getInputStream());
            Long nanosTime = (endTime - startTime);
    		double secondsTime = (nanosTime.doubleValue() / 1000000000);
    		String executionTime = new DecimalFormat("#.###").format(secondsTime).replace(",",".");
    		verdict.setStatus(output).setExecutionTime(executionTime);
        } 
        catch(Exception e) {
        	e.printStackTrace();
            return verdict.setStatus(Status.EXECUTION_ERROR);
        }
        return verdict;
	}

	public static void match(Verdict verdict, String correctSolution) {
		debugMatch(verdict.getStatus(), correctSolution);
		if(!verdict.getStatus().equals(correctSolution))
			verdict.setStatus(Status.WRONG_ANSWER);
		else
			verdict.setStatus(Status.CORRECT);
	}
	
	private static void debugMatch(String r, String c) {
		System.out.println("*************match*************");
		System.out.println("-------------" + r + "-------------");
		System.out.println("-------------" + c + "-------------");
		System.out.println("*************match*************");
	}
	
	public static final String WORKING_DIRECTORY = "java.io.tmpdir";
	
	public static final String BASE_NAME_INPUT =  "input";
	
	public static final String BASE_NAME_OUTPUT =  "output";
	
	public static final String DOT = ".";
	
	public static final String EXECUTION_TIME = "EXECUTION_TIME";
	
	public static final String NO_EXECUTION_TIME = "NO_EXECUTION_TIME";
	
	public static final String BASE_NAME_SUBMIT = "submit";
	
}
