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
 *  Chris Dunkers
 *  Mike Perrone
 *  Tushar Narayan
 **************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions;

import static org.junit.Assert.*;

import org.junit.*;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.UserPermission;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.model.PermissionModel;

public class PermissionModelTest {
	PermissionModel p;
	
	/**
	 * Setup.
	 */
	@Before
	public void setup(){
		p = new PermissionModel();
	}
	
	/**
	 * Test add permission.
	 */
	@Test
	public void testAddPermission(){
		UserPermission up = new UserPermission();
		p.addPermission(up);
		assertEquals(up, p.getPermissions()[0]);
	}

}
