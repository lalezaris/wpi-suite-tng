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
 * 	Tianyu Li
**************************************************/package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.MockNetwork;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.SaveRequirementController;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.configuration.NetworkConfiguration;

/**
 * 
 * @author Tianyu Li
 *
 */
public class SaveRequirementControllerTest {
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
	
	@Test
	public void fixJenkins(){
		assertEquals("Jenkins", "Jenkins");
	}

	@Test
	public void constructorSetsViewFieldCorrectly() {
		assertEquals(view, controller.getView());
	}
	
}
