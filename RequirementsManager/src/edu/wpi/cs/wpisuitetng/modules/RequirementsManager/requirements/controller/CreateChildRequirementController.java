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
 * Tyler Stone
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Iteration;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementPanel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.controller.MainTabController;



/**
 * Controller to create child requirements
 *
 * @author Tushar Narayan
 *
 * @version Mar 31, 2013
 *
 */
public class CreateChildRequirementController {

	private Requirement childRequirement;
	private RequirementView view;
	
	public CreateChildRequirementController(RequirementView view) {
		this.view = view;

		this.childRequirement = new Requirement();
	}

	/**
	 * Serve up the child requirement
	 */
	public void viewChild() {
		childRequirement.setIteration(((RequirementPanel) view.getRequirementPanel()).getModel().getIteration());
		childRequirement.setReleaseNumber(((RequirementPanel) view.getRequirementPanel()).getModel().getReleaseNumber());
		//TODO: Do we need types of requirements? If yes, child inherits that from parent as well.
		childRequirement.setStatus(((RequirementPanel) view.getRequirementPanel()).getModel().getStatus());
		childRequirement.setParentRequirementId(((RequirementPanel) view.getRequirementPanel()).getModel().getId());
		showRequirement(childRequirement);
	} 
	
	public void showRequirement(Requirement childRequirement) {
		// Make a new requirement view to display the requirement that was received
		childRequirement.setIteration(Iteration.getIterationById(childRequirement.getIterationId()));
		MainTabController.getController().addChildRequirementTab(childRequirement, view);
	}
	
}

