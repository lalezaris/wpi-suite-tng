package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class UserTest {

	User u;
	
	@Before
	public void setUp() throws Exception {
		u = new User("name", "username", 134567, PermissionsLevel.NONE);
	}

	@Test
	public void testConstructor() {
		assertEquals(u.getId(),134567);
		assertEquals(u.getName(),"name");
		assertEquals(u.getUserName(),"username");
		assertEquals(u.getPermissions(),PermissionsLevel.NONE);
	}
	
	@Test
	public void testChangePermissionsLevel(){
		u.setPermissions(PermissionsLevel.ADMIN);
		assertEquals(u.getPermissions(), PermissionsLevel.ADMIN);
	}

}
