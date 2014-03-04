package org.sjon.core;

import java.util.logging.Logger;

/**
 * 
 * An SjonRecord object corresponds to a single line of data in the SJON file, read from an {@link org.sjon.core.SjonFileReader} object.
 * It is represented as an array of String objects, for which a getter is provided, but normally you would get the value of a specific column by using an appropriate getter
 * corresponding to the type of the value, having the index of the column as a parameter. As a user of this class, you must know the 'schema' of the record: the order of 
 * the columns and their corresponding types.
 * 
 * @author Andreas Tasoulas
 *
 */
public class SjonRecord {

	private String [] data;
	
	private Logger logger = Logger.getLogger(this.getClass().getName());
	
	/**
	 * 
	 * Constructor converting the string representation of an SJON record to an SjonRecord object
	 * 
	 * @param rawRecord the string representation of an SJON record having the format '{value1, value2, ..., valueN}'
	 */
	public SjonRecord(String rawRecord) {
		
		// logger.config("Converting rawRecord " + rawRecord + " into data");
		
		this.data = rawRecord.substring(rawRecord.indexOf("{") + 1, rawRecord.indexOf("}")).split(",");
		
		// logger.config("Data number of attributes: " + this.data.length);
	}
	
	/**
	 * Getter for an integer value in the SJON record
	 * 
	 * @param index the index of the value in the record, corresponding to the ordinal of its column 
	 * @return a value of a column of the SJON record as an integer
	 */
	public int getInt(int index) {
		return Integer.parseInt(this.data[index].trim());
	}
	
	/**
	 * Getter for a double value in the SJON record
	 * 
	 * @param index the index of the value in the record, corresponding to the ordinal of its column 
	 * @return a value of a column of the SJON record as a double
	 */
	public double getDouble(int index) {
		return Double.parseDouble(this.data[index].trim());
	}
	
	/**
	 * Getter for a string value in the SJON record
	 * 
	 * @param index the index of the value in the record, corresponding to the ordinal of its column 
	 * @return a value of a column of the SJON record as a trimmed String (in effect performing no type conversion)
	 */
	public String getString(int index) {
		return this.data[index].trim();
	}
	
	/**
	 * 
	 * Getter for all data of an SJON record
	 * 
	 * @return an array of String objects representing the data of the SJON record per column
	 */
	public String [] getData() {
		logger.config("Number of attributes in data: " + this.data.length);
		return this.data;
	}
}
