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
 *  Joe Spicola
 *  Tyler Stone
 *  Lauren Kahn
 **************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JOptionPane;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.RequirementValidator;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.ValidationIssue;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.IntegerField;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RequirementStatus;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementPanel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementPanel.Mode;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.observer.CreateRequirementRequestObserver;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.observer.UpdateRequirementRequestObserver;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.controller.MainTabController;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * Controller to handle the saving of a requirement
 * Adapted from SaveDefectController in project DefectTracker
 * 
 * @author Joe Spicola
 * 
 * @version Mar 18, 2013
 *
 */
public class SaveRequirementController {

	/** The view object containing the request fields */
	protected RequirementView view;

	protected RequirementValidator reqVal;
	protected List<ValidationIssue> issues;

	/**
	 * Construct a new handler for the given view
	 * @param view the view containing the request fields
	 */
	public SaveRequirementController(RequirementView view) {
		this.view = view;
		reqVal = new RequirementValidator(null);
		issues = new ArrayList<ValidationIssue>();
	}

	/**
	 * Save the view's Requirement model to the server (asynchronous).
	 */
	public void save() {
		final RequirementPanel panel = (RequirementPanel) getView().getRequirementPanel();
		final RequestObserver requestObserver = (panel.getEditMode() == Mode.CREATE || panel.getEditMode() == Mode.CHILD) ? new CreateRequirementRequestObserver(getView()) : new UpdateRequirementRequestObserver(getView());
		Request request;
		request = Network.getInstance().makeRequest("requirementsmanager/requirement", (panel.getEditMode() == Mode.CREATE || panel.getEditMode() == Mode.CHILD) ? HttpMethod.PUT : HttpMethod.POST);

		if(view.checkRequiredFields() == 0){
			Requirement req = panel.getEditedModel();
			issues = reqVal.validate(req, view.getMode());

			if(issues.size() > 0){
				printIssues(issues);
			} else {
				panel.getNotesView().getSaveButton().doClick();	//save the note if did not press button		
				panel.getAcceptanceTestsView().getAddButton().doClick(); //save the acceptance test if the add test button was not pressed
				panel.getTasksView().closeIfClosed(panel.getEditedModel().getStatus());//Close all tasks if the requirement was closed.
				panel.getTasksView().getNewTaskPanel().getSaveButton().doClick(); // save the new task

				//				if(view.getReqModel().getUneditedRequirement().getTotalEstimateEffort() != ((RequirementPanel) view.getRequirementPanel()).getEditedModel().getTotalEstimateEffort()){ //checks that estimate has been changed
				//					if((!((RequirementPanel) view.getRequirementPanel()).getEditedModel().isTopLevelRequirement()) && (view != null)){ //If the estimate is a parent, no children need to be updated, and check that the view is not null
				//						Requirement parent = view.getParentRequirement(); //the parent of the current requirement
				//						RequirementView currentView = view; //the view of the current requirement
				//RequestObserver parentEstimateRequestObserver = new UpdateRequirementRequestObserver(currentView); //request observer for the parent of the current requirement
				//						while(!((RequirementPanel) currentView.getRequirementPanel()).getEditedModel().isTopLevelRequirement() && currentView != null){ //runs loop if the current requirement is not a top level requirement and that the current view is not null
				//							int totalChildEstimateEffort = parent.getTotalEstimateEffort() - currentView.getReqModel().getUneditedRequirement().getTotalEstimateEffort() + ((RequirementPanel) currentView.getRequirementPanel()).getEditedModel().getTotalEstimateEffort();//calculate the estimate: parent estimate - child's unedited estimate + child's edited estimate
				//							parent.setTotalEstimateEffort(totalChildEstimateEffort); //Set parent's estimate effort to what was calculated
				//							if(currentView.getParentView() != null){ // checks if the parent view of the current view is not null so that dynamic updating can occur
				//								currentView.getParentView().getRequirementPanel().setTxtTotalEstimate(totalChildEstimateEffort); //Sets the text estimate of the parent requirement
				//								currentView.getParentView().getRequirementPanel().getTxtTotalEstimate().setEnabled(false); //Disables the estimate field in the parent
				//							}Requirement theReq = view.getParentRequirement();
				//							theReq.getParentRequirementId();

				//Sends request to update parent
				//							Request estimateRequest;
				//							estimateRequest = Network.getInstance().makeRequest("requirementsmanager/requirement", (panel.getEditMode() == Mode.CREATE || panel.getEditMode() == Mode.CHILD) ? HttpMethod.POST : HttpMethod.POST);
				//							String JsonRequest = parent.toJSON();
				//							estimateRequest.setBody(JsonRequest);
				//							estimateRequest.addObserver(parentEstimateRequestObserver);
				//							estimateRequest.send();
				//							if(!parent.isTopLevelRequirement() && currentView.getParentView() != null){ //Checks if parent is not a top level requirement and parent view is not null, used to make sure all levels of children are updated
				//								currentView = currentView.getParentView(); //updates the current view to be the parent view 
				//								parent = currentView.getParentRequirement(); //updates the parent to be the parent requirement
				//							}
				//							else{
				//								break; //if parent is a top level requirement, break out of the loop
				//							}
				//						}
				//					}
				//				}

				//				HashMap<Integer, RequirementView> viewTable = MainTabController.getController().getReqViewHashMap();
				//				
				//				Requirement me = panel.getEditedModel();

				//this.view.getParentView()

				//updateViewEstimates(view);
				if((!((RequirementPanel) view.getRequirementPanel()).getEditedModel().isTopLevelRequirement()) && (view != null)) {
					boolean saveFlag = false;
					Requirement parent = view.getParentRequirement(); //the parent of the current requirement
					RequirementView currentView = view; //the view of the current requirement
					
					if (currentView.getRequirementPanel().getEditedModel().getStatus() == RequirementStatus.COMPLETE 
							&& currentView.getReqModel().getRequirement().getStatus() != RequirementStatus.COMPLETE) {
						saveFlag = true;
						parent.addCompletedChild();
						
						if (!(currentView.getParentView() == null)) {
							currentView.getParentView().getReqModel().getRequirement().addCompletedChild();
							
							if (currentView.getParentView().getReqModel().getRequirement().canBeCompleted() == true) {
								RequirementPanel parentPanel = currentView.getParentView().getRequirementPanel();
								parentPanel.getCmbStatus().addItem("COMPLETE");
							}
						}
					} else if (currentView.getRequirementPanel().getEditedModel().getStatus() != RequirementStatus.COMPLETE
							&& currentView.getReqModel().getRequirement().getStatus() == RequirementStatus.COMPLETE) {
						saveFlag = true;
						parent.removeCompletedChild();
						
						if (currentView.getParentView().getReqModel().getRequirement().canBeCompleted() == false) {
							RequirementPanel parentPanel = currentView.getParentView().getRequirementPanel();
							int delete = -1;
							for (int i = 0; i < parentPanel.getCmbStatus().getItemCount(); i++) {
								if (parentPanel.getCmbStatus().getItemAt(i) == "COMPLETE") {
									delete = i;
								}
							}
							
							if (delete != -1) {
								parentPanel.getCmbStatus().removeItemAt(delete);
							}
						}
					}

					RequestObserver parentRequestObserver = new UpdateRequirementRequestObserver(currentView); //request observer for the parent of the current requirement

					//Sends request to update parent
					Request parentRequest;
					parentRequest = Network.getInstance().makeRequest("requirementsmanager/requirement", (panel.getEditMode() == Mode.CREATE || panel.getEditMode() == Mode.CHILD) ? HttpMethod.POST : HttpMethod.POST);
					String JsonRequest = parent.toJSON();
					System.out.println(JsonRequest);
					parentRequest.setBody(JsonRequest);
					parentRequest.addObserver(parentRequestObserver);
					parentRequest.send();
				}
			}
		}

		String JsonRequest = panel.getEditedModel().toJSON();
		request.setBody(JsonRequest);
		System.out.println(JsonRequest);
		request.addObserver(requestObserver);
		request.send();
		//close tab
		view.getTab().getView().removeTabAt(this.getView().getTab().getThisIndex());
	}


	private void updateViewEstimates(RequirementView view){
		if (view.getParentRequirement() != null){
			if (view.getParentView() != null){
				int something = view.getParentView().getReqModel().getRequirement().getTotalEstimateEffort();
				view.getParentView().getRequirementPanel().getTxtTotalEstimate().setText(""+something);
			}
		}
	}


	/**
	 * A function to printout all of the issues in a pop up message
	 * 
	 * @param issues a list of the issues 
	 */
	public void printIssues(List<ValidationIssue> issues){
		StringBuffer message = new StringBuffer();
		for(int i = 0; i < issues.size(); i++){
			message.append(issues.get(i).getFieldName() + ":" + " ");
			message.append(issues.get(i).getMessage() + "\r\n");			
		}

		JOptionPane.showMessageDialog(view, 
				message.toString(), 
				"Error", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Gets view
	 * 
	 * @return the view
	 */
	public RequirementView getView() {
		return view;
	}

}
