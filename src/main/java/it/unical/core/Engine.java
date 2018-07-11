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
	
	public static Verdict compile(String file) {
		Verdict verdict = new Verdict();
		ProcessBuilderFactory processBuilderFactory = ProcessBuilderFactory.getInstance();
		IProcessBuilder iProcessBuilder = processBuilderFactory.createIProcessBuilder(file);
		if(!iProcessBuilder.compile())
			return verdict.setStatus(Status.COMPILE_SUCCESS);
		ProcessBuilder processBuilder = iProcessBuilder.getCompileProcessBuilder(file);
		processBuilder.directory(new File(System.getProperty(WORKING_DIRECTORY)));
		processBuilder.redirectErrorStream(true);
		boolean compiled = true;
        try {
            Process process = processBuilder.start();
            InputStream inputStream = process.getInputStream();
            String line;
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
                while((line = bufferedReader.readLine()) != null) {
                    compiled = false;
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
	
	public static Verdict run(String file, long timeLimit, String...input) {
		Verdict verdict = new Verdict();
		ProcessBuilderFactory processBuilderFactory = ProcessBuilderFactory.getInstance();
		IProcessBuilder iProcessBuilder = processBuilderFactory.createIProcessBuilder(file);
		ProcessBuilder processBuilder;
		if(input.length > 0)
			processBuilder = iProcessBuilder.getRunProcessBuilder(file,input[0]);
		else
			processBuilder = iProcessBuilder.getRunProcessBuilder(file);
		processBuilder.directory(new File(System.getProperty(WORKING_DIRECTORY)));
        processBuilder.redirectErrorStream(true);
		String output = "";
        try {
        	long startTime = System.nanoTime();
            Process process = processBuilder.start();
            if (!process.waitFor(timeLimit*2, TimeUnit.MILLISECONDS))
                return verdict.setStatus(Status.TIME_LIMIT_EXIT);
            long endTime = System.nanoTime();
            int exitCode = process.exitValue();
            for (int i = 0; i < process.getErrorStream().available(); i++)
				System.out.println(process.getErrorStream().read());
            if (exitCode != 0)
            	return verdict.setStatus(Status.RUN_TIME_ERROR);
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
