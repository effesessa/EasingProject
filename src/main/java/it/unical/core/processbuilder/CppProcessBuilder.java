package it.unical.core.processbuilder;

import it.unical.core.Engine;
import it.unical.utils.StringUtils;

/**
 * @author Fabrizio
 */

public class CppProcessBuilder implements IProcessBuilder
{

	@Override
	public boolean compile()
	{
		return true;
	}

	@Override
	public ProcessBuilder getCompileProcessBuilder(String file)
	{
		return new ProcessBuilder("g++", file, "-o", StringUtils.getBaseName(file));
	}

	public ProcessBuilder getRunEXE(String file, String path, String input)
	{
		return new ProcessBuilder(System.getProperty(Engine.WORKING_DIRECTORY) + path
				+ System.getProperty("file.separator") + StringUtils.getBaseName(file) + ".exe", input);
	}

	@Override
	public ProcessBuilder getRunProcessBuilder(String file)
	{
		return new ProcessBuilder(
				System.getProperty(Engine.WORKING_DIRECTORY) + StringUtils.getBaseName(file) + ".exe");
	}

	@Override
	public ProcessBuilder getRunProcessBuilder(String file, String input)
	{
		return new ProcessBuilder(System.getProperty(Engine.WORKING_DIRECTORY) + StringUtils.getBaseName(file) + ".exe",
				input);
	}

}
