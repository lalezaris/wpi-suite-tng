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
 *  Tyler
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements;

/**
 * Insert Description Here
 *
 * @author Tyler
 *
 * @version Mar 20, 2013
 *
 */

import javax.swing.SwingUtilities;
import javax.swing.JOptionPane;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

/**
 * A RequestObserver for a Request to update a Defect.
 */
public class UpdateRequirementRequestObserver implements RequestObserver {

	private final RequirementView view;

	/**
	 * Constructs a new UpdateDefectRequestObserver
	 * 
	 * @param view	The DefectView that will be affected by any updates.
	 */
	public UpdateRequirementRequestObserver(RequirementView view) {
		this.view = view;
	}

	@Override
	public void responseSuccess(IRequest iReq) {
		// cast observable to a Request
		Request request = (Request) iReq;

		// get the response from the request
		ResponseModel response = request.getResponse();

		// print the body
		System.out.println("Received response: " + response.getBody()); //TODO change this to logger
		/*if (response.getStatusCode() == 200) {
			// parse the defect from the body
			final Requirement defect = Requirement.fromJSON(response.getBody());

			// make sure the defect isn't null
			if (defect != null) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						//((RequirementPanel) view.getDefectPanel()).updateModel(defect);
						//view.setEditModeDescriptors(defect);
					}
				});
			}
			else { */
		JOptionPane.showMessageDialog(view, "Successfully saved new requirement to server.", 
			"Success!", JOptionPane.INFORMATION_MESSAGE);
			/*}
		}
		else {
			JOptionPane.showMessageDialog(view, 
					"Received " + iReq.getResponse().getStatusCode() + " error from server: " + iReq.getResponse().getStatusMessage(), 
					"Save Defect Error", JOptionPane.ERROR_MESSAGE);
		} */

		always();
	}

	@Override
	public void responseError(IRequest iReq) {
		JOptionPane.showMessageDialog(view, 
				"Received " + iReq.getResponse().getStatusCode() + " error from server: " + iReq.getResponse().getStatusMessage(), 
				"Save Defect Error", JOptionPane.ERROR_MESSAGE);
		always();
	}

	@Override
	public void fail(IRequest iReq, Exception exception) {
		JOptionPane.showMessageDialog(view, "Unable to complete request: " + exception.getMessage(), 
				"Save Defect Error", JOptionPane.ERROR_MESSAGE);
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
