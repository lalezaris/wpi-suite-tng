package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.model;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;

/**
 * 
 * @author Tianyu Li
 *
 */

public class RequirementTableModelTest {
	RequirementTableModel rtm1;

	@Before
	public void setUp() throws Exception {
		rtm1 = new RequirementTableModel();
	}

	@Test
	public void dataAndHeaderFieldsInitialized() {
		assertTrue(rtm1.columnNames != null);
		assertTrue(rtm1.data != null);
		assertEquals(9, rtm1.columnNames.length);
		assertEquals(0, rtm1.data.size());
	}

	@Test
	public void dataCanBeinserted() {
		insertTestData();
		
		assertEquals("Name", rtm1.getColumnName(1));
		assertEquals(4, rtm1.getRowCount());
		assertEquals(-1, rtm1.getValueAt(0, 0));
		assertEquals(-1, rtm1.getRowID(0));
		assertEquals("", rtm1.getValueAt(0, 5));
		assertEquals("req2", rtm1.getValueAt(1, 1));
		assertEquals("des3", rtm1.getValueAt(2, 2));
		assertEquals(4, rtm1.getValueAt(3, 0));
		assertEquals("null", rtm1.getValueAt(5, 0));
	}

	@Test
	public void dataCanBeRemoved() {
		insertTestData();
		
		rtm1.removeRow(0);
		assertEquals(3, rtm1.getRowCount());
		assertEquals("req2", rtm1.getValueAt(0, 1));
		rtm1.removeRow(2);
		assertEquals(2, rtm1.getRowCount());
	}
	
	@Test
	public void dataCanBeCleared() {
		insertTestData();
		
		rtm1.clear();
		assertEquals(0, rtm1.getRowCount());
	}
	
	@Test
	public void cellsCannotBeEdited() {		
		insertTestData();
		
		for (int i = 0; i < rtm1.getRowCount(); i++) {
			for (int j = 0; j < rtm1.getColumnCount(); j++) {
				assertFalse(rtm1.isCellEditable(i, j));
			}
		}
	}
	
	private void insertTestData() {
		Requirement req1 = new Requirement();
		req1.setEstimateEffort(-1);
		Requirement req2 = new Requirement("req2", "des2");
		Requirement req3 = new Requirement(3, "req3", "des3", null);
		Requirement req4 = new Requirement(4, "req4", "des4", null, null);
		rtm1.addRow(req1);
		rtm1.addRow(req2);
		rtm1.addRow(req3);
		rtm1.addRow(req4);
	}
}
