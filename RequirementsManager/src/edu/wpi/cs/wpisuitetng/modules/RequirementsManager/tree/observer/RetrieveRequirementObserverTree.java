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
 * Chris Hanna
 */
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tree.observer;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tree.controller.RetrieveRequirementControllerTree;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

/**
 * Observer to retrieve requirements tree.
 *
 * @param <T> the generic type
 * @author Chris Hanna
 * @version March 28, 2013
 */

public class RetrieveRequirementObserverTree<T> implements RequestObserver{

	private RetrieveRequirementControllerTree<T> controller;
	
	/**
	 * Instantiates a new retrieve requirement observer tree.
	 *
	 * @param controller the controller
	 */
	public RetrieveRequirementObserverTree(RetrieveRequirementControllerTree<T> controller){
		this.controller = controller;
	}
	
	/**
	 * Response to a request.
	 *
	 * @param iReq IRequest to respond to
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
			controller.errorRetrievingRequirement("Received " + iReq.getResponse().getStatusCode() + " error from server: " + iReq.getResponse().getStatusMessage());
			return;
		}

		// parse the defect received from the core
		String body = response.getBody();
		if ( body != null) {
			controller.recieveData(body);
		}
		else {
			controller.errorRetrievingRequirement("No requirement received.");
		}
	}

	/**
	 * Responds to an error.
	 *
	 * @param iReq IRequest to respond with an error to
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseError(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseError(IRequest iReq) {
		//controller.errorRetrievingRequirement("Received " + iReq.getResponse().getStatusCode() + " error from server: " + iReq.getResponse().getStatusMessage());
	}

	/**
	 * Responds to a failure.
	 *
	 * @param iReq IRequest to respond to a failure
	 * @param exception exception causing the failure
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#fail(edu.wpi.cs.wpisuitetng.network.models.IRequest, java.lang.Exception)
	 */
	@Override
	public void fail(IRequest iReq, Exception exception) {
		// TODO deal with exception
		//controller.errorRetrievingRequirement("Unable to complete request: " + exception.getMessage());
	}
}