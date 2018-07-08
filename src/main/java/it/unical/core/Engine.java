package it.unical.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.IOUtils;

import it.unical.core.processbuilder.IProcessBuilder;
import it.unical.core.processbuilder.ProcessBuilderFactory;
import it.unical.utils.Status;

/**
 * @author Fabrizio
 */

public class Engine {
	
	public static final String WORKING_DIRECTORY = "java.io.tmpdir";
	
	public static final String BASE_NAME =  "input";
	
	public static final String DOT = ".";
	
	public Engine() {
		
	}
	
	public static String compile(String file) {
		ProcessBuilderFactory processBuilderFactory = ProcessBuilderFactory.getInstance();
		IProcessBuilder iProcessBuilder = processBuilderFactory.createIProcessBuilder(file);
		if(!iProcessBuilder.compile())
			return Status.COMPILE_SUCCESS;
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
                    return Status.COMPILE_ERROR;
                return Status.COMPILE_SUCCESS;
            }
        }
        catch(IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return Status.COMPILE_ERROR;
	}
	
	public static String run(String file, long timeLimit, String...input) {
		System.out.println("** " + file);
		System.out.println("** " + input[0]);
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
                return Status.TIME_LIMIT_EXIT;
            long endTime = System.nanoTime();
            int exitCode = process.exitValue();
            for (int i = 0; i < process.getErrorStream().available(); i++)
				System.out.println(process.getErrorStream().read());
            if (exitCode != 0)
                return Status.RUN_TIME_ERROR;
            output = IOUtils.toString(process.getInputStream());
            long duration = (endTime - startTime) / 1000000;
            System.out.println(duration);
        } 
        catch(Exception e) {
        	e.printStackTrace();
            return Status.EXECUTION_ERROR;
        }
        return output;
	}

	public static String match(String response, String correctSolution) {
		debugMatch(response, correctSolution);
		if(!response.equals(correctSolution)) {
			return Status.WRONG_ANSWER;
		}
		return Status.CORRECT;
	}
	
	private static void debugMatch(String r, String c) {
		System.out.println("*************match*************");
		System.out.println("-------------" + r + "-------------");
		System.out.println("-------------" + c + "-------------");
		System.out.println("*************match*************");
	}
	
}
