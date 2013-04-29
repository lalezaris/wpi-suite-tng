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
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Iteration.observer;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Iteration.IterationView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.charts.BarPieChartView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Iteration;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.action.Refresher;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tree.TreeView;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

/**
 * A RequestObserver for a Request to update a Iteration.
 *
 * @author Arica Liu
 *
 * @version April 9th, 2013
 *
 */

public class UpdateIterationRequestObserver implements RequestObserver {

	private final IterationView view;

	/**
	 * Constructs a new UpdateIterationRequestObserver.
	 * 
	 * @param view	The IterationView that will be affected by any updates
	 */
	public UpdateIterationRequestObserver(IterationView view) {
		this.view = view;
	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseSuccess(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseSuccess(IRequest iReq) {
		// cast observable to a Request
		Request request = (Request) iReq;

		// get the response from the request
		ResponseModel response = request.getResponse();
		TreeView.getInstance().refreshTree();
		BarPieChartView.update();
		// print the body
		if (response.getStatusCode() == 200 || response.getStatusCode() == 201) {
			// parse the Iteration from the body
			final Iteration iteration = Iteration.fromJSON(response.getBody());

			Refresher.getInstance().refreshIterationsFromServer(view);
			// make sure the Iteration isn't null
			if (iteration != null) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						view.getIterationModel().updateModel(iteration);
						view.setEditModeDescriptors(iteration);
					}
				});
			}
			else {
				JOptionPane.showMessageDialog(view, "Unable to parse iteration received from server.", 
						"Save Iteration Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		else {
			JOptionPane.showMessageDialog(view, 
					"Received " + iReq.getResponse().getStatusCode() + " error from server: " + iReq.getResponse().getStatusMessage(), 
					"Save Iteration Error", JOptionPane.ERROR_MESSAGE);
		}

		always();
	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseError(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseError(IRequest iReq) {
		JOptionPane.showMessageDialog(view, 
				"Received " + iReq.getResponse().getStatusCode() + " error from server: " + iReq.getResponse().getStatusMessage(), 
				"Save Iteration Error", JOptionPane.ERROR_MESSAGE);
		always();
	}

	/**
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