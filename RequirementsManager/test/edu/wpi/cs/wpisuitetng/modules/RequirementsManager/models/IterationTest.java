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
 *  Tushar Narayan
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;


import static edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.IterationStatus.*;

/**
 * Tester class for Requirement.java
 *
 * @author Tushar Narayan
 * @author Michael Perrone
 *
 * @version Mar 25, 2013
 *
 */
public class IterationTest {

	Date startDate, endDate;
	Date startDate2, endDate2;
	Iteration iteration1, iteration2;
	IterationStatus itstatus1, itstatus2, itstatus3;

	Requirement r1, r2, r2copy, r3, r4;
	
	
	@Before
	public void setUp(){
		startDate = new Date(2013, 3, 25, 3, 30, 20);
		endDate = new Date(2013, 3, 30, 5, 10, 10);
		startDate2 = new Date(2013, 5, 25, 3, 30, 20);
		endDate2 = new Date(2013, 5, 30, 5, 10, 10);
		iteration1 = new Iteration("1", startDate, endDate);
		iteration2 = new Iteration("2", startDate2, endDate2);
		itstatus1 = NEW;
		itstatus2 = OPEN;
		itstatus3 = CLOSED;
	}
	
	@Test
	public void testConstructor() {
		assertEquals(iteration1.getIterationName(), "1");
		assertEquals(iteration1.getStartDate(), startDate);
		assertEquals(iteration1.getEndDate(), endDate);
		assertEquals(iteration1.getRequirements(), new ArrayList<Integer>());
		assertEquals(iteration1.getStatus(), itstatus1);
	}
	
	@Test
	public void testAddRequirement(){
		iteration1.addRequirement(12);
		iteration1.addRequirement(34);
		iteration1.addRequirement(56);
		ArrayList<Integer> newList = new ArrayList<Integer>(3);
		newList.add(12);
		newList.add(34);
		newList.add(56);
		assertEquals(iteration1.getRequirements(), newList);
	}
	
	@Test
	public void testRemoveRequirement(){
		iteration1.addRequirement(12);
		iteration1.addRequirement(34);
		iteration1.addRequirement(56);
		iteration1.removeRequirement(34);
		ArrayList<Integer> newList = new ArrayList<Integer>(3);
		newList.add(12);
		newList.add(56);
		assertEquals(iteration1.getRequirements(), newList);
		iteration1.addRequirement(56);
		assertEquals(iteration1.getRequirements(), newList);
	}
	
	@Test
	public void testGetBacklog(){
		assertEquals(iteration1.getBacklog().getIterationName(), "Backlog");
	}
	
	@Test
	public void testGetIterationById(){
		assertEquals("1", iteration1.getIterationName());
	}
	
	@Test
	public void testDateSetters(){
		Date endDate1 = new Date();
		Date startDate1 = new Date();
		iteration1.setEndDate(endDate1);
		assertEquals(endDate1, iteration1.getEndDate());
		iteration1.setStartDate(startDate1);
		assertEquals(startDate1, iteration1.getStartDate());
	}
	
	@Test
	public void testIDs(){
		iteration1.setId(42);
		assertEquals(iteration1.getId(), 42);
	}
	
	@Test
	public void testStatuses(){
		iteration1.setStatus(itstatus3);
		assertEquals(itstatus3, iteration1.getStatus());
	}
	
	@Test
	public void testPlaceholderFunctions(){
		assertEquals(iteration1.identify(iteration1), null);
	}
	
	@Test
	public void testToString(){
		assertEquals("Iteration " + iteration1.getIterationName(), "Iteration 1");
	}
	
	@Test
	public void testEquals(){
		assertTrue(iteration1.equals(iteration1));
		assertTrue(!iteration1.equals(iteration2));
		assertTrue(iteration1.equals(new Iteration("1", startDate, endDate)));
	}
	
	@Test
	public void testChangeDates(){
		Date d = new Date();
		iteration1.setEndDate(d);
		iteration2.setStartDate(d);
		assertEquals(d,iteration1.getEndDate());
		assertEquals(d,iteration2.getStartDate());
	}
}
