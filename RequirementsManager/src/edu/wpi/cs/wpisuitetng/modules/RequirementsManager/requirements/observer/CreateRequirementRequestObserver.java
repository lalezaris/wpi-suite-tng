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
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.observer;

/**
 * The request observer for a create request to the server
 *
 * @author Tyler, adapted from Defect Tracker
 *
 * @version Mar 24, 2013
 *
 */
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementPanel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementPanel.Mode;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.action.Refresher;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.action.RefresherMode;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

/**
 * An Observer for a Request to create a Requirement.
 */
public class CreateRequirementRequestObserver implements RequestObserver {

	private final RequirementView view;

	/**
	 * Constructs a new CreateRequirementRequestObserver
	 * 
	 * @param view	The RequirementView that will be affected by any updates.
	 */
	public CreateRequirementRequestObserver(RequirementView view) {
		this.view = view;
	}

	/**
	 * @param iReq the request
	 */
	@Override
	public void responseSuccess(IRequest iReq) {
		// cast observable to a Request
		Request request = (Request) iReq;

		// get the response from the request
		ResponseModel response = request.getResponse();


		// print the body
		System.out.println("Received response: " + response.getBody()); //TODO change this to logger

		if (response.getStatusCode() == 201) {
			// parse the Requirement from the body
			final Requirement requirement = Requirement.fromJSON(response.getBody());
			Refresher.getInstance().refreshRequirementsFromServer(RefresherMode.ALL);
			// make sure the Requirement isn't null
			if (requirement != null) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						((RequirementPanel) view.getRequirementPanel()).updateModel(requirement);
						view.setEditModeDescriptors(requirement);

						//to deal with child Requirements
						//we need to update their parent Requirement
						RequirementView parentView = view.getParentView();
						if(parentView != null){
							//System.out.println("\n\n\n\n\nWe have a not-null parent view!\n\n\n\n");
							RequirementPanel parentPanel = (RequirementPanel) parentView.getRequirementPanel();

							//get the EDITED model currently displayed, and just add the child to it
							parentPanel.getModel().addChildRequirement(requirement.getId());

							/*next get the UNEDITED model and save that to Database with the child
							 * this ensures that the child is added in both places,
							 * and doesn't require the parent Requirement to be explicitly
							 * saved again by the user if they don't want to
							 */
							Requirement uneditedParent = parentPanel.getUneditedModel();
							Requirement uneditedParentWithChild = uneditedParent;
							uneditedParentWithChild.addChildRequirement(requirement.getId());

							//now to save the uneditedPanelWithChild to database
							String JsonRequest = uneditedParentWithChild.toJSON();
							final RequestObserver requestObserver = new UpdateRequirementRequestObserver(parentView);
							//The line below was Tushar testing something. The parentPanel edit mode should never be CREATE!
							//final RequestObserver requestObserver = (parentPanel.getEditMode() == Mode.CREATE) ? new CreateRequirementRequestObserver(view) : new UpdateRequirementRequestObserver(view);
							Request request;
							request = Network.getInstance().makeRequest("requirementsmanager/requirement", HttpMethod.POST);
							request.setBody(JsonRequest);
							System.out.println("Sending REQ to server:" +JsonRequest );
							request.addObserver(requestObserver);
							request.send();
						}
					}
				});
			}
			else {
				JOptionPane.showMessageDialog(view,	"Unable to parse defect received from server.", 
						"Save Requirement Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		else {
			JOptionPane.showMessageDialog(view, 
					"Received " + iReq.getResponse().getStatusCode() + " status from server: " + iReq.getResponse().getStatusMessage(), 
					"Save Requirement Error", JOptionPane.ERROR_MESSAGE);
		}

		always();
	}

	@Override
	public void responseError(IRequest iReq) {
		System.out.println("Error: " + iReq.getResponse().getBody());
		JOptionPane.showMessageDialog(view, 
				"Received " + iReq.getResponse().getStatusCode() + " error from server: " + iReq.getResponse().getStatusMessage(), 
				"Save Requirement Error", JOptionPane.ERROR_MESSAGE);
		always();
	}

	@Override
	public void fail(IRequest iReq, Exception exception) {
		System.out.println("Fail: " + iReq.getResponse().getBody());
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
