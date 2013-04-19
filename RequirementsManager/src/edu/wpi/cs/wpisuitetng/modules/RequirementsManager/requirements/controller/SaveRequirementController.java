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
 **************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.RequirementValidator;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.ValidationIssue;
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
			try {
				issues = reqVal.validate(req, view.getMode());
				if(issues.size() > 0){
					printIssues(issues);
				} else {
					panel.getNotesView().getSaveButton().doClick();	//save the note if did not press button		
					
					System.out.println("Mode:" + panel.getEditMode());
					String JsonRequest = req.toJSON();
					request.setBody(JsonRequest);
					System.out.println("Sending REQ to server:" +JsonRequest );
					request.addObserver(requestObserver);
					request.send();
					//close tab
					view.getTab().getView().removeTabAt(this.getView().getTab().getThisIndex());
					System.out.println("SAVE REQUIREMENT");
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

	/**
	 * Gets view
	 * 
	 * @return the view
	 */
	public RequirementView getView() {
		return view;
	}
}
