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
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller;

import java.awt.event.MouseAdapter;

import javax.swing.JOptionPane;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Iteration;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.observer.RetrieveRequirementRequestObserver;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.RequirementListPanel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.model.RequirementTableModel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * A mouse listener for the requirement table on the dashboard
 *
 * @author Tyler Stone
 *
 * @version Mar 24, 2013
 *
 */
public class RetrieveRequirementController extends MouseAdapter {
	RequirementListPanel requirementListPanel;
	private int selectedRequirement;
	
	public RetrieveRequirementController(RequirementListPanel requirementListPanel) { 
		this.requirementListPanel = requirementListPanel;
	}
	
	@Override
    public void mouseClicked(java.awt.event.MouseEvent evt) {
        int row = requirementListPanel.getTable().rowAtPoint(evt.getPoint());
   
        if (row >= 0) {
        	selectedRequirement = ((RequirementTableModel)requirementListPanel.getTable().getModel()).getRowID(row);
            
            if (evt.getClickCount() == 2) {
				Request request;
				request = Network.getInstance().makeRequest("requirementsmanager/requirement/" + selectedRequirement, HttpMethod.GET);
				request.addObserver(new RetrieveRequirementRequestObserver(this));
				request.send();
            }
        }
    }
	
	public int getSelectedRequirement() {
		return selectedRequirement;
	}
	
	/**
	 * Called by {@link RetrieveRequirementRequestObserver} when the response
	 * is received from the server.
	 * @param defect the defect that was retrieved
	 */
	public void showRequirement(Requirement requirement) {
		// Make a new requirement view to display the requirement that was received
		requirement.setIteration(Iteration.getIterationById(requirement.getIterationId()));
		requirementListPanel.getTabController().addEditRequirementTab(requirement);
	}
	
	/**
	 * Called by {@link RetrieveRequirementRequestObserver} when an error
	 * occurred retrieving the requirement from the server.
	 */
	public void errorRetrievingRequirement(String error) {
		JOptionPane.showMessageDialog(requirementListPanel, 
				"An error occurred opening the defect you selected. " + error, "Error opening defect", 
				JOptionPane.ERROR_MESSAGE);
	}
}
