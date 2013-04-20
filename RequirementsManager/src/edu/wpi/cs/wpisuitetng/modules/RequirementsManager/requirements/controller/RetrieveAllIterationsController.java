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
 *  chrissy-hanny-boo-boo
 ***************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Iteration;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.observer.RetrieveAllIterationsObserver;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * Controller to retrieve all iterations from a requirement view.
 * 
 * @author Chris Hanna
 *
 */
public class RetrieveAllIterationsController {

	RequirementView view;
	
	/**
	 * Instantiates a new retrieve all iterations controller.
	 *
	 * @param view the view
	 */
	public RetrieveAllIterationsController(RequirementView view){
		this.view = view;
	}
	
	/**
	 * Retrieve iterations from <code>view</code>
	 */
	public void retrieve(){
		final RequestObserver requestObserver = new RetrieveAllIterationsObserver(this);
		Request request;
		request = Network.getInstance().makeRequest("iterationsmanager/iteration", HttpMethod.GET);
		request.addObserver(requestObserver);
		request.send();
	}
	
	/**
	 * Changes iteration combo box for <code>view</code>.
	 *
	 * @param iterations the iterations to added to the combo box.
	 */
	public void received(Iteration[] iterations){
		view.setAvailableIterations(iterations);
		view.setIterationComboBox();
	}
}
