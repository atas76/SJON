package org.sjon.core;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * 
 * Specifies the schema to describe an SJON file. The word 'schema' is closer to its XML meaning than the relational one. Essentially, an SJON file corresponds 
 * to a database 'table', with an ordered number of columns. This means, that the schema in our case is the number of columns and an ordered set of types. 
 * 
 * @author Andreas Tasoulas
 *
 */

public class Schema {
	
	private Logger logger = Logger.getLogger(this.getClass().getName());
	
	private List<String> columnTypes = new ArrayList<String>();
	
	/**
	 * 
	 * Initializes the schema with the types supported.
	 * 
	 * @param columnTypes the types supported. Typically, a subset of the class wrappers of Java primitive types (i.e. String, Integer, Double as time of writing this)
	 * Stay tuned for a bit more detailed documentation.
	 */
	public Schema(String [] columnTypes) {
		for (String columnType:columnTypes) {
			this.columnTypes.add(columnType);
		}
	}
	
	/**
	 * 
	 * Performs a series of checks on the 'raw' string record, to verify its conformance to the specified schema.
	 * 
	 * The data boundaries are the curly brace characters. Each record (logical line) is expected to start with an opening curly-brace character and the 
	 * end-record boundary is a closing curly brace. The record itself is a comma-list of values, with each value corresponding to a schema column.
	 * The number of values must match the number of columns specified in the schema. Its type is also checked for matching the specified schema type.  
	 * 
	 * @param rawData The logical line which is a string representation of an SJON record 
	 * @return <code>true</code>, if the record conforms to the schema according to the criteria documented, <code>false</code> otherwise
	 */
	public boolean conforms(String rawData) {
	
		if (!rawData.startsWith("{") || rawData.indexOf("}") == -1) {
			logger.warning("Syntax error: " + rawData);
			return false;
		}
		
		String [] dataArray = rawData.substring(1, rawData.indexOf("}")).split(",");
		
		if (dataArray.length != columnTypes.size()) {
			logger.info("Invalid number of column data according to schema for record: " + rawData);
			return false;
		}
		
		for (int i = 0; i < dataArray.length; i++) {
			if (!isDataOfExpectedType(dataArray[i], columnTypes.get(i))) {
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Checks whether a particular value is formatted according to the specified by the schema type.
	 * 
	 * @param data the value to be checked
	 * @param type the name of one of the supported types
	 * @return <code>true</code> if the value can be converted from its String representation to the specified type, <code>false</code> otherwise
	 */
	public boolean isDataOfExpectedType(String data, String type) {
		switch(type) {
		case "Integer":
			return data.trim().matches("^[0-9]+$");
		case "String":
			return data.trim().matches("^[A-Za-z\\s]+$");
		case "Double":
			try {
				Double.parseDouble(data);
			} catch (NumberFormatException nfex) {
				return false;
			}
			return true;
		default:
			logger.warning("Unknown type: " + type);
			return true;
		}
	}
}
