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

import com.google.gson.GsonBuilder;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.UserPermission;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.controller.SetUpPermissionsPanelController;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

/**
 * Observer for Permissions.
 *
 * @author Chris Hanna
 * @version Apr 5, 2013
 */
public class PermissionsObserver implements RequestObserver{

	private SetUpPermissionsPanelController controller;

	/**
	 * This method is called when information about an Permissions
	 * which was previously requested using an asynchronous
	 * interface becomes available.
	 *
	 * @param controller the controller
	 */
	public PermissionsObserver(SetUpPermissionsPanelController controller){
		this.controller = controller;
	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseSuccess(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseSuccess(IRequest iReq) {
		Request request = (Request) iReq;
		ResponseModel response = request.getResponse();

		GsonBuilder builder = new GsonBuilder();
		UserPermission[] all = builder.create().fromJson(response.getBody(), UserPermission[].class);		

		controller.receiveServerPermissions(all);

	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseError(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseError(IRequest iReq) {

	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#fail(edu.wpi.cs.wpisuitetng.network.models.IRequest, java.lang.Exception)
	 */
	@Override
	public void fail(IRequest iReq, Exception exception) {

	}

}
