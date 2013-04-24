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
 *  Chris Hanna
 *  Tianyu Li
 **************************************************/

package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.controller;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.RequirementValidator;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.ValidationIssue;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementPanel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.RequirementListPanel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.model.RequirementTableModel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * Controller for update requirements in the table view.
 *
 * @author Chris Hanna
 * @author Tianyu Li
 *
 * @version Apr 11th, 2013
 *
 */
public class UpdateAllRequirementsController {

	protected RequirementListPanel panel;
	protected RequirementValidator reqVal;
	protected List<ValidationIssue> issues;

	/**
	 * Instantiates a new update all requirements controller.
	 *
	 * @param panel the RequirementListPanel
	 */
	public UpdateAllRequirementsController(RequirementListPanel panel){
		this.panel = panel;
		issues = new ArrayList<ValidationIssue>();
	}

	/**
	 * Update everything.
	 */
	public void update(){
		RequirementTableModel table = (RequirementTableModel)panel.getTable().getModel();
		this.panel.getTable().setBackground(Color.WHITE);
		for (int i = 0 ; i < table.getRequirements().size(); i++) {
			try {
				issues = reqVal.validate(table.getRequirements().get(i), RequirementPanel.Mode.EDIT);
			} catch (NullPointerException e) {
			}
			if(issues.size() > 0){
				printIssues(issues, table.getRequirements().get(i).getTitle());
			}
			
			if (table.getRequirements().get(i) != null && issues.size() == 0) {
				saveRequirement(table.getRequirements().get(i));
			}
		}
		((RequirementTableModel) panel.getTable().getModel()).clear();
		((RequirementTableModel) panel.getTable().getModel()).clearRequirements();
		this.panel.getModel().setIsChange(false);
	}

	/**
	 * Save requirement.
	 *
	 * @param r the requirement
	 */
	private void saveRequirement(Requirement r){
		Request request = Network.getInstance().makeRequest(
				"requirementsmanager/requirement/"
						+ r.getId(), HttpMethod.POST);
		String JsonRequest = r.toJSON();
		request.setBody(JsonRequest);
		request.addObserver(new UpdateRequirementObserver(this));
		request.send();
		this.panel.getModel().setIsChange(false);
	}

	/**
	 * A function to printout all of the issues in a pop up message
	 * 
	 * @param issues a list of the issues 
	 */
	public void printIssues(List<ValidationIssue> issues, String title){
		StringBuffer message = new StringBuffer();
		for(int i = 0; i < issues.size(); i++){
			message.append(issues.get(i).getFieldName() + ":" + " ");
			message.append(issues.get(i).getMessage() + "\r\n");			
		}
		StringBuffer reqMessage = new StringBuffer("Error in " + title + ":\n");
		message = reqMessage.append(message);

		JOptionPane.showMessageDialog(null, 
				title + message.toString(), 
				"Error", JOptionPane.ERROR_MESSAGE);
	}

	public RequirementListPanel getPanel() {
		return panel;
	}
}