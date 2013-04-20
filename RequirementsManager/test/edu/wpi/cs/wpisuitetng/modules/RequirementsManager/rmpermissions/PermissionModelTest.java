package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions;

import static org.junit.Assert.*;

import org.junit.*;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.UserPermission;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.model.PermissionModel;

public class PermissionModelTest {
	PermissionModel p;
	
	@Before
	public void setup(){
		p = new PermissionModel();
	}
	
	@Test
	public void testAddPermission(){
		UserPermission up = new UserPermission();
		p.addPermission(up);
		assertEquals(up, p.getPermissions()[0]);
	}

}
