package it.unical.core.strategy;

import it.unical.entities.Problem;
import it.unical.forms.AddProblemForm;
import it.unical.forms.SubmitForm;
import it.unical.utils.StringUtils;
import it.unical.utils.TypeFileExtension;

/**
 * @author Fabrizio
 */

public class TypeContext {
	
	private AbstractStrategy abstractStrategy;
	
	private FileStrategy fileStrategy;
	
	private CompressStrategy compressStrategy;
	
	private AlgorithmStrategy algorithmStrategy;
	
	private static TypeContext typeContext;
	
	private String status;
	
	private TypeContext() {
		fileStrategy = new FileStrategy();
		compressStrategy = new CompressStrategy();
		algorithmStrategy = new AlgorithmStrategy();
	}
	
	public static TypeContext getInstance() {
		if(typeContext == null)
			typeContext = new TypeContext();
		return typeContext;
	} 
	
	public void setStrategy(String file) {
		String extension = StringUtils.getExtension(file);
		if(TypeFileExtension.extensionsText.contains(extension))
			abstractStrategy = fileStrategy;
		else if(TypeFileExtension.extensionsCompress.contains(extension))
			abstractStrategy = compressStrategy;
		else
			abstractStrategy = algorithmStrategy;
	}
	
	public Problem prepareToSave(AddProblemForm problemDTO) {
		Problem problem = abstractStrategy.prepareToSave(problemDTO);
		status = abstractStrategy.getStatus();
		return problem;
	}
	
	public String submit(Problem problem, SubmitForm submitDTO) {
		return abstractStrategy.submit(problem, submitDTO);
	}
	
	public String getStatus() {
		return status;
	}
}
