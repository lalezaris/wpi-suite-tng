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
 *  Ned Shelton
 **************************************************/

package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Iteration.controller;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Iteration.IterationPanel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Iteration.observer.AllRequirementObserver;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;


/**
 * Controller to retrieve requirements into the chart.
 * 
 * @author Ned Shelton
 *
 */
public class AllRequirementController {

	IterationPanel panel;
	/**
	 * Construct an AllRequirementController.
	 * 
	 * @param view An IterationPanel
	 */
	public AllRequirementController(IterationPanel panel){
		this.panel = panel;

	}

	/**
	 * Send a request to retrieve requirements.
	 */
	public void retrieve(){
		Request request = Network.getInstance().makeRequest("requirementsmanager/requirement", HttpMethod.GET);
		request.addObserver(new AllRequirementObserver(panel));
		request.send();
	}
}