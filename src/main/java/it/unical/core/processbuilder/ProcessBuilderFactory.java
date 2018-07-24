package it.unical.core.processbuilder;

import java.util.Map;
import java.util.TreeMap;

import it.unical.utils.StringUtils;

/**
 * @author Fabrizio
 */

public class ProcessBuilderFactory {
	
	private static ProcessBuilderFactory processBuilderFactory;
	
	private static String corePackage;
	
	private Map<String, IProcessBuilder> processBuilderCache;
	
	private ProcessBuilderFactory() {
		corePackage = this.getClass().getPackage().getName() + ".";
		processBuilderCache = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
	}
	
	public static ProcessBuilderFactory getInstance() {
		if(processBuilderFactory == null)
			processBuilderFactory = new ProcessBuilderFactory();
		return processBuilderFactory;
	}
	
	public IProcessBuilder createIProcessBuilder(String file) {
		String extension = StringUtils.getExtension(file);
		synchronized (processBuilderCache) {
			if(processBuilderCache.containsKey(extension) && processBuilderCache.get(extension) != null)
				return processBuilderCache.get(extension);
		}
		extension = StringUtils.capitalize(extension);
		System.out.println(corePackage + extension + "ProcessBuilder");
		try {
			Class<?> classIProcessBuilder = Class.forName(corePackage + extension + "ProcessBuilder");
			IProcessBuilder iProcessBuilder = (IProcessBuilder) classIProcessBuilder.newInstance();
			synchronized (processBuilderCache) {
				processBuilderCache.putIfAbsent(extension, iProcessBuilder);
			}
			return iProcessBuilder;
		} 
		catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			return new NoOneProcessBuilder();
		}
	}
}
