package org.sjon.core;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;
import org.sjon.Resources;

public class SjonFileReaderTest {
	
	private static String filename = Resources.getDataResource(Resources.SCORES);
	private static SjonFileReader sjonFileReader;
	
	@BeforeClass
	public static void setUp() throws IOException {
		sjonFileReader = new SjonFileReader(filename);
	}
	
	@Test
	public void testRawContentReading() {
		
		List<String> rawContent = sjonFileReader.getRawContent();
		assertEquals(482, rawContent.size());
	}
	
	@Test
	public void testRawRecordsReading() {
		List<String> rawRecords = sjonFileReader.getRawData();
		assertEquals(447, rawRecords.size());
	}
	
	@Test
	public void testSchemaDescriptionValidity() {
		
		String schemaRecord = sjonFileReader.getRawContent().get(0);
		
		assertTrue(schemaRecord.startsWith("{"));
		assertTrue(schemaRecord.endsWith("}"));
		
		String schemaDescription = schemaRecord.substring(1, schemaRecord.length() - 1);
		
		String [] schemaTypes = schemaDescription.split(",");
		
		for (String schemaType:schemaTypes) {
			switch(schemaType) {
			case "String":
				assertTrue(true);
				break;
			case "Integer":
				assertTrue(true);
				break;
			case "Double":
				assertTrue(true);
				break;
			default:
				fail();
			}
		}	
	}
	
	@Test
	public void testSchemaConformance() {
		
		Schema schema = sjonFileReader.getSchema();
		List<String> rawData = sjonFileReader.getRawData();
		
		for (String rawRecord:rawData) {
			assertTrue(schema.conforms(rawRecord));
		}
	}
}
