package it.unical.core.processbuilder;

/**
 * @author Fabrizio
 */

public interface IProcessBuilder {
	
	public ProcessBuilder getCompileProcessBuilder(String file);
	
	public ProcessBuilder getRunProcessBuilder(String file);
	
	public ProcessBuilder getRunProcessBuilder(String file, String input);
}
