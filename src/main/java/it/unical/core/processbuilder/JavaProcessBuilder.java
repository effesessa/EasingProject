package it.unical.core.processbuilder;

import it.unical.utils.StringUtils;

/**
 * @author Fabrizio
 */

public class JavaProcessBuilder implements IProcessBuilder {

	@Override
	public ProcessBuilder getCompileProcessBuilder(String file) {
		return new ProcessBuilder("javac", "-cp", ".", file);
	}

	@Override
	public ProcessBuilder getRunProcessBuilder(String file) {
		return new ProcessBuilder("java", "-cp", ".", StringUtils.getBaseName(file));
	}

	@Override
	public ProcessBuilder getRunProcessBuilder(String file, String input) {
		return new ProcessBuilder("java", "-cp", ".", StringUtils.getBaseName(file), input);
	}
}
