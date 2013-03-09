package com.tandon.testbench.nomads.combat;

import java.util.HashMap;

public class PropertyContainer {
	public PropertyContainer parent = null;
	
	private HashMap<String, Object> dictionary = new HashMap<String, Object>();
	
	public Object getProperty(String propertyName) {
		Object value = dictionary.get(propertyName);
		
		if (value == null && parent != null) {
			return parent.getProperty(propertyName);
		}
		
		return value;
	}

	public void setProperty(String propertyName, Object propertyValue) {
		dictionary.put(propertyName, propertyValue);
	}
	
	public String toString() {
		return "      " + dictionary.toString();
	}
}
