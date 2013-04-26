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
 *  Arica Liu
 **************************************************/

package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tree.controller;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tree.observer.UpdateRequirementRequestObserver;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * Controller to handle the saving of a requirement
 * Adapted from SaveDefectController in project DefectTracker
 * 
 * @author Arica Liu
 * 
 * @version April 18, 2013
 *
 */
public class SaveRequirementController {
	protected Requirement requirement;

	/**
	 * Construct a new handler for the given view
	 * @param r the requirement containing the request fields
	 */
	public SaveRequirementController(Requirement r) {
		requirement = r;
	}

	/**
	 * Save the view's Requirement model to the server (asynchronous).
	 */
	public void save() {
		if (requirement.getParentRequirementId() != requirement.getId()) {
			final RequestObserver requestObserver = new UpdateRequirementRequestObserver(getRequirement());
			Request request;
			request = Network.getInstance().makeRequest("requirementsmanager/requirement", HttpMethod.POST);

			String JsonRequest = requirement.toJSON();
			request.setBody(JsonRequest);
			request.addObserver(requestObserver);
			request.send();
		}
	}

	/**
	 * @return the requirement
	 */
	public Requirement getRequirement() {
		return requirement;
	}
}