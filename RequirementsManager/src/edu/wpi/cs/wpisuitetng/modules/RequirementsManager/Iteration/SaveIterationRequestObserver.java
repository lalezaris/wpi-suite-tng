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
 *  Arica Liu
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Iteration;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.Refresher;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

/**
 * A RequestObserver for a Request to update a Requirement.
 *
 * @author Arica Liu
 *
 * @version Mar 26, 2013
 *
 */
public class SaveIterationRequestObserver implements RequestObserver {

	private final IterationView view;

	/**
	 * Constructs a new UpdateIterationRequestObserver
	 * 
	 * @param view	The Iteration View that will be affected by any updates.
	 */
	public SaveIterationRequestObserver(IterationView view) {
		this.view = view;
	}

	/* Commented out part not needed for iteration 1 but may be needed in the future
	 * @param iReq the request to respond to
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseSuccess(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseSuccess(IRequest iReq) {
		// cast observable to a Request
		Request request = (Request) iReq;

		// get the response from the request
		ResponseModel response = request.getResponse();
		Refresher.getInstance().refreshIterationsFromServer(null);
		// print the body
		System.out.println("Received response: " + response.getBody()); //TODO change this to logger
		always();
	}

	/* 
	 * Responds with an error if unable to save iteration
	 * @parm iReq the IRequest to respond to
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseError(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseError(IRequest iReq) {
		JOptionPane.showMessageDialog(view, 
				"Received " + iReq.getResponse().getStatusCode() + " error from server: " + iReq.getResponse().getStatusMessage(), 
				"Save Iteration Error", JOptionPane.ERROR_MESSAGE);
		always();
	}

	/* 
	 * Displays a fail message if unable to save iteration
	 * @param iReq the request to respond to
	 * @param exception the exception that causes the failure to save iteration
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#fail(edu.wpi.cs.wpisuitetng.network.models.IRequest, java.lang.Exception)
	 */
	@Override
	public void fail(IRequest iReq, Exception exception) {
		JOptionPane.showMessageDialog(view, "Unable to complete request: " + exception.getMessage(), 
				"Save Iteration Error", JOptionPane.ERROR_MESSAGE);
		always();
	}

	/**
	 * Should always be run when an update method is called.
	 */
	private void always() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				view.setInputEnabled(true);
			}
		});
	}
}
