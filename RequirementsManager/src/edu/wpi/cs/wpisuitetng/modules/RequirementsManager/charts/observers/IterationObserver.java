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

package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.charts.observers;

import com.google.gson.GsonBuilder;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.charts.BarPieChartView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Iteration;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

/**
 * An asynchronous update interface for receiving notifications
 * about Iteration information as the Iteration is constructed.
 * 
 * @author Evan Polekoff
 * 
 */
public class IterationObserver implements RequestObserver{

	/** The view. */
	BarPieChartView view;

	/**
	 * This method is called when information about an Iteration
	 * which was previously requested using an asynchronous
	 * interface becomes available.
	 *
	 * @param view the view
	 */
	public IterationObserver(BarPieChartView view){
		this.view = view;
	}

	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseSuccess(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseSuccess(IRequest iReq) {
		Request request = (Request) iReq;
		ResponseModel response = request.getResponse();

		GsonBuilder builder = new GsonBuilder();
		Iteration[] iterations = builder.create().fromJson(response.getBody(), Iteration[].class);
		//This returns an array of all iterations except the backlog. 
		Iteration[] iterationsPlusBacklog = new Iteration[iterations.length + 1];//Make a new array one longer to hold the backlog.
		iterationsPlusBacklog[0] = Iteration.getBacklog();//Add the backlog.
		for(int i = 0; i < iterations.length; i ++)
			iterationsPlusBacklog[i+1] = iterations[i];//Iterate over the whole list and move the stuff into the new one.
		view.receiveServerIterations(iterationsPlusBacklog);//Pass this new array down.
	}

	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseError(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseError(IRequest iReq) {
	}

	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#fail(edu.wpi.cs.wpisuitetng.network.models.IRequest, java.lang.Exception)
	 */
	@Override
	public void fail(IRequest iReq, Exception exception) {}
}
