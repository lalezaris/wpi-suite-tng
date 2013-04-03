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


import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Iteration;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.observer.RetrieveAllChildRequirementsRequestObserver;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * Retrieve all child requirements based on a specific requirement ID
 *
 * @author Tyler Stone
 *
 * @version Apr 2, 2013
 *
 */
public class RetrieveAllChildRequirementsController {
	private Requirement[] childRequirementData;
	private Requirement[] rawRequirementData;
	
	public RetrieveAllChildRequirementsController() { }
	
	/**
	 * Function to retrieve a linked list of requirements 
	 *
	 * @return list of child requirements, or an empty set
	 */
	public Requirement[] retrieveChildrenByID(int reqID) {
		sendRequest();
		
		/* loop through each returned requirement and check if the parent id
		 * is equal to the requirement id. If so, it is added to the child 
		 * requirement array.
		 */
		int numChildren = 0;
		for (int i = 0 ; i < rawRequirementData.length; i ++) {
			if (rawRequirementData[i].getParentRequirementId() == reqID) {
				childRequirementData[numChildren] = rawRequirementData[i];
			}
		}
		
		return childRequirementData;
	}
	
	/**
	 * Send a request to the server to get all requirements
	 */
	public void sendRequest() {
		final RequestObserver requestObserver = new RetrieveAllChildRequirementsRequestObserver(this);
		Request request;
		request = Network.getInstance().makeRequest("requirementsmanager/requirement", HttpMethod.GET);
		request.addObserver(requestObserver);
		request.send();
	}

	/**
	 * This method is called by the {@link RetrieveAllRequirementsRequestObserver} when the
	 * response is received
	 * 
	 * @param Requirements an array of Requirements returned by the server
	 */
	public void validateReceivedData(Requirement[] Requirements) {	
		
		if (Requirements.length > 0) {
			// save the data
			this.rawRequirementData = Requirements;
			
			//TODO: improve implementation
			for (int i = 0 ; i < Requirements.length; i ++){
				Requirements[i].setIteration(Iteration.getIterationById(Requirements[i].getIterationId()));
			}
		}
		else {
			// do nothing, there are no Requirements
		}
	}

	/**
	 * This method is called by the {@link RetrieveAllRequirementsRequestObserver} when an
	 * error occurs retrieving the Requirements from the server.
	 */
	public void errorReceivingData(String error) { }
}
