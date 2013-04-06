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

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.controller.SetUpPermissionsPanelController;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

/**
 * Observer for users
 *
 * @author Chris Hanna
 *
 * @version Apr 5, 2013
 *
 */
public class UsersObserver implements RequestObserver{

	SetUpPermissionsPanelController controller;
	
	public UsersObserver(SetUpPermissionsPanelController controller){
		this.controller = controller;
	}
	
	
	@Override
	public void responseSuccess(IRequest iReq) {
		Request request = (Request) iReq;
		ResponseModel response = request.getResponse();
		
		GsonBuilder builder = new GsonBuilder();
		User[] users = builder.create().fromJson(response.getBody(), User[].class);
		//this.panel.setAllusers(users);
		this.controller.recieveServerUsers(users);
		//CurrentUserPermissions.updateCurrentUserPermissions();
	}

	@Override
	public void responseError(IRequest iReq) {
		
	}

	@Override
	public void fail(IRequest iReq, Exception exception) {
		
	}

}
