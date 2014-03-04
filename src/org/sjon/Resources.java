package org.sjon;

import java.util.HashMap;
import java.util.Map;

public class Resources {
	
	public static final String resourcesDataRoot = "./resources/data";
	
	public static final String SCORES = "scores";
	public static final String RANKING = "ranking";
	
	private static final Map<String, String> dataResources = new HashMap<String, String>();
	
	static {
		dataResources.put(SCORES, "reference scores.sjon");
		dataResources.put(RANKING, "uefa ranking 2013_14 mixed.sjon");
	}
	
	public static String getDataResource(String resourceName) {
		return resourcesDataRoot + "/" + dataResources.get(resourceName); 
	}
}
