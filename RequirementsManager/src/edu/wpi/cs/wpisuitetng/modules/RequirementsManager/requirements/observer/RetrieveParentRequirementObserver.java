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
 *  Tyler Stone
 **************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.observer;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.charts.BarPieChartView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.action.Refresher;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.action.RefresherMode;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

/**
 * Observer for retrieving a parent requirement from the server
 *
 * @author Tyler Stone
 *
 * @version Apr 16, 2013
 *
 */
public class RetrieveParentRequirementObserver implements RequestObserver {
	private final RequirementView view;

	/**
	 * Constructs a new RetrieveParentRequirementObserver.
	 *
	 * @param view The RequirementView that will be affected by any updates.
	 */
	public RetrieveParentRequirementObserver(RequirementView view) {
		this.view = view;
	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseSuccess(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseSuccess(IRequest iReq) {
		BarPieChartView.update();
		// cast observable to a Request
		Request request = (Request) iReq;

		// get the response from the request
		ResponseModel response = request.getResponse();

		// print the body
		if (response.getStatusCode() == 200 || response.getStatusCode() == 201) {
			// parse the Requirement from the body
			final Requirement[] requirements = Requirement.fromJSONArray(response.getBody());

			Refresher.getInstance().refreshRequirementsFromServer(RefresherMode.ALL);
			// make sure the Requirement isn't null
			if (requirements[0] != null) {
				SwingUtilities.invokeLater(new Runnable() {
					/**
					 * @see java.lang.Runnable#run()
					 */
					@Override
					public void run() {
						view.setParentRequirement(requirements[0]);
						view.setIterationComboBox();
					}
				});
			}
			else {
				JOptionPane.showMessageDialog(view, "Unable to parse requirement received from server.", 
						"Save Requirement Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		else {
			JOptionPane.showMessageDialog(view, 
					"Received " + iReq.getResponse().getStatusCode() + " error from server: " + iReq.getResponse().getStatusMessage(), 
					"Save Requirement Error", JOptionPane.ERROR_MESSAGE);
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
				"Save Requirement Error", JOptionPane.ERROR_MESSAGE);
		always();
	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#fail(edu.wpi.cs.wpisuitetng.network.models.IRequest, java.lang.Exception)
	 */
	@Override
	public void fail(IRequest iReq, Exception exception) {
		JOptionPane.showMessageDialog(view, "Unable to complete request: " + exception.getMessage(), 
				"Save Requirement Error", JOptionPane.ERROR_MESSAGE);
		always();
	}

	/**
	 * Should always be run when an update method is called.
	 */
	private void always() {
		SwingUtilities.invokeLater(new Runnable() {
			/**
			 * @see java.lang.Runnable#run()
			 */
			@Override
			public void run() {
				view.setInputEnabled(true);
			}
		});
	}
}
