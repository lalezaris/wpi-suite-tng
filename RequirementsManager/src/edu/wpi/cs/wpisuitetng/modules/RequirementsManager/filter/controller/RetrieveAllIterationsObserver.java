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
 *  Chris
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.filter.controller;

import com.google.gson.GsonBuilder;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Iteration;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

/**
 * Iterations Observer
 *
 * @author Chris
 *
 * @version Apr 17, 2013
 *
 */
public class RetrieveAllIterationsObserver implements RequestObserver{

	RetrieveAllIterationsController controller;
	/**
	 * Create an observer to catch an iteration list
	 * @param controller 
	 */
	public RetrieveAllIterationsObserver(RetrieveAllIterationsController controller) {
		this.controller = controller;
	}
	@Override
	public void responseSuccess(IRequest iReq) {
		Request request = (Request) iReq;
		ResponseModel response = request.getResponse();
		
		GsonBuilder builder = new GsonBuilder();
		Iteration[] iterations = builder.create().fromJson(response.getBody(), Iteration[].class);
		//this.panel.setAllusers(users);
		controller.response(iterations);
		
	}
	@Override
	public void responseError(IRequest iReq) {
		
		
	}
	@Override
	public void fail(IRequest iReq, Exception exception) {
		
		
	}

}
