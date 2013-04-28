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
 * 	Lauren Kahn
 *  Michael Perrone
**************************************************/package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.model;

import static edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.IterationStatus.CLOSED;
import static edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.IterationStatus.NEW;
import static edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.IterationStatus.OPEN;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Iteration;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.IterationStatus;

/**
 * 
 * @author Lauren Kahn
 * @author Michael Perrone
 *
 */

public class IterationTableModelTest {
	IterationTableModel it1;
	Date startDate, endDate;
	Date startDate2, endDate2;
	Iteration iteration1, iteration2;
	IterationStatus itstatus1, itstatus2, itstatus3;
	
	DefaultTableColumnModel itcm;
	
	
	Requirement r1, r2, r2copy, r3, r4;

	/**
	 * Sets up the test.
	 */
	@Before
	public void setUp(){
		it1 = new IterationTableModel();
		startDate = new Date(2013, 3, 25, 3, 30, 20);
		endDate = new Date(2013, 3, 30, 5, 10, 10);
		startDate2 = new Date(2013, 5, 25, 3, 30, 20);
		endDate2 = new Date(2013, 5, 30, 5, 10, 10);
		iteration1 = new Iteration("1", startDate, endDate);
		iteration2 = new Iteration("2", startDate2, endDate2);
		iteration1.setId(1);
		iteration2.setId(2);
		itstatus1 = NEW;
		itstatus2 = OPEN;
		itstatus3 = CLOSED;
		it1.addRow(iteration1);
		it1.addRow(iteration2);
		
		
		itcm = new DefaultTableColumnModel();
		TableColumn tc1 = new TableColumn();
		tc1.setHeaderValue("ID");
		TableColumn tc2 = new TableColumn();
		tc2.setHeaderValue("Name");
		TableColumn tc3 = new TableColumn();
		tc3.setHeaderValue("Start Date");
		TableColumn tc4 = new TableColumn();
		tc4.setHeaderValue("End Date");
		itcm.addColumn(tc1);
		itcm.addColumn(tc2);
		itcm.addColumn(tc3);
		itcm.addColumn(tc4);
	}

	/**
	 * Test if data and header fields get initialized.
	 */
	@Test
	public void dataAndHeaderFieldsInitialized() {
		assertTrue(it1.columnNames != null);
		assertTrue(it1.data != null);
		assertEquals(4, it1.columnNames.length);
		assertEquals(2, it1.data.size());
	}

	/**
	 * Test if data can be inserted.
	 */
	@Test
	public void dataCanBeinserted() {
		SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM dd, yyyy");
		assertEquals("ID", it1.getColumnName(0));
		assertEquals(2, it1.getRowCount());
		assertEquals(1, it1.getValueAt(0, 0));
		assertEquals(1, it1.getRowID(0));
		assertEquals("2", it1.getValueAt(1, 1));
		assertEquals(sdf.format(startDate2), it1.getValueAt(1, 2));
		assertEquals("null", it1.getValueAt(5, 0));
	}
	
	/**
	 * Test if data can be cleared.
	 */
	@Test
	public void dataCanBeCleared() {
		it1.clear();
		assertEquals(0, it1.getRowCount());
	}
	
	/**
	 * Test if cells cannot be edited.
	 */
	@Test
	public void cellsCannotBeEdited() {		
		for (int i = 0; i < it1.getRowCount(); i++) {
			for (int j = 0; j < it1.getColumnCount(); j++) {
				assertFalse(it1.isCellEditable(i, j));
			}
		}
	}
	

	/**
	 * Test sort table by ascending.
	 */
	@Test
	public void testSortedAscending(){
		it1.sortTable(0, itcm);//sort by ID
		assertEquals(it1.getValueAt(0, 1) ,"1");
		assertEquals(it1.getValueAt(1, 1) ,"2");
	}
	
	/**
	 * Test sort table by descending.
	 */
	@Test
	public void testSortedDescending(){
		it1.sortTable(1,itcm);//sort by name
		it1.sortTable(1,itcm);//sory by name descending
		assertEquals(it1.getValueAt(0, 1) ,"2");
		assertEquals(it1.getValueAt(1, 1) ,"1");
	}
	
	/**
	 * Test sort header by ascending.
	 */
	@Test
	public void testHeaderAscending(){
		it1.sortTable(2,itcm);
		assertEquals("Start Date"+RequirementTableModel.ASCENDING_SUFFIX , itcm.getColumn(2).getHeaderValue().toString());
	}
	
	/**
	 * Test sort header by descending.
	 */
	@Test
	public void testHeaderDescending(){
		it1.sortTable(2,itcm);
		it1.sortTable(2,itcm);
		assertEquals("Start Date"+RequirementTableModel.DESCENDING_SUFFIX , itcm.getColumn(2).getHeaderValue().toString());
	}
	
	/**
	 * Test reseting the table headers.
	 */
	@Test
	public void testHeaderResets(){
		it1.sortTable(2,itcm);
		it1.sortTable(2,itcm);
		it1.sortTable(1,itcm);
		it1.sortTable(0,itcm);
		assertEquals("Start Date", itcm.getColumn(2).getHeaderValue().toString());
		assertEquals("Name", itcm.getColumn(1).getHeaderValue().toString());
	}
}
