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
 *  Chris Hanna
**************************************************/

package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.charts.controller;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.charts.BarPieChartView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.charts.observers.IterationObserver;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * Contoller to retrieve iterations.
 * 
 * @author Chris Hanna
 *
 */
public class IterationController {

	BarPieChartView view;
	/**
	 * Construct a IterationController.
	 * 
	 * @param view A BarChartView
	 */
	public IterationController(BarPieChartView view){
		this.view = view;
	}
	/**
	 * Retrieves iterations.
	 */
	public void retreive(){
		Request request = Network.getInstance().makeRequest("iterationsmanager/iteration", HttpMethod.GET);
		request.addObserver(new IterationObserver(view));
		request.send();
	}
}