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
 *  Chris Hanna
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tree.controller;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.IRetrieveRequirementController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.observer.RetrieveRequirementRequestObserver;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tree.observer.RetrieveRequirementObserverTree;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * Retrieve Requirements for the treeView
 * 
 * @author Chris Hanna
 *
 */
public class RetrieveRequirementControllerTree {

	private IRetrieveRequirementController caller;
	
	public RetrieveRequirementControllerTree(IRetrieveRequirementController caller)
	{
		this.caller = caller;
	}
	
	public void retrieve(){
	
		int id = caller.getID();
		if (id > -1){
			Request request;
			request = Network.getInstance().makeRequest("requirementsmanager/requirement/" + id, HttpMethod.GET);
			request.addObserver(new RetrieveRequirementObserverTree(this));
			request.send();
		} else recieveData(null);
	}
	
	public void recieveData(Requirement r){
		caller.runWhenRecieved(r);
	}
	/**
	 * Called by {@link RetrieveRequirementRequestObserver} when an error
	 * occurred retrieving the requirement from the server.
	 */
	public void errorRetrievingRequirement(String error) {
		//TODO
	}
}
