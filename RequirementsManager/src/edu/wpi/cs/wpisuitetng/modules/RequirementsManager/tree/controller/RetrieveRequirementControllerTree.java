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
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.observer.RetrieveRequirementRequestObserver;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tree.observer.RetrieveRequirementObserverTree;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * Retrieve Requirements for the treeView.
 *
 * @param <T> the generic type
 * @author Chris Hanna
 */
public class RetrieveRequirementControllerTree<T> {

	private IRetrieveRequirementController<T> caller;
	private String address;
	private RequestObserver observer;


	/**
	 * Instantiates a new retrieve requirement controller tree.
	 *
	 * @param observer the observer
	 * @param address the address
	 * @param caller the caller
	 */
	public RetrieveRequirementControllerTree(RequestObserver observer, String address, IRetrieveRequirementController<T> caller)
	{
		this.observer = observer;
		this.address = address;
		this.caller = caller;
	}

	/**
	 * Retrieve requirement controller tree.
	 */
	public void retrieve(){

		String id = caller.getID();
		Request request;
		request = Network.getInstance().makeRequest(this.address + id, HttpMethod.GET);
		request.addObserver(new RetrieveRequirementObserverTree<T>(this));
		request.addObserver(this.observer);
		request.send();
	}

	/**
	 * Receive data.
	 *
	 * @param content the content
	 */
	public void recieveData(String content){
		caller.runWhenRecieved(content);
	}

	/**
	 * Called by {@link RetrieveRequirementRequestObserver} when an error
	 * occurred retrieving the requirement from the server.
	 *
	 * @param error the error
	 */
	public void errorRetrievingRequirement(String error) {
	}
	
	/**
	 * @return the string that would be returned when the getID function for the call is called
	 */
	public String getID(){
		return caller.getID();
	}
	
	public void runWhenRecieved(String s){
		caller.runWhenRecieved(s);
	}
}