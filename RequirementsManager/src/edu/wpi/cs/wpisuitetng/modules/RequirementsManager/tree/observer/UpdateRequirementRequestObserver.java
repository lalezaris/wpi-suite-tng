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
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tree.observer;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.charts.BarPieChartView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.action.Refresher;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.action.RefresherMode;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tree.TreeView;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;


/**
 * A RequestObserver for a Request to update a Requirement.
 *
 * @author Arica Liu
 *
 * @version April 21, 2013
 *
 */
public class UpdateRequirementRequestObserver implements RequestObserver {

	private final Requirement requirement;
	// An indicator for changed field;
	// 0 for Iteration, 1 for ParentRequirementID.
	protected int changedField;

	/**
	 * Constructs a new UpdateRequirementRequestObserver.
	 *
	 * @param view The TreeView that will be affected by any updates.
	 */
	public UpdateRequirementRequestObserver(Requirement r, int c) {
		this.requirement = r;
		this.changedField = c;
	}

	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseSuccess(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseSuccess(IRequest iReq) {
		BarPieChartView.update();
		// cast observable to a Request
		Request request = (Request) iReq;

		// get the response from the request
		ResponseModel response = request.getResponse();
		
		TreeView.getInstance().refreshTree();

		// print the body
		if (response.getStatusCode() == 200 || response.getStatusCode() == 201) {
			// parse the Requirement from the body
			final Requirement requirement = Requirement.fromJSON(response.getBody());

			Refresher.getInstance().refreshRequirementsFromServer(RefresherMode.ALL);
			// make sure the Requirement isn't null
			if (requirement != null) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						//Nothing to do on update
					}
				});
			}
			else {
				JOptionPane.showMessageDialog(null, "Unable to parse requirement received from server.", 
						"Save Requirement Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		else {
			JOptionPane.showMessageDialog(null, 
					"Received " + iReq.getResponse().getStatusCode() + " error from server: " + iReq.getResponse().getStatusMessage(), 
					"Save Requirement Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseError(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseError(IRequest iReq) {
		JOptionPane.showMessageDialog(null, 
				"Received " + iReq.getResponse().getStatusCode() + " error from server: " + iReq.getResponse().getStatusMessage(), 
				"Save Requirement Error", JOptionPane.ERROR_MESSAGE);
	}

	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#fail(edu.wpi.cs.wpisuitetng.network.models.IRequest, java.lang.Exception)
	 */
	@Override
	public void fail(IRequest iReq, Exception exception) {
		JOptionPane.showMessageDialog(null, "Unable to complete request: " + exception.getMessage(), 
				"Save Requirement Error", JOptionPane.ERROR_MESSAGE);
	}

	public Requirement getRequirement() {
		return requirement;
	}
}