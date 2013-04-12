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
 *  Tyler Stone
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Iteration.observer;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Iteration.controller.RetrieveIterationController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Iteration;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.RetrieveRequirementController;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

/**
 * Request Observer to retrieve a single iteration.
 *
 * @author Tyler Stone, adapted from RetrieveDefectRequestObserver
 * @version Mar 27, 2013
 */
public class RetrieveIterationRequestObserver implements RequestObserver {

	/** The retrieve iteration controller using this observer */
	protected RetrieveIterationController controller;

	/**
	 * Construct a new observer.
	 *
	 * @param controller the controller managing the request
	 */
	public RetrieveIterationRequestObserver(RetrieveIterationController controller) {
		this.controller = controller;
	}

	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseSuccess(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseSuccess(IRequest iReq) {
		// cast observable to a Request
		Request request = (Request) iReq;

		// get the response from the request
		ResponseModel response = request.getResponse();

		// check the response code of the request
		if (response.getStatusCode() != 200) {
			controller.errorRetrievingIteration("Received " + iReq.getResponse().getStatusCode() + " error from server: " + iReq.getResponse().getStatusMessage());
			return;
		}

		// parse the iteration received from the core
		Iteration[] iterations = Iteration.fromJSONArray(response.getBody());
		if (iterations.length > 0 && iterations[0] != null) {
			System.out.println("Iteration indexed by 0:"+ iterations[0]);
			controller.showIteration(iterations[0]);
		}
		else {
			controller.errorRetrievingIteration("No iteration received.");
		}
	}

	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseError(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseError(IRequest iReq) {
		controller.errorRetrievingIteration("Received " + iReq.getResponse().getStatusCode() + " error from server: " + iReq.getResponse().getStatusMessage());
	}

	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#fail(edu.wpi.cs.wpisuitetng.network.models.IRequest, java.lang.Exception)
	 */
	@Override
	public void fail(IRequest iReq, Exception exception) {
		// TODO deal with exception
		controller.errorRetrievingIteration("Unable to complete request: " + exception.getMessage());
	}
}
