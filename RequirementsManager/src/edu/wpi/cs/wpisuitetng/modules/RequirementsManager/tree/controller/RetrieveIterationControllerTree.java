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
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tree.observer.RetrieveIterationObserverTree;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * Retrieve Iterations for the treeView
 * 
 * @author Arica Liu
 *
 */
public class RetrieveIterationControllerTree<T> {

	private IRetrieveRequirementController<T> caller;
	private String address;
	private RequestObserver observer;
	
	/**
	 * Default constructor
	 * 
	 * @param observer
	 * @param address
	 * @param caller
	 */
	public RetrieveIterationControllerTree(RequestObserver observer, String address, IRetrieveRequirementController<T> caller)
	{
		this.observer = observer;
		this.address = address;
		this.caller = caller;
	}
	
	/**
	 * Retrieve iteration controller tree
	 * 
	 */
	public void retrieve(){
		String id = caller.getID();
		Request request;
		request = Network.getInstance().makeRequest(this.address + id, HttpMethod.GET);
		request.addObserver(new RetrieveIterationObserverTree<T>(this));
		request.addObserver(this.observer);
		request.send();
	}

	public void recieveData(String content){
		caller.runWhenRecieved(content);
	}
	
	/**
	 * Called by {@link RetrieveIterationRequestObserver} when an error
	 * occurred retrieving the iteration from the server.
	 */
	public void errorRetrievingIteration(String error) {
		//TODO
	}
}
