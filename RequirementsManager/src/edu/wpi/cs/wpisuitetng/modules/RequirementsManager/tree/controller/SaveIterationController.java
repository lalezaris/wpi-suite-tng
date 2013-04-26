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
 *  Arica Liu
 **************************************************/

package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tree.controller;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Iteration;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tree.observer.UpdateIterationRequestObserver;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * Controller to handle the saving of a iteration
 * Adapted from SaveDefectController in project DefectTracker
 * 
 * @author Arica Liu
 * 
 * @version April 24, 2013
 *
 */
public class SaveIterationController {
	protected Iteration iteration;

	/**
	 * Construct a new handler for the given view
	 * @param r the iteration containing the request fields
	 */
	public SaveIterationController(Iteration it) {
		iteration = it;
	}

	/**
	 * Save the view's Iteration model to the server (asynchronous).
	 */
	public void save() {
		final RequestObserver requestObserver = new UpdateIterationRequestObserver(getIteration());
		Request request;
		request = Network.getInstance().makeRequest("iterationsmanager/iteration", HttpMethod.POST);

		String JsonRequest = iteration.toJSON();
		request.setBody(JsonRequest);
		request.addObserver(requestObserver);
		request.send();

	}

	/**
	 * @return the iteration
	 */
	public Iteration getIteration() {
		return iteration;
	}
}