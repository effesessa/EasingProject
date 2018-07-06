package it.unical.core.processbuilder;

/**
 * @author Fabrizio
 */

public interface IProcessBuilder {
	
	boolean compile();
	
	ProcessBuilder getCompileProcessBuilder(String file);
	
	ProcessBuilder getRunProcessBuilder(String file);
	
	ProcessBuilder getRunProcessBuilder(String file, String input);
}
