package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.ToolBar;

import static org.junit.Assert.*;

import org.junit.*;
import org.junit.Assert.*;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Toolbar.ToolbarPanel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.MainTabView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.controller.MainTabController;

public class ToolBarTest {
	ToolbarPanel tbp;
	
	@Before
	public void setup(){
		tbp = new ToolbarPanel(new MainTabController(new MainTabView()));
	}
	
	@Test
	public void testSetToolbarDisplay(){
		tbp.setToolbarDisplay("NONE");
		assertEquals(true, tbp.checkDisplayNone());
		tbp.setToolbarDisplay("UPDATE");
		assertEquals(true, tbp.checkDisplayUpdate());
		tbp.setToolbarDisplay("ADMIN");
		assertEquals(true, tbp.checkDisplayAdmin());
	}
	
}
