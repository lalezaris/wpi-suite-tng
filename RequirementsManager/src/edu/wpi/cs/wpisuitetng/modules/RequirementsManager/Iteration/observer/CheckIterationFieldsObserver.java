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
 *  CDUNKERS
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Iteration.observer;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Iteration.IterationPanel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Iteration.IterationView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Iteration.controller.SaveIterationController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Iteration.IterationPanel.Mode;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Iteration;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

/**
 * The observer which will save a iteration if all of the fields are correct and response a sucess
 *
 * @author Chris Dunkers
 *
 * @version Apr 13, 2013
 *
 */
public class CheckIterationFieldsObserver implements RequestObserver {

	/** The View using the information obtained*/
	protected IterationView iterationView;

	/**
	 * Construct a new observer.
	 *
	 * @param controller the controller managing the request
	 */
	public CheckIterationFieldsObserver(IterationView iterationView) {
		this.iterationView = iterationView;
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
			iterationView.errorReceivingIterations("Received " + iReq.getResponse().getStatusCode() + " error from server: " + iReq.getResponse().getStatusMessage());
			return;
		}

		// parse the iteration received from the core
		Iteration[] iterations = Iteration.fromJSONArray(response.getBody());
//		if (iterations.length > 0 && iterations[0] != null) {
//			System.out.println("Iteration indexed by 0:"+ iterations[0]);
//		}
//		else {
//			iterationView.errorReceivingIterations("No iteration received.");
//		}
		
		final IterationPanel panel = (IterationPanel) iterationView.getIterationPanel();
		final RequestObserver requestObserver = (panel.getEditMode() == Mode.CREATE) ? new CreateIterationRequestObserver(iterationView) : new UpdateIterationRequestObserver(iterationView);
		Request requestSave;
		requestSave = Network.getInstance().makeRequest("iterationsmanager/iteration", (panel.getEditMode() == Mode.CREATE) ? HttpMethod.PUT : HttpMethod.POST);

		if(iterationView.checkRequiredFields(iterations) == 0){
			String JsonRequest = iterationView.getIterationModel().getEditedModel().toJSON();
			requestSave.setBody(JsonRequest);
			System.out.println("Sending REQ to server:" +JsonRequest );
			requestSave.addObserver(requestObserver);
			requestSave.send();
			//close tab
			iterationView.getTab().getView().removeTabAt(iterationView.getTab().getThisIndex());
			System.out.println("SAVE ITERATION");
		}
	}

	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseError(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseError(IRequest iReq) {
		iterationView.errorReceivingIterations("Received " + iReq.getResponse().getStatusCode() + " error from server: " + iReq.getResponse().getStatusMessage());
	}

	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#fail(edu.wpi.cs.wpisuitetng.network.models.IRequest, java.lang.Exception)
	 */
	@Override
	public void fail(IRequest iReq, Exception exception) {
		// TODO deal with exception
		iterationView.errorReceivingIterations("Unable to complete request: " + exception.getMessage());
	}
	
	
}
