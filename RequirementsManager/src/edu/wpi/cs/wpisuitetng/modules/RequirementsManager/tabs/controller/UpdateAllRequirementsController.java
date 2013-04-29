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
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Iteration;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.RequirementValidator;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.ValidationIssue;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.action.Refresher;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementPanel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.RequirementListPanel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.model.RequirementTableModel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.model.RequirementTableModel.CellLocation;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tree.ReqTreeModel;
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

	protected int waitUpdateCount;

	/**
	 * Instantiates a new update all requirements controller.
	 *
	 * @param panel the RequirementListPanel
	 */
	public UpdateAllRequirementsController(RequirementListPanel panel){
		this.panel = panel;
		issues = new ArrayList<ValidationIssue>();
		waitUpdateCount = 0;
	}

	/**
	 * Update everything.
	 */
	public void update(){
		RequirementTableModel table = (RequirementTableModel)panel.getTable().getModel();

		List<Requirement> reqs = new ArrayList<Requirement>();
		List<Integer> reqsRow = new ArrayList<Integer>();
		List<CellLocation> cells = table.getChangedLocations();

		boolean allValid = true;
		for (int i = 0 ; i < cells.size(); i ++)
			if (!cells.get(i).isValid()){
				allValid = false;
				break;
			}
		if (allValid){
			//ADD ROWS WITH VALID CHANGE CELLS TO LIST OF REQUIREMENTS TO BE SAVED
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
						if (!reqs.contains(r)){
							reqs.add(r);
							//reqsRow.add(cells.get(i).getRow());
						}
					}
				}
			}

			//REMOVE ROWS FROM FUTURE SAVE THAT CONTAIN AN INVALID CELL CHANGE
			for (int i = 0 ; i <cells.size(); i ++){
				if (table.getRequirements().get(cells.get(i).getRow())!= null){
					Requirement r = table.getRequirements().get(cells.get(i).getRow());
					if (reqs.contains(r)){
						if (!cells.get(i).isValid()){
							reqs.remove(r);
						}
					}
				} else{
					//reqsRow.add(cells.get(i).getRow());
				}
			}


			for (int i = 0 ; i < reqs.size(); i ++){
				Iteration temp = reqs.get(i).getIteration();
				saveRequirement(reqs.get(i));
				reqs.get(i).setIteration(temp);
				int row = 0;
				for (int c = 0 ; c < cells.size(); c ++){
					if (table.getRequirements().get(cells.get(c).getRow())!= null 
							&& reqs.get(i).equals(table.getRequirements().get(cells.get(c).getRow()))){
						row = cells.get(c).getRow();
					}
				}
				table.updateRow(row, reqs.get(i));
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


			if (issues.size() == 0){ //THERE WERE NO ISSUES. SO STUFF ACTUALLY SAVED
				panel.getModel().setIsChange(false);

				panel.getFilterController().getPanel().triggerTableUpdate();
				panel.setButtonsForSaving();
			}

		}
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
		waitUpdateCount++;
		panel.getModel().setIsChange(false);
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

	/**
	 * Update success.
	 */
	public void updateSuccess() {
		waitUpdateCount--;
		if (waitUpdateCount<=0){
			waitUpdateCount = 0;
			panel.showUpdateSuccessfully("Update Successful.");
			panel.setButtonsForNoChanges();
			ReqTreeModel tree = Refresher.getInstance().getTreeModel();
			if (tree!=null)
				tree.refreshTree();
			panel.getFilterController().setEditable(!panel.getModel().getIsChange());
		}


	}
}