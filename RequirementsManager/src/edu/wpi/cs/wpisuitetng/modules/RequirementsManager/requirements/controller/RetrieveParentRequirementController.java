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

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.observer.RetrieveAllIterationsObserver;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.observer.RetrieveParentRequirementObserver;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * The controller to retrieve a parent requirement from the database
 *
 * @author Tyler
 *
 * @version Apr 16, 2013
 *
 */
public class RetrieveParentRequirementController {
	RequirementView view;
	
	public RetrieveParentRequirementController(RequirementView view){
		this.view = view;
	}
	
	public void retrieveParent() {
		final RequestObserver requestObserver = new RetrieveParentRequirementObserver(view);
		Request request;
		request = Network.getInstance().makeRequest("requirementsmanager/requirement/" + view.getReqModel().getRequirement().getParentRequirementId(), HttpMethod.GET);
		request.addObserver(requestObserver);
		request.send();
	}
}
