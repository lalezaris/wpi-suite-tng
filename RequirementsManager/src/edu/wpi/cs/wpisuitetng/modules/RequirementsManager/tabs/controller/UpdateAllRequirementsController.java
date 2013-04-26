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
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.model.RequirementTableModel.CellLocation;
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


		
		List<Requirement> reqs = new ArrayList<Requirement>();
		List<Integer> reqsRow = new ArrayList<Integer>();
		List<CellLocation> cells = table.getChangedLocations();
		for (int i = 0 ; i < cells.size() ; i ++){
			if (table.getRequirements().get(cells.get(i).getRow())!= null){
				Requirement r = table.getRequirements().get(cells.get(i).getRow());
				try {
					issues = reqVal.validate(r, RequirementPanel.Mode.EDIT);
				} catch (NullPointerException e) {
					System.out.println("The " + (i + 1) + "th requirement is legal");
				}
				if(issues.size() > 0){
					printIssues(issues, r.getTitle());
				}
				if (r != null && issues.size() == 0){
					reqs.add(table.getRequirements().get(cells.get(i).getRow()));
					reqsRow.add(cells.get(i).getRow());
					saveRequirement(table.getRequirements().get(cells.get(i).getRow()));
				}
			}
		}
		//panel.updateUI();
		
//		for (int i = 0 ; i < table.getRequirements().size(); i++) {
//			try {
//				issues = reqVal.validate(table.getRequirements().get(i), RequirementPanel.Mode.EDIT);
//			} catch (NullPointerException e) {
//				System.out.println("The " + (i + 1) + "th requirement is legal");
//			}
//			if(issues.size() > 0){
//				printIssues(issues, table.getRequirements().get(i).getTitle());
//			}
//			
//			if (table.getRequirements().get(i) != null && issues.size() == 0) {
//				System.out.println("SENDING SAVE REQUEST FROM TABLE");
//				reqs.add(table.getRequirements().get(i));
//				saveRequirement(table.getRequirements().get(i));
//			}
//		}
		//panel.refreshList();
		for (int i = 0 ; i < reqs.size(); i ++){
			System.out.println("update req :" + reqs.get(i).getId() + " with iteration " + reqs.get(i).getIteration());
			table.updateRow(reqsRow.get(i), reqs.get(i));
		}
		panel.updateUI();
		//Requirement[] reqArray = new Requirement[reqs.size()];
		//for (int i = 0 ; i < reqArray.length; i ++)
			//reqArray[i] = reqs.get(i);
		//panel.addRequirements(reqArray);
		
		((RequirementTableModel) panel.getTable().getModel()).clearChangeVisuals();
		//panel.getTable().updateUI();
		//((RequirementTableModel) panel.getTable().getModel()).clear();
		//((RequirementTableModel) panel.getTable().getModel()).clearRequirements();
		this.panel.getModel().setIsChange(false);

		this.panel.getFilterController().getPanel().triggerTableUpdate();
		//this.panel.updateUI();

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
	 * A function to printout all of the issues in a pop up message.
	 *
	 * @param issues a list of the issues
	 * @param title the title
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