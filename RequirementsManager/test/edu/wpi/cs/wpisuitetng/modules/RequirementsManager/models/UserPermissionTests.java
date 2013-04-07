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
 *  CDUNKERS
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RMPermissionsLevel;

/**
 * Insert Description Here
 *
 * @author CDUNKERS
 *
 * @version Apr 2, 2013
 *
 */
public class UserPermissionTests {

	UserPermission uPerm1, uPerm2, uPerm3;
	
	@Before
	public void setUp() throws Exception {
		uPerm1 = new UserPermission("username1", RMPermissionsLevel.NONE);
		uPerm2 = new UserPermission("username2", RMPermissionsLevel.UPDATE);
		uPerm3 = new UserPermission("username3", RMPermissionsLevel.ADMIN);
	}

	@Test
	public void testConstructor() {
		assertEquals(uPerm1.getUsername(),"username1");
		assertEquals(uPerm2.getUsername(),"username2");
		assertEquals(uPerm3.getUsername(),"username3");
		assertEquals(uPerm1.getPermissions(),RMPermissionsLevel.NONE);
		assertEquals(uPerm2.getPermissions(),RMPermissionsLevel.UPDATE);
		assertEquals(uPerm3.getPermissions(),RMPermissionsLevel.ADMIN);
	}
	
	@Test
	public void testChangePermissionsLevel(){
		uPerm1.setPermissions(RMPermissionsLevel.UPDATE);
		uPerm2.setPermissions(RMPermissionsLevel.ADMIN);
		uPerm3.setPermissions(RMPermissionsLevel.NONE);
		assertEquals(uPerm1.getPermissions(), RMPermissionsLevel.UPDATE);
		assertEquals(uPerm2.getPermissions(), RMPermissionsLevel.ADMIN);
		assertEquals(uPerm3.getPermissions(), RMPermissionsLevel.NONE);
	}
	
}
