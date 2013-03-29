package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements;

import static org.junit.Assert.assertEquals;

import javax.swing.JPanel;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.MockNetwork;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Toolbar.ToolbarPanel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.DashboardTab;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.MainTabController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.MainTabView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.Tab;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.view.MainView;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.configuration.NetworkConfiguration;

/**
 * 
 * @author tli
 *
 */
public class TestSaveRequirementController {
	SaveRequirementController controller;
	RequirementView view;
	
	@Before
	public void setUp() throws Exception {
		Network.initNetwork(new MockNetwork());
		Network.getInstance().setDefaultNetworkConfiguration(new NetworkConfiguration("http://wpisuitetng"));
		Requirement tempReq = new Requirement();
		view = new RequirementView(tempReq, RequirementPanel.Mode.CREATE, null);
		controller = new SaveRequirementController(view);
	}
	
}
