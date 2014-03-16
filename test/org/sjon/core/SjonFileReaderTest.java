package org.sjon.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.sjon.Resources;

public class SjonFileReaderTest {
	
	private static String filename = Resources.getDataResource(Resources.FIXTURES);
	private static SjonFileReader sjonFileReader;
	
	private final static int expectedContentNumber = 6;
	private final static int expectedRecordsNumber = 4;
	
	private final String dateFormat = "yyyy-MM-dd HH:mm";
	
	private final double oddsThreshold = 0.01;
	
	@BeforeClass
	public static void setUp() throws IOException {
		sjonFileReader = new SjonFileReader(filename);
	}
	
	@Test
	public void testRawContentReading() {
		
		List<String> rawContent = sjonFileReader.getRawContent();
		assertEquals(expectedContentNumber, rawContent.size());
	}
	
	@Test
	public void testRawRecordsReading() {
		List<String> rawRecords = sjonFileReader.getRawData();
		assertEquals(expectedRecordsNumber, rawRecords.size());
	}
	
	@Test
	public void testExplicitContent() throws ParseException {
		
		SjonRecord record = sjonFileReader.getData().get(0);
		
		assertEquals("Manchester United", record.getString(0));
		assertEquals("Liverpool", record.getString(1));
		assertEquals("Home", record.getString(2));
		assertEquals(new SimpleDateFormat(dateFormat).parse("2014-03-16 15:30").getTime(), record.getDateTime(3).getTime());
		assertEquals(2.6, record.getDouble(4), oddsThreshold);
		assertEquals(3.6, record.getDouble(5), oddsThreshold);
		assertEquals(2.95, record.getDouble(6), oddsThreshold);
		
		record = sjonFileReader.getData().get(2);
		
		assertEquals("Frankfurt", record.getString(0));
		assertEquals("Hamburger", record.getString(1));
		assertEquals("Home", record.getString(2));
		assertEquals(new SimpleDateFormat(dateFormat).parse("2014-03-16 18:30").getTime(), record.getDateTime(3).getTime());
		assertEquals(1.93, record.getDouble(4), oddsThreshold);
		assertEquals(3.71, record.getDouble(5), oddsThreshold);
		assertEquals(4.75, record.getDouble(6), oddsThreshold);
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
			case "DateTime":
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
