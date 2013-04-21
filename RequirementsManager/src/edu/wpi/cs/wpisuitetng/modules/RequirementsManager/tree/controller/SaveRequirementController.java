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
	// An indicator for changed field;
	// 0 for Iteration, 1 for ParentRequirementID.
	protected int changedField;

	/**
	 * Construct a new handler for the given view
	 * @param view the view containing the request fields
	 */
	public SaveRequirementController(Requirement r, int c) {
		requirement = r;
		changedField = c;
	}

	/**
	 * Save the view's Requirement model to the server (asynchronous).
	 */
	public void save() {
		final RequestObserver requestObserver = new UpdateRequirementRequestObserver(getRequirement(), getChangedField());
		Request request;
		request = Network.getInstance().makeRequest("requirementsmanager/requirement", HttpMethod.POST);

		String JsonRequest = requirement.toJSON();
		request.setBody(JsonRequest);
		System.out.println("Sending REQ to server:" +JsonRequest );
		request.addObserver(requestObserver);
		request.send();

		System.out.println("SAVE REQUIREMENT");
	}

	/**
	 * @return the requirement
	 */
	public Requirement getRequirement() {
		return requirement;
	}

	/**
	 * @return the changedField
	 */
	public int getChangedField() {
		return changedField;
	}
}