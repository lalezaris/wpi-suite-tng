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
 *  Evan Polekoff
 **************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Iteration.observer;

import com.google.gson.GsonBuilder;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Iteration.IterationPanel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Iteration;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;


/**
 * An asynchronous update interface for receiving notifications
 * about Requirement information as the Requirement is constructed.
 * 
 * @author Evan Polekoff
 */
public class AllRequirementObserver implements RequestObserver{

	/** The view. */
	IterationPanel panel;

	/**
	 * This method is called when information about an Requirement
	 * which was previously requested using an asynchronous
	 * interface becomes available.
	 *
	 * @param panel the panel
	 */
	public AllRequirementObserver(IterationPanel panel){
		this.panel = panel;
	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseSuccess(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseSuccess(IRequest iReq) {
		Request request = (Request) iReq;
		ResponseModel response = request.getResponse();

		GsonBuilder builder = new GsonBuilder();
		Requirement[] reqs = builder.create().fromJson(response.getBody(), Requirement[].class);
		for (int i = 0 ; i < reqs.length; i ++){
			reqs[i].setIteration(Iteration.getIterationById(reqs[i].getIterationId()));
		}

		this.panel.receiveServerRequirements(reqs);
		this.panel.getTxtEstimate().setText("" + this.panel.calculateTxtEstimate(reqs));
		this.panel.repaint();
	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseError(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseError(IRequest iReq) {		
	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#fail(edu.wpi.cs.wpisuitetng.network.models.IRequest, java.lang.Exception)
	 */
	@Override
	public void fail(IRequest iReq, Exception exception) {		
	}
}