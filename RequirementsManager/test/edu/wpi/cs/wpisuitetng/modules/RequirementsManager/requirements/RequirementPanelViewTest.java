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
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Note;
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
 * @author Michael French
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
	
	//test that the view is indeed populating the fields with the given requirement's information
	@Test 
	public void testGettingRequirement(){
		Requirement newReq = new Requirement("newReq", "a desc");
		RequirementView newReqView = new RequirementView(newReq, RequirementPanel.Mode.CREATE, null);
		newReqView.setUp(newReq, RequirementPanel.Mode.CREATE, RMPermissionsLevel.ADMIN);
		assertEquals("newReq", newReqView.getRequirementPanel().getTxtTitle().getText());
		assertEquals("a desc", newReqView.getRequirementPanel().getTxtDescription().getText());
	}
	
	//test that the proper things are disabled according to the current user's permission level
	@Test
	public void testPermissions(){
		Requirement req2 = new Requirement();
		
		RequirementView noneView = new RequirementView(req2, RequirementPanel.Mode.EDIT, null);
		noneView.setUp(req2, RequirementPanel.Mode.CREATE, RMPermissionsLevel.NONE);
		
		RequirementView updateView = new RequirementView(req2, RequirementPanel.Mode.EDIT, null);
		updateView.setUp(req2, RequirementPanel.Mode.CREATE, RMPermissionsLevel.UPDATE);
		
		RequirementView adminView = new RequirementView(req2, RequirementPanel.Mode.EDIT, null);
		adminView.setUp(req2, RequirementPanel.Mode.CREATE, RMPermissionsLevel.ADMIN);
		
		assertEquals(false, noneView.getRequirementPanel().txtTitle.isEnabled());
		assertEquals(false, noneView.getRequirementPanel().txtDescription.isEnabled());
		assertEquals(false, noneView.getRequirementPanel().txtEstimate.isEnabled());
		assertEquals(false, noneView.getRequirementPanel().txtActual.isEnabled());
		assertEquals(false, noneView.getRequirementPanel().txtReleaseNumber.isEnabled());
		assertEquals(false, noneView.getRequirementPanel().cmbIteration.isEnabled());
		assertEquals(false, noneView.getRequirementPanel().cmbPriority.isEnabled());
		assertEquals(false, noneView.getRequirementPanel().cmbStatus.isEnabled());
		assertEquals(false, noneView.getRequirementPanel().cmbType.isEnabled());
		
		assertEquals(false, updateView.getRequirementPanel().txtTitle.isEnabled());
		assertEquals(false, updateView.getRequirementPanel().txtDescription.isEnabled());
		assertEquals(false, updateView.getRequirementPanel().txtEstimate.isEnabled());
		assertEquals(false, updateView.getRequirementPanel().txtActual.isEnabled());
		assertEquals(false, updateView.getRequirementPanel().txtReleaseNumber.isEnabled());
		assertEquals(false, updateView.getRequirementPanel().cmbIteration.isEnabled());
		assertEquals(false, updateView.getRequirementPanel().cmbPriority.isEnabled());
		assertEquals(false, updateView.getRequirementPanel().cmbStatus.isEnabled());
		assertEquals(false, updateView.getRequirementPanel().cmbType.isEnabled());
		
		assertEquals(true, adminView.getRequirementPanel().txtTitle.isEnabled());
		assertEquals(true, adminView.getRequirementPanel().txtDescription.isEnabled());
		assertEquals(true, adminView.getRequirementPanel().txtEstimate.isEnabled());
		assertEquals(true, adminView.getRequirementPanel().txtActual.isEnabled());
		assertEquals(true, adminView.getRequirementPanel().txtReleaseNumber.isEnabled());
		assertEquals(false, adminView.getRequirementPanel().cmbIteration.isEnabled());
		assertEquals(true, adminView.getRequirementPanel().cmbPriority.isEnabled());
		assertEquals(true, adminView.getRequirementPanel().cmbStatus.isEnabled());
		assertEquals(true, adminView.getRequirementPanel().cmbType.isEnabled());
		
		
	}

}
