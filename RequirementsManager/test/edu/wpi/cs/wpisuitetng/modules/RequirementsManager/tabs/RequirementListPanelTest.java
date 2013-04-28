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
 *  Tianyu Li
 **************************************************/

package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs;

import static org.junit.Assert.*;

import javax.swing.JPanel;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.MockNetwork;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.controller.MainTabController;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.configuration.NetworkConfiguration;

/**
 * @author Tianyu Li
 *
 */
public class RequirementListPanelTest {
	RequirementListPanel panel;
	
	@Before
	public void setUp() {
		Network.initNetwork(new MockNetwork());
		Network.getInstance().setDefaultNetworkConfiguration(new NetworkConfiguration("http://wpisuitetng"));
		panel = new RequirementListPanel(MainTabController.getController());
		Requirement req1 = new Requirement("req1", "des1");
		Requirement req2 = new Requirement("req2", "des2");
		Requirement req3 = new Requirement("req3", "des3");
		Requirement req4 = new Requirement("req4", "des4");
		Requirement[] reqs1 = {req1, req2, req3, req4};
		panel.addRequirements(reqs1);
	}
	
	@Test
	public void panelNotNullTest() {
		assertNotNull(panel);
	}
	
	@Test
	public void buttonPanelTest() {
		JPanel buttonPanel = panel.getButtonPanel();
		assertTrue(buttonPanel.isVisible());
		assertEquals(4, buttonPanel.getComponentCount());
	}
	
	@Test
	public void addRequirementsTest() {
		assertEquals(4, panel.getModel().getRequirements().size());
		assertEquals("req1", panel.getModel().getRequirements().get(0).getTitle());
		assertEquals("des1", panel.getModel().getRequirements().get(0).getDescription());
	}
}
