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
 * Michael French
 */
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.observer;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.RetrieveAllRequirementsController;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

/**
 * Takes a response to a request, and passes it to the controller that it was instantiated with.
 *
 * @author Sam Abradi
 *
 * @version Mar 21, 2013
 *
 */
public class RetrieveAllRequirementsRequestObserver implements RequestObserver{
	RetrieveAllRequirementsController r;
	
	/**
	 * This method is called when information about an RetrieveAllRequirementsRequest
	 * which was previously requested using an asynchronous
	 * interface becomes available.
	 *
	 * @param r the requirement
	 */
	public RetrieveAllRequirementsRequestObserver(RetrieveAllRequirementsController r){
		this.r = r;
	}

	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseSuccess(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseSuccess(IRequest iReq) {
		// cast observable to request
		Request request = (Request) iReq;

		// get the response from the request
		ResponseModel response = request.getResponse();
		if ((response.getStatusCode() >= 200)&&(response.getStatusCode() < 300)) {
			// parse the response				
			Requirement[] requirements = Requirement.fromJSONArray(response.getBody());

			// notify the controller
			r.receivedData(requirements);
		}
		else {
			r.errorReceivingData("Received " + iReq.getResponse().getStatusCode() + " error from server: " + iReq.getResponse().getStatusMessage());
		}
	}

	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseError(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseError(IRequest iReq) {
		System.out.print("Response Error: " + iReq.getBody() + "  ::  " + iReq.toString() );
		
	}

	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#fail(edu.wpi.cs.wpisuitetng.network.models.IRequest, java.lang.Exception)
	 */
	@Override
	public void fail(IRequest iReq, Exception exception) {
		
	}
}

