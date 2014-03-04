package org.sjon.util;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.sjon.Resources;
import org.sjon.core.SjonFileReader;

public class SjonRefIntegrityCheckerTest {
	
	private static String primaryFilename = Resources.getDataResource(Resources.RANKING);
	private static String refFilename = Resources.getDataResource(Resources.SCORES);
	
	private static SjonFileReader primarySjonFileReader;
	private static SjonFileReader refSjonFileReader;
	
	@BeforeClass
	public static void setUp() throws IOException {
		primarySjonFileReader = new SjonFileReader(primaryFilename);
		refSjonFileReader = new SjonFileReader(refFilename);
	}
	
	@Test
	public void testCheck() {	
		SjonRefIntegrityChecker integrityChecker = new SjonRefIntegrityChecker(primarySjonFileReader, refSjonFileReader, 1, new int [] {0,1});
		assertTrue(integrityChecker.check());
	}
}
