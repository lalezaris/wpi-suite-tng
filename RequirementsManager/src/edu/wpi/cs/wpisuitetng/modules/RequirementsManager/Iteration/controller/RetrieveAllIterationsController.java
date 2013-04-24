/**
 * This file was developed for CS3733: Software Engineering
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Sam Abradi
 */

package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Iteration.controller;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Iteration.IterationView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Iteration.observer.RetrieveAllIterationsRequestObserver;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Iteration;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.action.Refresher;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * Retrieve all the iterations from the database.
 *
 * @author Sam Abradi
 *
 * @version Mar 21, 2013
 *
 */
public class RetrieveAllIterationsController {
	/** The search Iterations view */
	protected IterationView view;
	
	/**The Iterations data retrieved from the server*/
	protected Iteration[] data = null;

    /**
     * This method is called by the {@link RetrieveAllIterationsRequestObserver} when the
     * response is received.
     *
     * @param Iterations An array of Iterations returned by the server
     */
    public void receivedData(Iteration[] Iterations) {
    	if (Iterations.length > 0) {
    		// save the data
    		data = Iterations;
    		
    		Refresher.getInstance().refreshIterations(Iterations, view);
    	}
    }
	/**
	 * Construct a new RetrieveAllIterationsController
	 *
	 * @param view the search Iterations view
	 */
	public RetrieveAllIterationsController(IterationView view) {
		this.view = view;
	}

	/**
	 * Send a request for all of the Iterations
	 */
	public void refreshData() {
		final RequestObserver requestObserver = new RetrieveAllIterationsRequestObserver(this);
		Request request;
		request = Network.getInstance().makeRequest("iterationsmanager/iteration",  HttpMethod.GET);
		request.addObserver(requestObserver);
		request.send();
	}

	/*
	 * TODO: proper implementation
	 * 
	 * @param string
	 */
	public void errorReceivingData(String string) {

	}
}