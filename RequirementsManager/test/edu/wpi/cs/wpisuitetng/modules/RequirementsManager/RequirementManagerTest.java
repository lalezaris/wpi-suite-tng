/**
 * This file was developed for CS3733: Software Engineering
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Michael French
 */
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager;

import org.junit.*;
import static org.junit.Assert.assertEquals;

public class RequirementManagerTest {

	RequirementsManager rm;
	
	/**
	 * Setup.
	 */
	@Before
	public void setup(){
		rm = new RequirementsManager();
	}
	
	/**
	 * Test constructor.
	 */
	@Test
	public void testConstructor(){
		//make sure everything is initialized
		assertEquals(true, rm.getTabs().size() > 0);
		assertEquals(true, rm.tabController != null);
		assertEquals(true, rm.toolbarPanel != null);
	}
}
