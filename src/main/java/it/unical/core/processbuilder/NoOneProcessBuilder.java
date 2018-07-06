package it.unical.core.processbuilder;

/**
 * @author Fabrizio
 */

/**
 * useless
 * impossible to instantiate this class
 */
public class NoOneProcessBuilder implements IProcessBuilder {

	@Override
	public ProcessBuilder getCompileProcessBuilder(String file) {
		return null;
	}

	@Override
	public ProcessBuilder getRunProcessBuilder(String file) {
		return null;
	}

	@Override
	public ProcessBuilder getRunProcessBuilder(String file, String input) {
		return null;
	}

	@Override
	public boolean compile() {
		return false;
	}

}
