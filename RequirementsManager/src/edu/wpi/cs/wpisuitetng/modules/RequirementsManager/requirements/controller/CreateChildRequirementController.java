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
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.controller.MainTabController;

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
		MainTabController.getController().addChildRequirementTab(childRequirement);
	}
	
}

