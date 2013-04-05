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
 *  Chirs Hanna
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.UserPermission;

import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;

import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * Controller to save permissions
 *
 * @author Chris Hanna
 *
 * @version Apr 5, 2013
 *
 */
public class SavePermissionsController {
	/** The view object containing the request fields */

	private UserPermissionPanel panel;
	
	/**
	 * Construct a new handler for the given view
	 * @param view the view containing the request fields
	 */
	public SavePermissionsController(UserPermissionPanel panel) {
		this.panel = panel;
	}


	/**
	 * Call this to save a Permission
	 * @param permission the permission to save to the database
	 * @param mode chose to update, or make a new one
	 */
	public void save(UserPermission permission, PermissionSaveMode mode) {
		Request request;
		
		if (mode == PermissionSaveMode.NEW)
			request = Network.getInstance().makeRequest("requirementsmanager/permissions", HttpMethod.PUT);
		else request = Network.getInstance().makeRequest("requirementsmanager/permissions", HttpMethod.POST);
			String JsonRequest = permission.toJSON();
			request.setBody(JsonRequest);
			request.addObserver(new UpdatePermissionObserver(panel));
			request.send();
			//close tab
		
	} 
}
