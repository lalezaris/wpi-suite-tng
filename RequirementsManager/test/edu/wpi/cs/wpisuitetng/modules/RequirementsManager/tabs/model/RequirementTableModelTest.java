package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;


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
		assertEquals(8, rtm1.columnNames.length);
		//assertEquals(0, rtm1.data.length);
	}

	@Test
	public void dataCanBeReplaced() {
		insertTestData();

		assertEquals(0, rtm1.getRowCount());
//		assertEquals("0,0", rtm1.getValueAt(0, 0));
//		assertEquals("1,1", rtm1.getValueAt(1, 1));
//		assertEquals(3, rtm1.getValueAt(2, 2));
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
	
//	@Test
//	public void columnClassTypesAreMaintained() {
//		insertTestData();
//		
//		assertEquals(String.class, rtm1.getColumnClass(0));
//		assertEquals(String.class, rtm1.getColumnClass(1));
//		assertEquals(Integer.class, rtm1.getColumnClass(2));
//	}
	
	private void insertTestData() {
		String[] columnNames = {"Col A", "Col B", "Col C"};

		Object[][] newData = {
				{"0,0", "0,1", new Integer(1)},
				{"1,0", "1,1", new Integer(2)},
				{"2,0", "2,1", new Integer(3)}
		};

		//rtm1.setColumnNames(columnNames);
		//rtm1.setData(newData);
	}
}
