package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.model;

import static edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.IterationStatus.CLOSED;
import static edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.IterationStatus.NEW;
import static edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.IterationStatus.OPEN;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Iteration;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.IterationStatus;

/**
 * 
 * @author Lauren Kahn
 *
 */

public class IterationTableModelTest {
	IterationTableModel it1;
	Date startDate, endDate;
	Date startDate2, endDate2;
	Iteration iteration1, iteration2;
	IterationStatus itstatus1, itstatus2, itstatus3;

	Requirement r1, r2, r2copy, r3, r4;

	@Before
	public void setUp() throws Exception {
		it1 = new IterationTableModel();
		startDate = new Date(2013, 3, 25, 3, 30, 20);
		endDate = new Date(2013, 3, 30, 5, 10, 10);
		startDate2 = new Date(2013, 5, 25, 3, 30, 20);
		endDate2 = new Date(2013, 5, 30, 5, 10, 10);
		iteration1 = new Iteration("1", startDate, endDate);
		iteration2 = new Iteration("2", startDate2, endDate2);
		itstatus1 = NEW;
		itstatus2 = OPEN;
		itstatus3 = CLOSED;
		it1.addRow(iteration1);
		it1.addRow(iteration2);
	}

	@Test
	public void dataAndHeaderFieldsInitialized() {
		assertTrue(it1.columnNames != null);
		assertTrue(it1.data != null);
		assertEquals(4, it1.columnNames.length);
		assertEquals(2, it1.data.size());
	}

	@Test
	public void dataCanBeinserted() {
		SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM dd, yyyy");
		assertEquals("ID", it1.getColumnName(0));
		assertEquals(2, it1.getRowCount());
		assertEquals(0, it1.getValueAt(0, 0));
		assertEquals(0, it1.getRowID(0));
		assertEquals("2", it1.getValueAt(1, 1));
		assertEquals(sdf.format(startDate2), it1.getValueAt(1, 2));
		assertEquals("null", it1.getValueAt(5, 0));
	}
	
	@Test
	public void dataCanBeCleared() {
		it1.clear();
		assertEquals(0, it1.getRowCount());
	}
	
	@Test
	public void cellsCannotBeEdited() {		
		for (int i = 0; i < it1.getRowCount(); i++) {
			for (int j = 0; j < it1.getColumnCount(); j++) {
				assertFalse(it1.isCellEditable(i, j));
			}
		}
	}
}
