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

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.charts.BarChartView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementPanel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.action.Refresher;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.action.RefresherMode;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.BatchRequirementEditController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.BatchRequirementEditController.ChangeField;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

/**
 * A RequestObserver for a Request to update a Requirement.
 *
 * @author Tyler Stone
 *
 * @version Mar 20, 2013
 *
 */
/**
 * A RequestObserver for a Request to update a Requirement.
 */
public class UpdateRequirementRequestObserver implements RequestObserver {

	private final RequirementView view;

	/**
	 * Constructs a new UpdateRequirementRequestObserver.
	 *
	 * @param view The RequirementView that will be affected by any updates.
	 */
	public UpdateRequirementRequestObserver(RequirementView view) {
		this.view = view;
	}

	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseSuccess(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseSuccess(IRequest iReq) {
		BarChartView.update();
		// cast observable to a Request
		Request request = (Request) iReq;

		// get the response from the request
		ResponseModel response = request.getResponse();

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
						Requirement unchangedModel = ((RequirementPanel) view.getRequirementPanel()).getUneditedModel();
						Requirement changedModel = ((RequirementPanel) view.getRequirementPanel()).getEditedModel();

						/* Great! the requirement was updated! 
						 * Now we check if the iterationID was changed.
						 * 
						 * If so, update all children
						 */
						
						if (unchangedModel.getIterationId() != changedModel.getIterationId()) {
							BatchRequirementEditController<Integer> batchController = 
									new BatchRequirementEditController<Integer>(ChangeField.ITERATIONID, changedModel.getIterationId());
							//change all children
							batchController.instantiateChange(changedModel.getChildRequirementIds());
						}
						//						
						//						((RequirementPanel) view.getRequirementPanel()).updateModel(requirement);
						//						view.setEditModeDescriptors(requirement);
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

	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseError(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseError(IRequest iReq) {
		JOptionPane.showMessageDialog(view, 
				"Received " + iReq.getResponse().getStatusCode() + " error from server: " + iReq.getResponse().getStatusMessage(), 
				"Save Requirement Error", JOptionPane.ERROR_MESSAGE);
		always();
	}

	/* (non-Javadoc)
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
			@Override
			public void run() {
				view.setInputEnabled(true);
			}
		});
	}
}
