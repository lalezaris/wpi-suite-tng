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
 *  channamontana
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.observer;


import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Iteration;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.RetrieveAllIterationsController;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

// TODO: Auto-generated Javadoc
/**
 * An asynchronous update interface for receiving notifications
 * about RetrieveAllIterations information as the RetrieveAllIterations is constructed.
 *
 * @author Chris Hanna
 */
public class RetrieveAllIterationsObserver implements RequestObserver {

	RetrieveAllIterationsController controller;
	
	/**
	 * This method is called when information about an RetrieveAllIterations
	 * which was previously requested using an asynchronous
	 * interface becomes available.
	 *
	 * @param controller the controller
	 */
	public RetrieveAllIterationsObserver(RetrieveAllIterationsController controller){
		this.controller = controller;
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
			Iteration[] iterations = Iteration.fromJSONArray(response.getBody());

			// notify the controller
			controller.received(iterations);
		}
		else {
			//r.errorReceivingData("Received " + iReq.getResponse().getStatusCode() + " error from server: " + iReq.getResponse().getStatusMessage());
		}

	}

	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseError(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseError(IRequest iReq) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#fail(edu.wpi.cs.wpisuitetng.network.models.IRequest, java.lang.Exception)
	 */
	@Override
	public void fail(IRequest iReq, Exception exception) {
		// TODO Auto-generated method stub

	}

}
