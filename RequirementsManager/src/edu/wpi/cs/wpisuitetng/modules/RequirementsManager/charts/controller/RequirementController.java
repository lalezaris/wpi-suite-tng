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

package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.charts.controller;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.charts.BarChartView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.charts.observers.RequirementObserver;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.charts.observers.UserObserver;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * Controller to retrieve requireemtns into the chart.
 * 
 * @author Ned Shelton
 *
 */
public class RequirementController {

	BarChartView view;
	/**
	 * Construct a RequirementController.
	 * 
	 * @param view A BarChartView
	 */
	public RequirementController(BarChartView view){
		this.view = view;
		
	}
	
	/**
	 * Send a request to retrieve requirements.
	 */
	public void retrieve(){
		Request request = Network.getInstance().makeRequest("requirementsmanager/requirement", HttpMethod.GET);
		request.addObserver(new RequirementObserver(view));
		request.send();
	}
	
}
