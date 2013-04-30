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

import java.util.ArrayList;

import com.google.gson.GsonBuilder;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

/**
 * Observer for current user permissions
 *
 * @author Chris Hanna
 *
 * @version Apr 5, 2013
 *
 */
public class CurrentUserPermissionsObserver implements RequestObserver{

	private ArrayList<User> projectUsers;
	@Override
	public void responseSuccess(IRequest iReq) {

		projectUsers = new ArrayList<User>();

		Request request = (Request) iReq;
		ResponseModel response = request.getResponse();

		GsonBuilder builder = new GsonBuilder();
		User[] users = builder.create().fromJson(response.getBody(), User[].class);

		//Figure out which coreUser's name matches the known current user's name.
		User user = null;
		for (int i = 0 ; i < users.length ; i ++){
			if (ConfigManager.getConfig().getUserName().equals(users[i].getUsername()))
			{
				user = users[i];
				break;
			}
		}
		for (int i=0; i<users.length;i++){
			if(   users[i].getProject() != null  && ConfigManager.getConfig().getProjectName().equals(users[i].getProject().getName()))
			{
				projectUsers.add(users[i]);
			}
		}
		User[] projUserArray = new User[projectUsers.size()];
		for(int i=0;i<projUserArray.length;i++)
			projUserArray[i] = projectUsers.get(i);
		CurrentUserPermissions.setUsers(user, users);

	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseError(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseError(IRequest iReq) {
		System.out.println("Failed to retrieve current user permissions3");

	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#fail(edu.wpi.cs.wpisuitetng.network.models.IRequest, java.lang.Exception)
	 */
	@Override
	public void fail(IRequest iReq, Exception exception) {
		System.out.println("Failed to retrieve current user permissions4");

	}

}
