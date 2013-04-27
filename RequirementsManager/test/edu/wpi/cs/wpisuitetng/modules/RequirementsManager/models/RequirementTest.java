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
 *  Evan Polekoff
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models;

import static edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RequirementPriority.HIGH;
import static edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RequirementPriority.LOW;
import static edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RequirementPriority.MEDIUM;
import static edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RequirementStatus.COMPLETE;
import static edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RequirementStatus.DELETED;
import static edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RequirementStatus.INPROGRESS;
import static edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RequirementStatus.NEW;
import static edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RequirementStatus.OPEN;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RequirementPriority;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RequirementStatus;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RequirementType;

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
	Requirement r5, r6;
	ArrayList<Note> testNotes;
	RequirementStatus status1, status2, status3, status4, status5;
	RequirementPriority priority1, priority2, priority3;
	
	/**
	 * Sets up the tests.
	 */
	@Before
	public void setUp(){
		date1 = new Date();
		r1 = new Requirement();
		r2 = new Requirement(10, "Test Requirement", "This is a test requirement.", "");
		r2copy = new Requirement(10, "Test Requirement", "This is a test requirement.", "");
		r3 = new Requirement(15, "Test Requirement 3", "This is another test requirement." , "");	
		r4 = new Requirement(20, "Test Requirement 4", "This is the fourth test requirement.", "");
		date2 = new Date();
		status1 = NEW;
		status2 = INPROGRESS;
		status3 = OPEN;
		status4 = COMPLETE;
		status5 = DELETED;
		priority1 = HIGH;
		priority2 = MEDIUM;
		priority3 = LOW;
		r5 = new Requirement("Test Title", "Test Description");
		testNotes = new ArrayList<Note>();
		r6 = new Requirement(42, "Test Title 2", "Test Description 2", "", testNotes);
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
	//This test needs the network to be set up
		//assertEquals(childList.retrieveChildrenByID(r1.getId()), new ArrayList<Requirement>());
	//Not sure if we still will implement types
		//assertEquals(r1.getType(), "Requirement");
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
		//assertEquals(childList.retrieveChildrenByID(r2.getId()), new ArrayList<Requirement>());
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
		
		//assertEquals(childList.retrieveChildrenByID(r4.getId()), new ArrayList<Requirement>());
		
		
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
	
	/**
	 * Tests creating a note.
	 */
	@Test
	public void testNote(){
		Note testNote1 = new Note("New Note Body", "New User");
		r5.addNote(testNote1);
		ArrayList<Note> containsTestNote1 = new ArrayList<Note>();
		containsTestNote1.add(testNote1);
		assertEquals(r5.getNotes(), containsTestNote1);
		assertEquals(r5.countNotes(), 1);
		assertEquals(r6.countNotes(), 0);
		testNotes.add(new Note("First Note Body", "First Note Person"));
		testNotes.add(new Note("Second Note Body", "Second Note Person"));
		assertEquals(r6.countNotes(), 2);
		assertEquals(r5.getNotes(), containsTestNote1);
		r5.updateNotes(testNotes);
		assertEquals(r5.getNotes(), testNotes);
		assertEquals(r5.countNotes(), 2);
	}
	
	/**
	 * Tests more setters.
	 */
	@Test
	public void testMoreSetters(){
		r5.setTitle("This has to be a title string!");
		assertEquals(r5.getTitle(), "This has to be a title string!");
		r5.setDescription("And this must be a description string...");
		assertEquals(r5.getDescription(), "And this must be a description string...");
		r6.setEstimateEffort(100);
		r6.setActualEffort(50);
		assertEquals(r6.getEstimateEffort(), 100);
		assertEquals(r6.getActualEffort(), 50);
		String testUser1 = "user1";
		String testUser2 = "user2";
		r6.setCreator(testUser1);
		assertEquals(r6.getCreator(), testUser1);
//		r6.setAssignee(testUser2);
//		assertEquals(r6.getAssignee(), testUser2);
		
	}
	
	/**
	 * Test enums for requirement priority and types.
	 */
	@Test
	public void testEnums(){
		assertEquals(RequirementPriority.BLANK,RequirementPriority.valueFromString(""));
		assertEquals(RequirementPriority.LOW,RequirementPriority.valueFromString("LOW"));
		assertEquals(RequirementPriority.MEDIUM,RequirementPriority.valueFromString("MEDIUM"));
		assertEquals(RequirementPriority.HIGH,RequirementPriority.valueFromString("HIGH"));
		
		assertEquals(RequirementType.BLANK,RequirementType.valueFromString(""));
		assertEquals(RequirementType.EPIC,RequirementType.valueFromString("EPIC"));
		assertEquals(RequirementType.THEME,RequirementType.valueFromString("THEME"));
		assertEquals(RequirementType.USERSTORY,RequirementType.valueFromString("USER STORY"));
		assertEquals(RequirementType.NONFUNCTIONAL,RequirementType.valueFromString("NON FUNCTIONAL"));
		assertEquals(RequirementType.SCENARIO,RequirementType.valueFromString("SCENARIO"));
	}
	
}
