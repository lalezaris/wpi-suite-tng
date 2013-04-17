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
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.MockNetwork;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RMPermissionsLevel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementPanel.EstimateListener;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementPanel.Mode;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementPanel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementPanel.SaveListener;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.observers.CurrentUserPermissions;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.configuration.NetworkConfiguration;

/**
 * Test for the Requirements Panel
 *
 * @author Chris Dunkers
 *
 * @version Apr 13, 2013
 *
 */
public class RequirementPanelViewTest {
	
	//RequirementPanel view;
	RequirementView reqView;
	Requirement req;
	
	@Before
	public void setUp() {
		Network.initNetwork(new MockNetwork());
		Network.getInstance().setDefaultNetworkConfiguration(new NetworkConfiguration("http://wpisuitetng"));
		
		req = new Requirement();
		reqView = new RequirementView(req, RequirementPanel.Mode.CREATE, null);
		reqView.setUp(req, RequirementPanel.Mode.CREATE, RMPermissionsLevel.ADMIN);
		//view = new RequirementPanel(reqView,RequirementPanel.Mode.CREATE);
	}
	
	//test that the save button is enabled/disabled properly
	@Test
	public void testSaveButtonEnable(){
		assertEquals(false, reqView.getRequirementPanel().getSaveRequirementBottom().isEnabled());
		reqView.getRequirementPanel().txtDescription.getKeyListeners()[0].keyReleased(null);
		reqView.getRequirementPanel().txtDescription.setText("new desc");
		reqView.getRequirementPanel().txtTitle.setText("new Title");
		reqView.getRequirementPanel().txtDescription.getKeyListeners()[0].keyReleased(null);
		reqView.getRequirementPanel().txtTitle.getKeyListeners()[0].keyReleased(null);
		assertEquals(true, reqView.getRequirementPanel().getSaveRequirementBottom().isEnabled());
	}
	
	//test that changing the status enables the Iteration field
	@Test
	public void testChangeStatus(){
		reqView.getRequirementPanel().txtEstimate.setText("5");
		reqView.getRequirementPanel().txtEstimate.getKeyListeners()[0].keyReleased(null);
		assertEquals("5", reqView.getRequirementPanel().txtEstimate.getText());
		assertEquals(true, reqView.getRequirementPanel().getCmbIteration().isEnabled());
	}
	


}
