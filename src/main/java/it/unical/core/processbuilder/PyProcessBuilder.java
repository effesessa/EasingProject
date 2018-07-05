package it.unical.core.processbuilder;

/**
 * @author Fabrizio
 */

public class PyProcessBuilder implements IProcessBuilder {

	@Override
	public ProcessBuilder getCompileProcessBuilder(String file) {
		return new ProcessBuilder("");
	}

	@Override
	public ProcessBuilder getRunProcessBuilder(String file) {
		return new ProcessBuilder("");
	}

	@Override
	public ProcessBuilder getRunProcessBuilder(String file, String input) {
		// TODO Auto-generated method stub
		return null;
	}

}
