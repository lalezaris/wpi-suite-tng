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
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.observers;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.UserPermission;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.UserPermissionPanel;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

/**
 * Observer for update permissions.
 *
 * @author Chris Hanna
 * @version Apr 5, 2013
 */
public class UpdatePermissionObserver implements RequestObserver{

	UserPermissionPanel panel;

	/**
	 * This method is called when information about an UpdatePermission
	 * which was previously requested using an asynchronous
	 * interface becomes available.
	 *
	 * @param panel the panel
	 */
	public UpdatePermissionObserver(UserPermissionPanel panel){
		this.panel = panel;
	}


	/**
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseSuccess(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseSuccess(IRequest iReq) {

		Request request = (Request) iReq;

		// get the response from the request
		ResponseModel response = request.getResponse();

		// parse the Requirement from the body
		final UserPermission per = UserPermission.fromJSON(response.getBody());

		if (panel !=null)
			panel.getView().getPermModel().addPermission(per);


		CurrentUserPermissions.updateCurrentUserPermissions();

	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseError(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseError(IRequest iReq) {
		System.out.println("Per response error");
		System.out.println("Received " + iReq.getResponse().getStatusCode() + " error from server: " + iReq.getResponse().getStatusMessage()); 
	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#fail(edu.wpi.cs.wpisuitetng.network.models.IRequest, java.lang.Exception)
	 */
	@Override
	public void fail(IRequest iReq, Exception exception) {
		System.out.println("Per response fail");

	}

}
