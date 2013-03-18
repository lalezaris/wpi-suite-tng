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
 *  Tushar
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.model;

import static edu.wpi.cs.wpisuitetng.modules.RequirementsManager.model.RequirementStatus.FUTURE;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.core.models.Project;

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
	
	@Before
	public void setUp(){
		r1 = new Requirement();
		r2 = new Requirement(10, "Test Requirement", "This is a test requirement.");
	}
	
	@Test
	public void testDefaultConstructor() {
		assertEquals(r1.getReleaseNumber(), 0);
		assertEquals(r1.getStatus(), FUTURE);
		assertEquals(r1.getPriority(), "");
		assertEquals(r1.getName(), "");
		assertEquals(r1.getDescription(), "");
		assertEquals(r1.getEstimate(), 0);
		assertEquals(r1.getActualEffort(), 0);
		assertEquals(r1.getSubRequirements(), new ArrayList<Requirement>());
		assertEquals(r1.getType(), "Requirement");
		assertEquals(r1.getId(), -1);
	}
	
	@Test
	public void testParameterConstructor() {
		assertEquals(r2.getReleaseNumber(), 0);
		assertEquals(r2.getStatus(), FUTURE);
		assertEquals(r2.getPriority(), "");
		assertEquals(r2.getName(), "Test Requirement");
		assertEquals(r2.getDescription(), "This is a test requirement.");
		assertEquals(r2.getEstimate(), 0);
		assertEquals(r2.getActualEffort(), 0);
		assertEquals(r2.getSubRequirements(), new ArrayList<Requirement>());
		assertEquals(r2.getType(), "Requirement");
		assertEquals(r2.getId(), 10);
	}

}
