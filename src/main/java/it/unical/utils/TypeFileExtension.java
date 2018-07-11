package it.unical.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Fabrizio
 */

public class TypeFileExtension {
	
	public static final List<String> extensionsLanguage;
	
	public static final List<String> extensionsText;
	
	public static final List<String> extensionsCompress;
	
	public static final Map<String, String> highlight;
	
	public static final String CPP = "cpp";
	
	public static final String C = "c";
	
	public static final String JAVA = "java";
	
	public static final String PYTHON = "py";
	
	public static final String RAR = "rar";
	
	public static final String ZIP = "zip";
	
	public static final String SEVEN_Z = "7z";
	
	public static final String DAT = "dat";
	
	public static final String TXT = "txt";
	
	public static final String DLV = "dlv";
	
	public static final String PROLOG = "prolog";
	
	public static final String PYTHON_NAME = "python";
	
	static {
		Map<String, String> map = new HashMap<>();
		map.put(DLV,PROLOG);
		map.put(CPP,CPP);
		map.put(PYTHON, PYTHON_NAME);
		map.put(C,C);
		map.put(JAVA,JAVA);
		highlight = Collections.unmodifiableMap(map);
	}
	
	static {
		List<String> list = new ArrayList<>();
		list.add(CPP);
		list.add(C);
		list.add(JAVA);
		list.add(PYTHON);
		list.add(DLV);
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
		list.add(DLV);
		list.add(TXT);
		extensionsText = Collections.unmodifiableList(list);
	}
	
	public static String getMimeType(String extension) {
		switch (extension) {
			case TXT:
				return "text/plain";
			case ZIP:
				return "application/zip";
			case CPP: 
				return "text/x-c";
			case JAVA:
				return "text/x-java-source";
			case PYTHON:
				return "text/x-script.phyton";
			case C:
				return "text/x-c";
			default:
				return "application/octet-stream";
		}
	}
}
