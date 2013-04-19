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

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
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

	RequirementListPanel panel;
	
	/**
	 * Instantiates a new update all requirements controller.
	 *
	 * @param panel the RequirementListPanel
	 */
	public UpdateAllRequirementsController(RequirementListPanel panel){
		this.panel = panel;
	}
	
	/**
	 * Update everything.
	 */
	public void update(){
		RequirementTableModel table = (RequirementTableModel)panel.getTable().getModel();
		System.out.println(table.getRequirements().size());
		System.out.println("Reach here");
		for (int i = 0 ; i < table.getRequirements().size(); i++) {
			if (table.getRequirements().get(i) != null) {
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
		System.out.println(r.getId());
		Request request = Network.getInstance().makeRequest(
				"requirementsmanager/requirement/"
						+ r.getId(), HttpMethod.POST);
		String JsonRequest = r.toJSON();
		request.setBody(JsonRequest);
		request.addObserver(new UpdateRequirementObserver(this));
		request.send();
		this.panel.getModel().setIsChange(false);
	}
}