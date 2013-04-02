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
 *  CDUNKERS
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller;

import static edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.RequirementStatus.DELETED;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.RequirementStatus;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementPanel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementPanel.Mode;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.observer.CreateRequirementRequestObserver;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.observer.UpdateRequirementRequestObserver;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * Controller that handles the delete requirement action
 *
 * @author CDUNKERS
 *
 * @version Mar 27, 2013
 *
 */
public class DeleteRequirementController {
	
	/** The view object containing the request fields */
	protected RequirementView view;

	/**
	 * Construct a new handler for the given view
	 * @param view the view containing the request fields
	 */
	public DeleteRequirementController(RequirementView view) {
		this.view = view;
	}

	/**
	 * Save the view's Iteration model to the server (asynchronous).
	 */
	public void delete() {
		final RequirementPanel panel = (RequirementPanel) view.getRequirementPanel();
		final RequestObserver requestObserver = (panel.getEditMode() == Mode.CREATE) ? new CreateRequirementRequestObserver(view) : new UpdateRequirementRequestObserver(view);
		Request request;
		request = Network.getInstance().makeRequest("requirementsmanager/requirement", (panel.getEditMode() == Mode.CREATE) ? HttpMethod.PUT : HttpMethod.POST);
		if(panel.checkRequiredFields() == 0){
			Requirement delRequirement = panel.getEditedModel();
			delRequirement.setStatus(RequirementStatus.DELETED);
			request.setBody(delRequirement.toJSON());
			request.addObserver(requestObserver);
			request.send();
			//close tab
			this.view.getTab().getView().removeTabAt(this.view.getTab().getThisIndex());
			System.out.println("DELETE REQUIREMENT");
		} 

	} 
}
