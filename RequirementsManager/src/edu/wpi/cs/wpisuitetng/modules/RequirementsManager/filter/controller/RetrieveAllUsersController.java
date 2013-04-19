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
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.filter.controller;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.filter.FilterController;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * Insert Description Here
 *
 * @author Chris Hanna
 *
 * @version Apr 17, 2013
 *
 */
public class RetrieveAllUsersController {

	FilterController controller;
	/**
	 * Enter Description here.
	 * 
	 */
	public RetrieveAllUsersController(FilterController controller) {
		this.controller = controller;
	}
	
	/**
	 * Retrieve.
	 */
	public void retrieve(){
		Request request = Network.getInstance().makeRequest("core/user", HttpMethod.GET);
		request.addObserver(new RetrieveAllUsersObserver(this));
		request.send();
	}
	
	/**
	 * Response.
	 *
	 * @param users the users
	 */
	public void response(User[] users){
		controller.setUsers(users);
	}

}
