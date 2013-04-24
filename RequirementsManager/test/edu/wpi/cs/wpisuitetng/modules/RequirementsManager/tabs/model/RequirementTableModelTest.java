/**************************************************
 * This file was developed for CS3733: Software Engineering
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html 
 *
 * Contributors:
 * 	Tianyu Li
 *  Michael Perrone
 *  Tushar Narayan
**************************************************/package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Iteration;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RequirementPriority;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RequirementStatus;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.action.Refresher;


/**
 * Tests for the RequirementTableModel
 * 
 * @author Tianyu Li
 * @author Michael Perrone
 */

public class RequirementTableModelTest {
	RequirementTableModel rtm1;
	DefaultTableColumnModel dtcm;

	@Before
	public void setUp(){
		rtm1 = new RequirementTableModel(null);
		Requirement req1 = new Requirement(1,"req1","des1",null);
		req1.setEstimateEffort(-1);
		Requirement req2 = new Requirement(2,"req2", "des2",null);
		Requirement req3 = new Requirement(3, "req3", "des3", null);
		Requirement req4 = new Requirement(4, "req4", "des4", null, null);
		req2.setStatus(RequirementStatus.COMPLETE);
		req3.setStatus(RequirementStatus.INPROGRESS);
		req4.setStatus(RequirementStatus.NEW);
		req1.setPriority(RequirementPriority.HIGH);
		req2.setPriority(RequirementPriority.LOW);
		req3.setPriority(RequirementPriority.MEDIUM);
		
		Iteration[] itList= {Iteration.getBacklog()};
		Refresher.getInstance().setLastKnownIterations(itList);
		
		rtm1.addRow(req1);
		rtm1.addRow(req2);
		rtm1.addRow(req3);
		rtm1.addRow(req4);
		
		
		dtcm = new DefaultTableColumnModel();
		TableColumn tc1 = new TableColumn();
		tc1.setHeaderValue("ID");
		TableColumn tc2 = new TableColumn();
		tc2.setHeaderValue("Name");
		TableColumn tc3 = new TableColumn();
		tc3.setHeaderValue("Description");
		TableColumn tc4 = new TableColumn();
		tc4.setHeaderValue("Status");
		dtcm.addColumn(tc1);
		dtcm.addColumn(tc2);
		dtcm.addColumn(tc3);
		dtcm.addColumn(tc4);
	}

	@Test
	public void dataAndHeaderFieldsInitialized() {
		assertTrue(rtm1.columnNames != null);
		assertTrue(rtm1.data != null);
		assertEquals(9, rtm1.columnNames.length);
		assertEquals(4, rtm1.data.size());
	}

	@Test
	public void dataCanBeinserted() {
		assertEquals("Name", rtm1.getColumnName(1));
		assertEquals(4, rtm1.getRowCount());
		assertEquals(1, rtm1.getValueAt(0, 0));
		assertEquals(1, rtm1.getRowID(0));
		assertEquals(-1, rtm1.getValueAt(0, 5));
		assertEquals("req2", rtm1.getValueAt(1, 1));
		assertEquals("des3", rtm1.getValueAt(2, 2));
		assertEquals(4, rtm1.getValueAt(3, 0));
		assertEquals(null, rtm1.getValueAt(5, 0));
	}

	@Test
	public void dataCanBeRemoved() {
		rtm1.removeRow(0);
		assertEquals(3, rtm1.getRowCount());
		assertEquals("req2", rtm1.getValueAt(0, 1));
		rtm1.removeRow(2);
		assertEquals(2, rtm1.getRowCount());
	}
	
	@Test
	public void dataCanBeCleared() {
		rtm1.clear();
		//assertEquals(0, rtm1.getRowCount());
	}
	
	@Test
	public void cellsCannotBeEdited() {		
		for (int i = 0; i < rtm1.getRowCount(); i++) {
			for (int j = 0; j < rtm1.getColumnCount(); j++) {
				assertFalse(rtm1.isCellEditable(i, j));
			}
		}
	}
	
	@Test
	public void testSortedAscending(){
		rtm1.sortTable(0, dtcm);//sort by ID
		assertEquals(rtm1.getValueAt(0, 1) ,"req1");
		assertEquals(rtm1.getValueAt(1, 1) ,"req2");
		assertEquals(rtm1.getValueAt(2, 1) ,"req3");
		assertEquals(rtm1.getValueAt(3, 1) ,"req4");
	}
	
	@Test
	public void testSortedDescending(){
		rtm1.sortTable(1,dtcm);//sort by name
		rtm1.sortTable(1,dtcm);//sory by name descending
		assertEquals(rtm1.getValueAt(0, 1) ,"req4");
		assertEquals(rtm1.getValueAt(1, 1) ,"req3");
		assertEquals(rtm1.getValueAt(2, 1) ,"req2");
		assertEquals(rtm1.getValueAt(3, 1) ,"req1");
	}
	
	@Test
	public void testHeaderAscending(){
		rtm1.sortTable(2,dtcm);
		assertEquals("Description"+RequirementTableModel.ASCENDING_SUFFIX , dtcm.getColumn(2).getHeaderValue().toString());
	}
	
	@Test
	public void testHeaderDescending(){
		rtm1.sortTable(2,dtcm);
		rtm1.sortTable(2,dtcm);
		assertEquals("Description"+RequirementTableModel.DESCENDING_SUFFIX , dtcm.getColumn(2).getHeaderValue().toString());
	}
	
	@Test
	public void testHeaderResets(){
		rtm1.sortTable(2,dtcm);
		rtm1.sortTable(2,dtcm);
		rtm1.sortTable(1,dtcm);
		rtm1.sortTable(0,dtcm);
		assertEquals("Description", dtcm.getColumn(2).getHeaderValue().toString());
		assertEquals("Name", dtcm.getColumn(1).getHeaderValue().toString());
	}
}
