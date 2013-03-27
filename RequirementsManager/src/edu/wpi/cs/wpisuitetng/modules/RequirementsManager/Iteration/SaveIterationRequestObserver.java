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

import javax.swing.SwingUtilities;
import javax.swing.JOptionPane;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

/**
 * A RequestObserver for a Request to update a Iteration.
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
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseSuccess(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseSuccess(IRequest iReq) {
		// cast observable to a Request
		Request request = (Request) iReq;

		// get the response from the request
		ResponseModel response = request.getResponse();

		// print the body
		System.out.println("Received response: " + response.getBody()); //TODO change this to logger
		/*if (response.getStatusCode() == 200) {
			// parse the iteration from the body
			final Iteration iteration = Iteration.fromJSON(response.getBody());

			// make sure the iteration isn't null
			if (iteration != null) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						//((IterationPanel) view.getIterationPanel()).updateModel(Iteration);
						//view.setEditModeDescriptors(iteration);
					}
				});
			}
			else { */
//		JOptionPane.showMessageDialog(view, "Successfully saved new iteration to server.", 
//			"Success!", JOptionPane.INFORMATION_MESSAGE);
			/*}
		}
		else {
			JOptionPane.showMessageDialog(view, 
					"Received " + iReq.getResponse().getStatusCode() + " error from server: " + iReq.getResponse().getStatusMessage(), 
					"Save Iteration Error", JOptionPane.ERROR_MESSAGE);
		} */

		always();
	}

	/* 
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
