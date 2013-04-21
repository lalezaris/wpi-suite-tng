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
 * Evan Polekoff
 * Sam Abradi
 */
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tasks.observers;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tasks.controller.RetrieveRequirementsTasksController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Task;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

/**
 * Take a response to a request, and pass it to the controller
 * that it was instantiated with.
 *
 * @author Sam Abradi
 * @author Evan Polkoff
 *
 * @version Apr 18, 2013
 *
 */
public class RetrieveRequirementTasksObserver implements RequestObserver{
	RetrieveRequirementsTasksController r;
	
    public RetrieveRequirementTasksObserver(RetrieveRequirementsTasksController r){
    	this.r = r;
    }
	@Override
	public void responseSuccess(IRequest iReq) {
    	// cast observable to request
		Request request = (Request) iReq;
		
		// get the response from the request
		ResponseModel response = request.getResponse();
		if ((response.getStatusCode() >= 200)&&(response.getStatusCode() < 300)) {
		    // parse the response
		    Task[] Tasks = Task.fromJSONArray(response.getBody());
		    for(int i = 0; i < Tasks.length; i++){
		    	System.out.println("Iteration id:" + Tasks[i].getId());
		    }
		    // notify the controller
		    r.received(Tasks);
		}else {
		    r.errorReceivingData("Received " + iReq.getResponse().getStatusCode() + " error from server: " + iReq.getResponse().getStatusMessage());
		}
		
	}

	@Override
	public void responseError(IRequest iReq) {
		System.out.println("hello good sir, I think there may be a problem with the response for your request to get tasks");
	}

	@Override
	public void fail(IRequest iReq, Exception exception) {
		System.out.println("hello good sir, I think there may be a failure with the response for your request to get tasks");
		
	}

}
