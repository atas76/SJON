package org.sjon.util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.sjon.core.SjonFileReader;
import org.sjon.core.SjonRecord;

/**
 * 
 * A utility class for checking the referential integrity between two SJON files contents. The referential integrity is not imposed by any external rule or declaration.
 * It is just of semantic nature, and this class is used for checking semantic consistency of data between arbitrarily chosen files.
 * 
 * @author Andreas Tasoulas
 *
 */
public class SjonRefIntegrityChecker {
	
	private SjonFileReader primary;
	private SjonFileReader referenced;
	private int primaryKeyIndex;
	private int [] foreignKeyIndices;
	
	private Logger logger = Logger.getLogger(this.getClass().getName());
	
	/**
	 * 
	 * Constructor for the integrity checker object.
	 * 
	 * @param primary the object containing the "reference data", equivalent to a table that has a one-to-many relation to its referenced tables in the relational model
	 * @param referenced the object containing the "referenced data", equivalent to one of the tables that are mapped in a one-to-many relation to a "reference table"
	 * @param primaryKeyIndex the index of the primary key column in the reference data
	 * @param foreignKeyIndices an array of the foreign key column indices in the referenced data
	 */
	public SjonRefIntegrityChecker(SjonFileReader primary, SjonFileReader referenced, int primaryKeyIndex, int [] foreignKeyIndices) {
		this.primary = primary;
		this.referenced = referenced;
		this.primaryKeyIndex = primaryKeyIndex;
		this.foreignKeyIndices = foreignKeyIndices;
	}
	
	/**
	 * 
	 * A simple checker of referential integrity according to the constructor parameters. Each value in each of the foreign key columns in the referenced data must match
	 * a value in the primary key column of the reference data.
	 * 
	 * @return <code>true</code> if referential integrity test passes, <code>false</code> otherwise
	 */
	public boolean check() {
		
		List<SjonRecord> primaryData = primary.getData();
		List<SjonRecord> referencedData = referenced.getData();
		
		Set<String> primaryKeys = new HashSet<String>();
		
		for (SjonRecord primaryDataRecord:primaryData) {
			primaryDataRecord.getData(); // For logging purposes
			primaryKeys.add(primaryDataRecord.getString(primaryKeyIndex));
		}
		
		for (SjonRecord refDataRecord:referencedData) {
			for (int i = 0; i < foreignKeyIndices.length; i++) {
				String data = refDataRecord.getString(i);
				if (!primaryKeys.contains(data)) {
					logger.warning("Failure finding " + data);
					return false;
				}
			}
		}
		return true;
	}
}
