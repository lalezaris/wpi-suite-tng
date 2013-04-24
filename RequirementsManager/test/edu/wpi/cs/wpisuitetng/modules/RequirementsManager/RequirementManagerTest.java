package edu.wpi.cs.wpisuitetng.modules.RequirementsManager;

import org.junit.*;
import static org.junit.Assert.assertEquals;

public class RequirementManagerTest {

	RequirementsManager rm;
	@Before
	public void setup(){
		rm = new RequirementsManager();
	}
	
	@Test
	public void testConstructor(){
		//make sure everything is initialized
		assertEquals(true, rm.getTabs().size() > 0);
		assertEquals(true, rm.tabController != null);
		assertEquals(true, rm.toolbarPanel != null);
	}
}
