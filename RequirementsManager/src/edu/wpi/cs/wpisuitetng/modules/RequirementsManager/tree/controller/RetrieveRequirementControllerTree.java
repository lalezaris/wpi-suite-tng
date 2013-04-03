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
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * Retrieve Requirements for the treeView
 * 
 * @author Chris Hanna
 *
 */
public class RetrieveRequirementControllerTree<T> {

	private IRetrieveRequirementController<T> caller;
	private String address;
	private RequestObserver observer;
	
	public RetrieveRequirementControllerTree(RequestObserver observer, String address, IRetrieveRequirementController<T> caller)
	{
		this.observer = observer;
		this.address = address;
		this.caller = caller;
	}
	
	public void retrieve(){
	
		String id = caller.getID();
		//int id = -1;
		
		//if (Integer.parseInt(id) > -1){
			Request request;
			//"requirementsmanager/requirement/"
			request = Network.getInstance().makeRequest(this.address + id, HttpMethod.GET);
			request.addObserver(new RetrieveRequirementObserverTree<T>(this));
			request.addObserver(this.observer);
			request.send();
			
		//} else recieveData(null);
	}
	
	//public void recieveData(T r){
	//	caller.runWhenRecieved(r);
	//}
	public void recieveData(String content){
		caller.runWhenRecieved(content);
	}
	
	/**
	 * Called by {@link RetrieveRequirementRequestObserver} when an error
	 * occurred retrieving the requirement from the server.
	 */
	public void errorRetrievingRequirement(String error) {
		//TODO
	}
}
