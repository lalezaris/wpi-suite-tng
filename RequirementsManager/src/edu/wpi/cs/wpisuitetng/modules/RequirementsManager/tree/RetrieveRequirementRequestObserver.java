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
 *  Tyler
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tree;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

/**
 * Request Observer to retrieve a single requirement
 *
 * @author Tyler, adapted from RetrieveDefectRequestObserver
 *
 * @version Mar 27, 2013
 *
 */
public class RetrieveRequirementRequestObserver implements RequestObserver {

	/** The retrieve defect controller using this observer */
	protected RetrieveRequirementController controller;

	/**
	 * Construct a new observer
	 * @param controller the controller managing the request
	 */
	public RetrieveRequirementRequestObserver(RetrieveRequirementController controller) {
		this.controller = controller;
	}

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
		Requirement[] requirements = Requirement.fromJSONArray(response.getBody());
		if (requirements.length > 0 && requirements[0] != null) {
			controller.showRequirement(requirements[0]);
		}
		else {
			controller.errorRetrievingRequirement("No requirement received.");
		}
	}

	@Override
	public void responseError(IRequest iReq) {
		controller.errorRetrievingRequirement("Received " + iReq.getResponse().getStatusCode() + " error from server: " + iReq.getResponse().getStatusMessage());
	}

	@Override
	public void fail(IRequest iReq, Exception exception) {
		// TODO deal with exception
		controller.errorRetrievingRequirement("Unable to complete request: " + exception.getMessage());
	}
}
