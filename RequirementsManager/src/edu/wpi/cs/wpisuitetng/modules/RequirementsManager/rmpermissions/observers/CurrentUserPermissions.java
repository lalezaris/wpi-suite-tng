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
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.UserPermission;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RMPermissionsLevel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.action.Refresher;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.IOnPermissionUpdate;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.controller.PermissionSaveMode;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.controller.SavePermissionsController;
import edu.wpi.cs.wpisuitetng.modules.core.models.Role;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

/**
 * Current User Permissions.
 *
 * @author Chris Hanna
 * @version Apr 5, 2013
 */
public class CurrentUserPermissions implements RequestObserver{

	private static ArrayList<IOnPermissionUpdate> roList = new ArrayList<IOnPermissionUpdate>();
	private static boolean gotPermissions = false;
	private static User coreUser = null;
	private static User[] allCoreUsers = null;
	private static UserPermission user = null;
	private static CurrentUserPermissions instance = null;

	/**
	 * Gets the single instance of CurrentUserPermissions.
	 *
	 * @return single instance of CurrentUserPermissions
	 */
	protected static CurrentUserPermissions getInstance(){
		if (instance == null){
			instance = new CurrentUserPermissions();
		}
		return instance;
	}

	/**
	 * Default constructor.
	 */
	private CurrentUserPermissions(){}

	/**
	 * Call this method to send out requests to get the most recent User Permissions Level.
	 * Because this is sending requests, you cannot rely on User Permissions being accurate after
	 * this method call. 
	 */
	public static void updateCurrentUserPermissions(){
		reset();
		Refresher.getInstance().getObjects(getInstance(), "requirementsmanager/permissions", "");
		Refresher.getInstance().getObjects(new CurrentUserPermissionsObserver(),"core/user", "");
	}

	/**
	 * Update current user permissions.
	 *
	 * @param r the r
	 */
	public static void updateCurrentUserPermissions(IOnPermissionUpdate r){
		reset();
		Refresher.getInstance().getObjects(getInstance(), "requirementsmanager/permissions", "");
		Refresher.getInstance().getObjects(new CurrentUserPermissionsObserver(),"core/user", "");
		roList.add(r);
	}

	/**
	 * Resets current user permissions.
	 */
	private static void reset(){
		coreUser = null;
		gotPermissions = false;
	}

	/**
	 * Set the coreUser that will be considered the current user.
	 * It is recommended that this only be called internally. Calling it externally can break the project.
	 *
	 * @param currentUser the current user
	 * @param allUsers the all users
	 */
	protected static void setUsers(User currentUser, User[] allUsers){
		CurrentUserPermissions.coreUser = currentUser;
		CurrentUserPermissions.allCoreUsers = allUsers;
		doWhenRecievedAllData();

	}


	/**
	 * Gets current user permissions.
	 *
	 * @return The current user's RM permission level
	 */
	public static RMPermissionsLevel getCurrentUserPermission(){
		if (!gotPermissions|| coreUser==null){
			return RMPermissionsLevel.NONE;
		}
		else {
			return user.getPermissions();
		}
	}

	/**
	 * Decide if the current user has a certain permission level. This will only take into account
	 * the local RM permission level.
	 * @param level The Level you want to check
	 * @return True if the current user has the input level or greater
	 */
	public static boolean doesUserHavePermissionLocal(RMPermissionsLevel level){
		if (!gotPermissions || coreUser==null){
			return false;
		} else{
			return valueOf(user.getPermissions()) >= valueOf(level);
		}
	}

	/**
	 * Decide if the current user has a certain permission level. This will take into account the
	 * user's WPI-TNG-SUITE Role as well as the local RM permission level.
	 * @param level The Level you want to check
	 * @return True if the current user has the input level or greater
	 */
	public static boolean doesUserHavePermissionMaster(RMPermissionsLevel level){
		//If we don't have responses from both Requests, then return FALSE
		if (!gotPermissions || coreUser==null){
			return false;
		} else{ 
			//but if we do have responses, then ..
			//The user has access IF their local RM permissions are great enough, OR their master WPISUITE permissions are great enough.
			return (valueOf(user.getPermissions()) >= valueOf(level) || coreUser.getRole() == Role.ADMIN);
		}
	}

	/**
	 * Value of.
	 *
	 * @param level the level
	 * @return the int
	 */
	private static int valueOf(RMPermissionsLevel level){
		if (level == RMPermissionsLevel.ADMIN)
			return 3;
		else if (level == RMPermissionsLevel.UPDATE)
			return 2;
		else if (level == RMPermissionsLevel.NONE)
			return 1;
		else return 0;
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

		for (int i = 0 ; i < all.length ; i ++){
			if (all[i].getUsername().equals(ConfigManager.getConfig().getUserName()))
				user = all[i];
		}
		gotPermissions = true;
		doWhenRecievedAllData();

	}

	/**
	 * Do when received all data.
	 */
	private static void doWhenRecievedAllData(){
		if (gotPermissions && coreUser !=null){

			//The signed in user does not have a user permission associated with them yet.
			if (user == null){
				SavePermissionsController controller = new SavePermissionsController(null);

				UserPermission perm = new UserPermission(ConfigManager.getConfig().getUserName(),RMPermissionsLevel.NONE);

				for (int i = 0 ; i < allCoreUsers.length; i ++){
					if (perm.getUsername().equals(allCoreUsers[i].getUsername())){
						User u = allCoreUsers[i];
						if (u.getRole() == Role.ADMIN){
							perm.setPermissions(RMPermissionsLevel.ADMIN);
						}
					}
				}


				controller.save(perm, PermissionSaveMode.NEW);


			}

			for (int i = 0; i < roList.size(); i++){
				roList.get(i).onUpdate();
			}
		}
	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseError(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseError(IRequest iReq) {
		System.out.println("Failed to retrieve current user permissions");

	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#fail(edu.wpi.cs.wpisuitetng.network.models.IRequest, java.lang.Exception)
	 */
	@Override
	public void fail(IRequest iReq, Exception exception) {
		System.out.println("Failed to retrieve current user permissions2");

	}

	/**
	 * Gets the project users.
	 *
	 * @return the project users
	 */
	public static User[] getProjectUsers(){
		return allCoreUsers;
	}

	/**
	 * Gets the user.
	 *
	 * @return the user
	 */
	public static UserPermission getUser(){
		return user;
	}
}
