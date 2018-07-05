package it.unical.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Fabrizio
 */

public class Status {
	
	public static final List<String> statusList;
	
	public static final String DEFAULT = "";
	
	public static final String COMPILE_SUCCESS = "COMPILE_SUCCESS";
	
	public static final String COMPILE_ERROR = "COMPILE_ERROR";
	
	public static final String TIME_LIMIT_EXIT = "TIME_LIMIT_EXIT";
	
	public static final String RUN_TIME_ERROR = "RUN_TIME_ERROR";
	
	public static final String EXECUTION_SUCCESS = "EXECUTION_SUCCESS";
	
	public static final String EXECUTION_ERROR = "EXECUTION_ERROR";
	
	public static final String CORRECT = "CORRECT";
	
	public static final String WRONG_ANSWER = "WRONG_ANSWER";
	
	public static final String SUCCESS = "SUCCESS";
	
	static {
		List<String> list = new ArrayList<>();
		list.add(DEFAULT);
		list.add(COMPILE_SUCCESS);
		list.add(COMPILE_ERROR);
		list.add(TIME_LIMIT_EXIT);
		list.add(RUN_TIME_ERROR);
		list.add(EXECUTION_ERROR);
		list.add(EXECUTION_SUCCESS);
		list.add(CORRECT);
		list.add(WRONG_ANSWER);
		statusList = Collections.unmodifiableList(list);
	}
}