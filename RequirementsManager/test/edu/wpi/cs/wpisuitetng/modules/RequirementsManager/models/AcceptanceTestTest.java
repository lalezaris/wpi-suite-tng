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
 *  Mike French
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.IterationStatus;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;


import static edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.IterationStatus.*;

/**
 * Tests for the AcceptanceTest class
 * 
 * @author Mike French
 * 
 * @version April 14, 2013
 *
 */
public class AcceptanceTestTest {
	
	AcceptanceTest a;
	
	@Before
	public void setup(){
		a = new AcceptanceTest("A Title", "bodybodybodybodybodybody");
	}
	
	@Test
	public void testConstructor(){
		assertEquals(a.getTitle(), "A Title");
		assertEquals(a.getBody(), "bodybodybodybodybodybody");
	}

}
