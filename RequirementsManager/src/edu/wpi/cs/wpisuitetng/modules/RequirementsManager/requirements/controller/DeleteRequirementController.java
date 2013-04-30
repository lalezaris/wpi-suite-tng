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
 *  Chris Dunkers
 **************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.RequirementValidator;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.ValidationIssue;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RequirementStatus;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementPanel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementPanel.Mode;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.observer.CreateRequirementRequestObserver;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.observer.UpdateRequirementRequestObserver;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * Controller that handles the delete requirement action.
 *
 * @author Chris Dunkers
 *
 * @version Mar 27, 2013
 *
 */
public class DeleteRequirementController {

	/** The view object containing the request fields */
	protected RequirementView view;
	protected RequirementValidator reqVal;
	protected List<ValidationIssue> issues;

	/**
	 * Construct a new handler for the given view.
	 * @param view the view containing the request fields
	 */
	public DeleteRequirementController(RequirementView view) {
		this.view = view;
		reqVal = new RequirementValidator(null);
		issues = new ArrayList<ValidationIssue>();
	}

	/**
	 * Save the view's Iteration model to the server (asynchronous).
	 */
	public void delete() {
		final RequirementPanel panel = view.getRequirementPanel();
		Requirement delRequirement = panel.getEditedModel();
		
		if (!(view.getParentRequirement() == null)) {
			final Requirement parent = view.getParentRequirement();
			parent.removeChildRequirement(delRequirement.getId());
			
			if (delRequirement.getStatus() == RequirementStatus.COMPLETE) {
				parent.removeCompletedChild();
			}
			
			parent.setEstimateEffort(parent.getEstimateEffort() - delRequirement.getEstimateEffort());
			
			Request parentRequest;
			parentRequest = Network.getInstance().makeRequest("requirementsmanager/requirement", HttpMethod.POST);
		
			String parentJsonRequest = parent.toJSON();
			parentRequest.setBody(parentJsonRequest);
			parentRequest.send();
		}
		
		final RequestObserver requestObserver = (panel.getEditMode() == Mode.CREATE) ? new CreateRequirementRequestObserver(view) : new UpdateRequirementRequestObserver(view);
		Request request;
		request = Network.getInstance().makeRequest("requirementsmanager/requirement", (panel.getEditMode() == Mode.CREATE) ? HttpMethod.PUT : HttpMethod.POST);
		if(view.checkRequiredFields() == 0){
			delRequirement.setParentRequirementId(-1);
			
			try {
				issues = reqVal.validate(delRequirement, view.getMode());
				if(issues.size() > 0){
					printIssues(issues);
				} else {
					delRequirement.setStatus(RequirementStatus.DELETED);
					String JsonRequest = delRequirement.toJSON();
					request.setBody(JsonRequest);
					request.addObserver(requestObserver);
					request.send();
					//close tab
					view.getTab().getView().removeTabAt(view.getTab().getThisIndex());
				}
			} catch (Exception e){
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
}



