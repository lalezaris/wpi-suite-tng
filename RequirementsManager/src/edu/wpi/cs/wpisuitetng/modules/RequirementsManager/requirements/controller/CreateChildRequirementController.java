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
 * Lauren Kahn
 * Tushar Narayan
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Iteration;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementPanel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementPanel.Mode;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.observer.CreateRequirementRequestObserver;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.observer.RetrieveRequirementRequestObserver;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.observer.UpdateRequirementRequestObserver;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.tabs.NotesView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.RequirementListPanel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.controller.MainTabController;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 *
 * @author Tushar Narayan
 *
 * @version Mar 31, 2013
 *
 */
public class CreateChildRequirementController {

	private Requirement parentRequirement;
	private Requirement childRequirement;
	
	public CreateChildRequirementController(Requirement parentRequirement) {
		this.parentRequirement = parentRequirement;
		this.childRequirement = new Requirement();
	}

	/**
	 * Serve up the child requirement
	 */
	public void viewChild() {
		System.out.println("Creating child requirement tab view here.");
		childRequirement.setIteration(parentRequirement.getIteration());
		childRequirement.setReleaseNumber(parentRequirement.getReleaseNumber());
		//TODO: Do we need types of requirements? If yes, child inherits that from parent as well.
		childRequirement.setStatus(parentRequirement.getStatus());
		childRequirement.setParentRequirementId(parentRequirement.getId());
		showRequirement(childRequirement);
		
		//((RequirementPanel) view.getRequirementPanel()).updateModel(childRequirement);
		//view.setEditModeDescriptors(childRequirement);
	} 
	
	public void showRequirement(Requirement childRequirement) {
		// Make a new requirement view to display the requirement that was received
		childRequirement.setIteration(Iteration.getIterationById(childRequirement.getIterationId()));
		MainTabController.getController().addEditRequirementTab(childRequirement);
	}
	
}

