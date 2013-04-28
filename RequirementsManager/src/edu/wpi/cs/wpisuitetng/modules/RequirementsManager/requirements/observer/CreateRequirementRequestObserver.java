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

/**
 * The request observer for a create request to the server.
 *
 * @author Tyler Stone, adapted from Defect Tracker
 *
 * @version Mar 24, 2013
 *
 */
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.History.HistoricalChange;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.charts.BarPieChartView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementPanel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.action.Refresher;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.action.RefresherMode;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.UpdateRequirementViewController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tree.TreeView;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

/**
 * An Observer for a Request to create a Requirement.
 * 
 * @author Tyler Stone
 */
public class CreateRequirementRequestObserver implements RequestObserver {

	private final RequirementView view;

	/**
	 * Constructs a new CreateRequirementRequestObserver.
	 *
	 * @param view The RequirementView that will be affected by any updates.
	 */
	public CreateRequirementRequestObserver(RequirementView view) {
		this.view = view;
	}

	/**
	 * This method is called when information about an CreateRequirementRequest
	 * which was previously requested using an asynchronous
	 * interface becomes available.
	 *
	 * @param iReq the request
	 */
	@Override
	public void responseSuccess(IRequest iReq) {
		// cast observable to a Request
		Request request = (Request) iReq;

		// get the response from the request
		ResponseModel response = request.getResponse();
		TreeView.getInstance().refreshTree();

		if (response.getStatusCode() == 201) {
			BarPieChartView.update();

			// parse the Requirement from the body
			final Requirement requirement = Requirement.fromJSON(response.getBody());
			Refresher.getInstance().refreshRequirementsFromServer(RefresherMode.ALL);

			// update all open Requirement Views
			UpdateRequirementViewController.updateAllOpenRequirementViews();

			// make sure the Requirement isn't null
			if (requirement != null) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						view.setEditModeDescriptors(requirement);

						//child Requirements: we need to update their parent Requirement
						RequirementView parentView = view.getParentView();
						if(parentView != null){
							RequirementPanel parentPanel = (RequirementPanel) parentView.getRequirementPanel();
							parentPanel.setDeleteEnabled(false);

							/*next get the UNEDITED model and save that to Database with the child
							 * this ensures that the child is added in both places,
							 * and doesn't require the parent Requirement to be explicitly
							 * saved again by the user if they don't want to
							 */
							Requirement uneditedParent = parentView.getReqModel().getUneditedRequirement();

							Requirement uneditedParentWithChild = new Requirement(
									uneditedParent.getId(), 
									uneditedParent.getTitle(), 
									uneditedParent.getDescription(), 
									uneditedParent.getCreator());//uneditedParent;
							uneditedParentWithChild.setType(uneditedParent.getType());
							uneditedParentWithChild.setReleaseNumber(uneditedParent.getReleaseNumber());
							uneditedParentWithChild.setIterationId(uneditedParent.getIterationId());
							uneditedParentWithChild.setIteration(uneditedParent.getIteration());
							uneditedParentWithChild.setStatus(uneditedParent.getStatus());
							uneditedParentWithChild.setPriority(uneditedParent.getPriority());
							uneditedParentWithChild.setEstimateEffort(uneditedParent.getEstimateEffort());
							uneditedParentWithChild.setActualEffort(uneditedParent.getActualEffort());
							uneditedParentWithChild.setCreationDate(uneditedParent.getCreationDate());
							uneditedParentWithChild.setLastModifiedDate(uneditedParent.getLastModifiedDate());
							uneditedParentWithChild.setParentRequirementId(uneditedParent.getParentRequirementId());
							uneditedParentWithChild.setId(uneditedParent.getId());
							uneditedParentWithChild.setCreator(uneditedParent.getCreator());
							uneditedParentWithChild.setAssignee(uneditedParent.getAssignee());
							uneditedParentWithChild.updateNotes(uneditedParent.getNotes());
							for (int i = 0; i < uneditedParent.getHistory().size(); i++){
								uneditedParentWithChild.addHistoricalChange(uneditedParent.getHistory().get(i));
							}
							uneditedParentWithChild.updateAcceptanceTests(uneditedParent.getAcceptanceTests());
							uneditedParentWithChild.updateTasks(uneditedParent.getTasks());
							for (int i = 0; i < uneditedParent.getChildRequirementIds().size(); i++){
								uneditedParentWithChild.addChildRequirement(uneditedParent.getChildRequirementIds().get(i));
							}

							uneditedParentWithChild.addChildRequirement(requirement.getId());
							HistoricalChange change = new HistoricalChange(
									new Date(), 
									uneditedParent.getId(), 
									uneditedParentWithChild.getId(), 
									new User(ConfigManager.getConfig().getUserName(), ConfigManager.getConfig().getUserName(), ConfigManager.getConfig().getUserName(), 1));
							change.updateChangeFromDiff(uneditedParent, uneditedParentWithChild, null);
							uneditedParentWithChild.addHistoricalChange(change);


							//now to save the uneditedParentWithChild to database
							parentView.getReqModel().setUneditedRequirement(uneditedParentWithChild);

							for (int i = 0; i < parentView.getRequirementPanel().getCmbStatus().getItemCount(); i++) {
								if (parentView.getRequirementPanel().getCmbStatus().getItemAt(i) == "COMPLETE") {
									parentView.getRequirementPanel().getCmbStatus().removeItemAt(i);
								}
							}
							//now to save the uneditedPanelWithChild to database
							String JsonRequest = uneditedParentWithChild.toJSON();
							final RequestObserver requestObserver = new UpdateRequirementRequestObserver(parentView);
							Request request;
							request = Network.getInstance().makeRequest("requirementsmanager/requirement", HttpMethod.POST);
							request.setBody(JsonRequest);
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
