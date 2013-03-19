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
 *  Ned Shelton
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.model;

import static edu.wpi.cs.wpisuitetng.modules.RequirementsManager.model.RequirementStatus.NEW;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

/**
 * Tester class for Requirement.java
 *
 * @author Tushar Narayan
 *
 * @version Mar 18, 2013
 *
 */
public class RequirementTest {

	Requirement r1;
	Requirement r2;
	Requirement r2copy;
	Requirement r3;
	
	@Before
	public void setUp(){
		r1 = new Requirement();
		r2 = new Requirement(10, "Test Requirement", "This is a test requirement.");
		r2copy = new Requirement(10, "Test Requirement", "This is a test requirement.");
		r3 = new Requirement(15, "Test Requirement 2", "This is another test requirement.");	
	}
	
	/**
	 * testing default constructor for Requirement class
	 * 
	 */
	@Test
	public void testDefaultConstructor() {
		assertEquals(r1.getReleaseNumber(), 0);
		assertEquals(r1.getStatus(), NEW);
		assertEquals(r1.getPriority(), "");
		assertEquals(r1.getName(), "");
		assertEquals(r1.getDescription(), "");
		assertEquals(r1.getEstimate(), 0);
		assertEquals(r1.getActualEffort(), 0);
		assertEquals(r1.getSubRequirements(), new ArrayList<Requirement>());
		assertEquals(r1.getType(), "Requirement");
		assertEquals(r1.getId(), -1);
	}
	
	/**
	 * testing constructor with arguments for Requirement class
	 * 
	 */
	@Test
	public void testParameterConstructor() {
		assertEquals(r2.getReleaseNumber(), 0);
		assertEquals(r2.getStatus(), NEW);
		assertEquals(r2.getPriority(), "");
		assertEquals(r2.getName(), "Test Requirement");
		assertEquals(r2.getDescription(), "This is a test requirement.");
		assertEquals(r2.getEstimate(), 0);
		assertEquals(r2.getActualEffort(), 0);
		assertEquals(r2.getSubRequirements(), new ArrayList<Requirement>());
		assertEquals(r2.getType(), "Requirement");
		assertEquals(r2.getId(), 10);
	}
	
	/**
	 * test for fromJSON function
	 * 
	 */
	@Test
	public void testfromJSON() {
		String json = r3.toJSON();
		Requirement newRequirement = Requirement.fromJSON(json);
		//assertTrue(newDefect.getEvents().get(0) instanceof DefectChangeset);
		//assertTrue(newDefect.getEvents().get(1) instanceof Comment);
		assertEquals(15, newRequirement.getId());
	}
	
	/**
	 * test for the Identify function
	 * 
	 */
	@Test
	public void testIdentify(){
		assertTrue(r1.identify(r1));
		assertTrue(r2.identify(r2copy));
		assertTrue(r2.identify("10"));
		assertTrue(r3.identify("15"));
		assertFalse(r1.identify(r2));
		assertFalse(r2.identify("20"));
		assertFalse(r2.identify(new Object()));
		assertFalse(r3.identify(null));
	}

}
