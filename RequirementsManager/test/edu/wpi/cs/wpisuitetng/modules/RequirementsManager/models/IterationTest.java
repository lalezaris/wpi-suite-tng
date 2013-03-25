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


import static edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.IterationStatus.*;

/**
 * Tester class for Requirement.java
 *
 * @author Tushar Narayan
 *
 * @version Mar 25, 2013
 *
 */
public class IterationTest {

	Date startDate, endDate;
	Iteration iteration1;
	IterationStatus itstatus1, itstatus2, itstatus3;
	
	@Before
	public void setUp(){
		startDate = new Date(2013, 3, 25, 3, 30, 20);
		endDate = new Date(2013, 3, 30, 5, 10, 10);
		iteration1 = new Iteration(1, startDate, endDate);
		itstatus1 = NEW;
		itstatus2 = OPEN;
		itstatus3 = CLOSED;
	}
	
	@Test
	public void testConstructor() {
		assertEquals(iteration1.getIterationNumber(), 1);
		assertEquals(iteration1.getStartDate(), startDate);
		assertEquals(iteration1.getEndDate(), endDate);
		assertEquals(iteration1.getRequirements(), new ArrayList<Integer>());
		assertEquals(iteration1.getStatus(), itstatus1);
	}
}
