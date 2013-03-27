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
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models;

import static edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.RequirementStatus.*;
import static edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.RequirementPriority.*;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;

/**
 * Tester class for Requirement.java
 *
 * @author Tushar Narayan
 *
 * @version Mar 18, 2013
 *
 */
public class RequirementTest {

	Date date1, date2;
	Requirement r1, r2, r2copy, r3, r4;
	RequirementStatus status1, status2, status3, status4;
	RequirementPriority priority1, priority2, priority3;
	
	@Before
	public void setUp(){
		date1 = new Date();
		r1 = new Requirement();
		r2 = new Requirement(10, "Test Requirement", "This is a test requirement.", new User("", "", "", -1));
		r2copy = new Requirement(10, "Test Requirement", "This is a test requirement.", new User("", "", "", -1));
		r3 = new Requirement(15, "Test Requirement 3", "This is another test requirement." , new User("", "", "", -1));	
		r4 = new Requirement(20, "Test Requirement 4", "This is the fourth test requirement.", new User("", "", "", -1));
		date2 = new Date();
		status1 = NEW;
		status2 = INPROGRESS;
		status3 = DONE;
		status4 = ACCEPTED;
		priority1 = HIGH;
		priority2 = MEDIUM;
		priority3 = LOW;
	}
	
	/**
	 * tests default constructor for Requirement class
	 * 
	 */
	@Test
	public void testDefaultConstructor() {
		assertEquals(r1.getReleaseNumber(), "");
		assertEquals(r1.getStatus(), NEW);
		assertEquals(r1.getPriority(), MEDIUM);
		assertEquals(r1.getTitle(), "");
		assertEquals(r1.getDescription(), "");
		assertEquals(r1.getEstimateEffort(), 0);
		assertEquals(r1.getActualEffort(), 0);
		assertEquals(r1.getSubRequirements(), new ArrayList<Requirement>());
	//	assertEquals(r1.getType(), "Requirement");
		assertEquals(r1.getId(), -1);
	}
	
	/**
	 * tests constructor with id, title, and description parameters for Requirement class
	 * 
	 */
	@Test
	public void testThreeParameterConstructor() {
		assertEquals(r2.getReleaseNumber(), "");
		assertEquals(r2.getStatus(), NEW);
		assertEquals(r2.getPriority(), MEDIUM);
		assertEquals(r2.getTitle(), "Test Requirement");
		assertEquals(r2.getDescription(), "This is a test requirement.");
		assertEquals(r2.getEstimateEffort(), 0);
		assertEquals(r2.getActualEffort(), 0);
		assertEquals(r2.getSubRequirements(), new ArrayList<Requirement>());
		//assertEquals(r2.getType(), "Requirement");
		assertEquals(r2.getId(), 10);
	}
	
	/**
	 * tests constructor with title and description parameters
	 * also tests the getters and setters
	 */
	@Test
	public void testTwoParameterConstructor(){
		assertEquals("", r4.getReleaseNumber());
		r4.setReleaseNumber("10101");
		assertEquals("10101", r4.getReleaseNumber());
		
		//testing all 4 statuses
		assertEquals(status1, r4.getStatus());
		r4.setStatus(status2);
		assertEquals(status2, r4.getStatus());
		r4.setStatus(status3);
		assertEquals(status3, r4.getStatus());
		r4.setStatus(status4);
		assertEquals(status4, r4.getStatus());
		
		//testing the 3 required priorities
		assertEquals(priority2, r4.getPriority());
		r4.setPriority(priority1);
		assertEquals(priority1, r4.getPriority());
		r4.setPriority(priority3);
		assertEquals(priority3, r4.getPriority());
		
		assertEquals("Test Requirement 4", r4.getTitle());
		
		assertEquals("This is the fourth test requirement.", r4.getDescription());
		
		assertEquals(0, r4.getEstimateEffort());
		
		assertEquals(0, r4.getActualEffort());
		
		assertEquals(r4.getSubRequirements(), new ArrayList<Requirement>());
		
		
		r4.setCreationDate(date1);
		assertEquals(date1, r4.getCreationDate());
		
		r4.setLastModifiedDate(date2);
		assertEquals(date2, r4.getLastModifiedDate());
		
		assertEquals(20, r4.getId());
		r4.setId(42);
		assertEquals(42, r4.getId());
	}
	
	/**
	 * test for fromJSON and toJSON functions
	 * 
	 */
	@Test
	public void testfromJSON() {
		String json = r3.toJSON();
		Requirement newRequirement = Requirement.fromJSON(json);
		assertEquals(15, newRequirement.getId());
	}
}
