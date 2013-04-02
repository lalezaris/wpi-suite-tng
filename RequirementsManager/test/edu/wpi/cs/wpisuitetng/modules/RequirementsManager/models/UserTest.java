package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class UserTest {

	UserPermission u;
	
	@Before
	public void setUp() throws Exception {
		u = new UserPermission("username", RMPermissionsLevel.NONE);
	}

	@Test
	public void testConstructor() {
		assertEquals(u.getUsername(),"username");
		assertEquals(u.getPermissions(),RMPermissionsLevel.NONE);
	}
	
	@Test
	public void testChangePermissionsLevel(){
		u.setPermissions(RMPermissionsLevel.ADMIN);
		assertEquals(u.getPermissions(), RMPermissionsLevel.ADMIN);
	}

}
