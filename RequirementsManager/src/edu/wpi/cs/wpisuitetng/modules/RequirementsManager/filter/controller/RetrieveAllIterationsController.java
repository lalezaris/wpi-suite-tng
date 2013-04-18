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
 *  Chris
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.filter.controller;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.charts.observers.IterationObserver;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.filter.FilterController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Iteration;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * Insert Description Here
 *
 * @author Chris Hanna
 *
 * @version Apr 17, 2013
 *
 */
public class RetrieveAllIterationsController {

	private FilterController controller;
	/**
	 * Enter Description here.
	 * 
	 */
	public RetrieveAllIterationsController(FilterController controller) {
		this.controller = controller;
		
	}

	public void retrieve(){
		Request request = Network.getInstance().makeRequest("iterationsmanager/iteration", HttpMethod.GET);
		request.addObserver(new RetrieveAllIterationsObserver(this));
		request.send();
	}
	
	public void response(Iteration[] iterations){
		this.controller.setIterations(iterations);
	}
	
}
