/**************************************************
 * This file was developed for CS3733: Software Engineering The course was taken
 * at Worcester Polytechnic Institute.
 * 
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Tyler Stone
 **************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller;

import java.util.ArrayList;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RequirementPriority;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RequirementStatus;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.observer.BatchRequirementEditRequestObserver;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.observer.BatchUpdateRequirementRequestObserver;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * A general class for editing a single field for multiple requirements.
 *
 * @param <T> the generic type
 * @author Tyler Stone
 * @version Apr 4, 2013
 * 
 * TODO: update date modified on each requirement edit!!!!
 */

public class BatchRequirementEditController<T> {

	/**
	 * The Enum ChangeField.
	 */
	public enum ChangeField {
		RELEASENUMBER, // string
		ITERATIONID, // int
		STATUS, // requirementStatus
		PRIORITY, // requirementPriority
		ESTIMATE, // int
		ACTUAL, // int
		ASSIGNEE, // username
		PARENTID // int
	}

	private ChangeField changeField;
	private T changeToMake; // generic type because it can be either a string,
	// int, requirementStatus, or requirementPriority

	/**
	 * Instantiates a new batch requirement edit controller.
	 *
	 * @param changeField the field containing the change 
	 * @param changeToMake the change to make
	 */
	public BatchRequirementEditController(ChangeField changeField, T changeToMake) {
		this.changeField = changeField;
		this.changeToMake = changeToMake;
	}

	/**
	 * Begin changing the specified requirements.
	 *
	 * @param requirementIDs the ids of the requirements to change
	 */
	public void instantiateChange(ArrayList<Integer> requirementIDs) {
		getEachRequirement(requirementIDs);
	}

	/**
	 * Get each requirement from the server, and then have the request
	 * observer send the received data to the changeSingleRequirement
	 * function.
	 *
	 * @param requirementIDs the ids of the requirements to fetch
	 * @return the each requirement
	 */
	private void getEachRequirement(ArrayList<Integer> requirementIDs) {
		/* send a request for each id */
		for (int iterate : requirementIDs) {
			Request request;
			request = Network.getInstance().makeRequest(
					"requirementsmanager/requirement/"
							+ requirementIDs.get(iterate), HttpMethod.GET);
			request.addObserver(new BatchRequirementEditRequestObserver(this));
			request.send();
		}
	}

	/**
	 * Change a single requirement, called by the
	 * request observer that received the requirement data.
	 *
	 * @param requirement the requirement to change
	 */
	public void changeSingleRequirement(Requirement requirement) {
		/* depending on the specified field, make the change for this specific requirement */
		switch (changeField) {
		case RELEASENUMBER:
			requirement.setReleaseNumber((String) changeToMake);
			break;
		case ITERATIONID:
			requirement.setIterationId((Integer) changeToMake);
			break;
		case STATUS:
			requirement.setStatus((RequirementStatus) changeToMake);
			break;
		case PRIORITY:
			requirement.setPriority((RequirementPriority) changeToMake);
			break;
		case ESTIMATE:
			requirement.setEstimateEffort((Integer) changeToMake);
			break;
		case ACTUAL:
			requirement.setActualEffort((Integer) changeToMake);
			break;
		case ASSIGNEE:
			requirement.setAssignee((ArrayList<String>) changeToMake);
			break;
		case PARENTID:
			requirement.setParentRequirementId((Integer) changeToMake);
			break;
		default:
			System.out.println("Invalid Switch Case");
		}

		final RequestObserver requestObserver = new BatchUpdateRequirementRequestObserver();

		Request request;
		request = Network.getInstance().makeRequest("requirementsmanager/requirement", HttpMethod.POST);

		String JsonRequest = requirement.toJSON();
		request.setBody(JsonRequest);

		request.addObserver(requestObserver);
		request.send();
	}

	/**
	 * Print out an error if encountered one when retrieving requirement.
	 *
	 * @param string the error message
	 */
	public void errorRetrievingRequirement(String string) {
		System.out.println(string);
	}
}
