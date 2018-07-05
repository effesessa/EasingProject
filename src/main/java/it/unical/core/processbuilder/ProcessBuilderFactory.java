package it.unical.core.processbuilder;

import it.unical.utils.StringUtils;

/**
 * @author Fabrizio
 */

public class ProcessBuilderFactory {
	
	private static ProcessBuilderFactory processBuilderFactory;
	
	private static String corePackage;
	
	private ProcessBuilderFactory() {
		corePackage = this.getClass().getPackage().getName() + ".";
	}
	
	public static ProcessBuilderFactory getInstance() {
		if(processBuilderFactory == null)
			processBuilderFactory = new ProcessBuilderFactory();
		return processBuilderFactory;
	}
	
	public IProcessBuilder createIProcessBuilder(String file) {
		String extension = StringUtils.getExtension(file);
		extension = StringUtils.capitalize(extension);
		System.out.println(corePackage + extension + "ProcessBuilder");
		try {
			Class<?> classIProcessBuilder = Class.forName(corePackage + extension + "ProcessBuilder");
			return (IProcessBuilder) classIProcessBuilder.newInstance();
		} 
		catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			return (IProcessBuilder) new NoOneProcessBuilder();
		}
	}
}
