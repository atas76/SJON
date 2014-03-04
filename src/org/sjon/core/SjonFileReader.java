package org.sjon.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * A wrapper class for loading the contents of an SJON file.
 * 
 * @author Andreas Tasoulas
 *
 */
public class SjonFileReader {
	
	private List<String> rawContent = new ArrayList<String>(); // All the content, line by line
	
	/**
	 * 
	 * Loads the contents of a file line by line. The file is closed and no further reference to it is needed anymore.
	 * 
	 * @param path the path of the file
	 * @throws FileNotFoundException the file is hiding somewhere
	 * @throws IOException problem reading the file. (Probably) not my fault.
	 */
	public SjonFileReader(String path) throws FileNotFoundException, IOException {
		
		File file = new File(path);
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line;
		while ((line = br.readLine()) != null) {
			rawContent.add(line);
		}
		br.close();
	}
	
	/**
	 * 
	 * Getter for the loaded contents of the file.
	 * 
	 * @return the contents of the file as list of strings, each corresponding to a logical line of the SJON file. The lines are 'raw', meaning unparsed.
	 */
	public List<String> getRawContent() {
		return this.rawContent;
	}
	
	/**
	 * 
	 * Getter for the 'data' subset of the loaded contents of the file.
	 * 
	 * The logical lines of an SJON file can either be data, metadata description of the 'schema', or comments. The first line of an SJON file is dedicated to the
	 * schema description. Its syntax is similar to that of the data record (curly-brace enclosed comma-list), but the values are names of types. 
	 * 
	 * The rest of lines can be either comments (quite predictably starting with the dash ('#') character) or data 'records'.
	 * 
	 * @return a list of strings, each corresponding to the 'raw' string representation of a data record
	 */
	public List<String> getRawData() {
		
		List<String> rawRecords = new ArrayList<String>();
		
		for (String line:this.rawContent) {
			if (!line.startsWith("#")) {
				rawRecords.add(line);
			}
		}
		
		rawRecords.remove(0); // We don't want to mix the schema description
		
		return rawRecords;
	}
	
	/**
	 * 
	 * Getter for the data, parsed as {@link SjonRecord} objects
	 * 
	 * @return a list of {@link SjonRecord} objects, each corresponding to a logical SJON file line, converted to an SJON record
	 */
	public List<SjonRecord> getData() {
		
		List<SjonRecord> data = new ArrayList<SjonRecord>();
		
		for (String rawRecord:this.getRawData()) {
			data.add(new SjonRecord(rawRecord));
		}
		
		return data;
	}
	
	/**
	 * 
	 * Creates an object of the schema corresponding to the SJON file data source of this SjonFileReader object
	 * 
	 * @return a schema object representing the SJON file contents loaded to the SjonFileReader object
	 */
	public Schema getSchema() {
		
		String schemaRecord = getRawContent().get(0);
		
		String schemaDescription = schemaRecord.substring(1, schemaRecord.length() - 1);
		
		String [] schemaTypes = schemaDescription.split(",");
		
		return new Schema(schemaTypes);
	}
}
