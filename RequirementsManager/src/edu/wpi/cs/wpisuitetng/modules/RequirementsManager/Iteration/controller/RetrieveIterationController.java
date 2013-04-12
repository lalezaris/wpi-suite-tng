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
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Iteration.controller;

import java.awt.event.MouseAdapter;

import javax.swing.JOptionPane;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Iteration.observer.RetrieveIterationRequestObserver;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Iteration;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.observer.RetrieveRequirementRequestObserver;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.IterationListPanel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.RequirementListPanel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.model.IterationTableModel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.model.RequirementTableModel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * A mouse listener for the iteration table on the dashboard.
 *
 * @author Tyler Stone
 *
 * @version Mar 24, 2013
 *
 */
public class RetrieveIterationController extends MouseAdapter {
	
	/** The iteration list panel. */
	IterationListPanel iterationListPanel;
	private int selectedIteration;
	
	/**
	 * Instantiates a new retrieve iteration controller.
	 *
	 * @param iterationListPanel the iteration list panel
	 */
	public RetrieveIterationController(IterationListPanel iterationListPanel) { 
		this.iterationListPanel = iterationListPanel;
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
    public void mouseClicked(java.awt.event.MouseEvent evt) {
        int row = iterationListPanel.getTable().rowAtPoint(evt.getPoint());
   
        if (row >= 0) {
        	selectedIteration = ((IterationTableModel)iterationListPanel.getTable().getModel()).getRowID(row);
            
            if (evt.getClickCount() == 2) {
				Request request;
				request = Network.getInstance().makeRequest("iterationsmanager/iteration/" + selectedIteration, HttpMethod.GET);
				request.addObserver(new RetrieveIterationRequestObserver(this));
				request.send();
            }
        }
    }
	
	/**
	 * Gets selected iteration.
	 *
	 * @return selected iteration
	 */
	public int getSelectedIteration() {
		return selectedIteration;
	}
	
	/**
	 * Called by {@link RetrieveIterationRequestObserver} when the response
	 * is received from the server.
	 * @param iteration the iteration that was retrieved
	 */
	public void showIteration(Iteration iteration) {
		// Make a new iteration view to display the iteration that was received
		//iteration.setIteration(Iteration.getIterationById(iteration.getId()));
		iterationListPanel.getTabController().addEditIterationTab(iteration);
	}
	
	/**
	 * Called by {@link RetrieveRequirementRequestObserver} when an error
	 * occurred retrieving the requirement from the server.
	 *
	 * @param error the error
	 */
	public void errorRetrievingIteration(String error) {
		JOptionPane.showMessageDialog(iterationListPanel, 
				"An error occurred opening the iteration you selected. " + error, "Error opening iteration", 
				JOptionPane.ERROR_MESSAGE);
	}
}
