package com.datastax.restserver.utils;

public class PropertyHelper {
	
	public static String getProperty(String name, String defaultValue){		
		return System.getProperty(name) == null ? defaultValue : System.getProperty(name); 
	}	
}
