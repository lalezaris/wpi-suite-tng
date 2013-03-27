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
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tree;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Iteration;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

/**
 * Takes a response to a request, and passes it to the controller that it was instantiated with
 *
 * @author Sam Abradi
 *
 * @version Mar 21, 2013
 *
 */
public class RetrieveAllIterationsRequestObserverTree implements RequestObserver{
    RetrieveAllIterationsControllerTree r;
    public RetrieveAllIterationsRequestObserverTree(RetrieveAllIterationsControllerTree r){
	this.r = r;
    }
    
    @Override
	public void responseSuccess(IRequest iReq) {
	// cast observable to request
	Request request = (Request) iReq;
	
	// get the response from the request
	ResponseModel response = request.getResponse();
	System.out.println("GOT RESPONSE");
	if ((response.getStatusCode() >= 200)&&(response.getStatusCode() < 300)) {
	    // parse the response
	    Iteration[] Iterations = Iteration.fromJSONArray(response.getBody());
	    
	    // notify the controller
	    r.receivedData(Iterations);
	}
	else {
	    r.errorReceivingData("Received " + iReq.getResponse().getStatusCode() + " error from server: " + iReq.getResponse().getStatusMessage());
	}
    }
    
    @Override
	public void responseError(IRequest iReq) {
	// TODO Auto-generated method stub
	System.out.print("Response Error");
	
    }
    
    @Override
	public void fail(IRequest iReq, Exception exception) {
	// TODO Auto-generated method stub
	
    }
}
