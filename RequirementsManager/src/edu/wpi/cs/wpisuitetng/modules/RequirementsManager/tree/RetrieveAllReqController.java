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
 *  Sam Lalezari
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tree;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * Controller to handle retrieving requirements from the server 
 * and displaying them in TreeView
 * 
 * Some code borrowed and modified from RetrieveAllDefectsContoller.java
 *
 * @author Sam Lalezari
 *
 * @version Mar 19, 2013
 *
 */
public class RetrieveAllReqController {
	
	// The tree view
	protected TreeView view;
	
	// Requirement data retrieved from the server
	protected Requirement[] data = null;
	
	/**
	 * Constructer for a new RetriveAllReqController 
	 * 
	 * @param view The tree view
	 */
	public RetrieveAllReqController(TreeView view){
		this.view = view;		
	}
	
	/**
	 * Sends a request for all of the requirements
	 * 
	 */
	public void refreshData(){
		//TODO finish writing code
		final RequestObserver requestObserver = new RetrieveAllReqRequestObserver(this);
		Request request;
		request = Network.getInstance().makeRequest("RequirementsManager/models", HttpMethod.GET);
		request.addObserver(requestObserver);
		request.send();
	}
	
}
