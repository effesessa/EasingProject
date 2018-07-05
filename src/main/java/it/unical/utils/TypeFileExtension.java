package it.unical.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Fabrizio
 */

public class TypeFileExtension {
	
	public static final List<String> extensionsLanguage;
	
	public static final List<String> extensionsText;
	
	public static final List<String> extensionsCompress;
	
	public static final String CPP = "cpp";
	
	public static final String C = "c";
	
	public static final String JAVA = "java";
	
	public static final String PYTHON = "py";
	
	public static final String RAR = "rar";
	
	public static final String ZIP = "zip";
	
	public static final String SEVEN_Z = "7z";
	
	public static final String DAT = "dat";
	
	public static final String TXT = "txt";
	
	static {
		List<String> list = new ArrayList<>();
		list.add(CPP);
		list.add(C);
		list.add(JAVA);
		list.add(PYTHON);
		extensionsLanguage = Collections.unmodifiableList(list);
	}
	
	static {
		List<String> list = new ArrayList<>();
		list.add(RAR);
		list.add(ZIP);
		list.add(SEVEN_Z);
		extensionsCompress = Collections.unmodifiableList(list);
	}
	
	static {
		List<String> list = new ArrayList<>();
		list.add(DAT);
		list.add(TXT);
		extensionsText = Collections.unmodifiableList(list);
	}
}
