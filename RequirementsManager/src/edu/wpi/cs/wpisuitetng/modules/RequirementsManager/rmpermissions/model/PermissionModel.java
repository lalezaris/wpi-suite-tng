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
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.model;

import java.util.ArrayList;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.UserPermission;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;

/**
 * The Class to hold PermissionModel.
 * 
 * @author Chris Hanna
 */
public class PermissionModel {

	private User[] users;
	private UserPermission[] permissions;
	private boolean gotUsers;
	private boolean gotPermissions;


	/**
	 * Instantiates a new permission model.
	 */
	public PermissionModel(){
		this.gotUsers = false;
		this.gotPermissions = false;
	}

	/**
	 * Adds the permission.
	 *
	 * @param perm the UserPermission
	 */
	public void addPermission(UserPermission perm){
		ArrayList<UserPermission> all2 = new ArrayList<UserPermission>();
		boolean hasName = false;
		for (int i =0 ; i < permissions.length ; i ++){
			all2.add(permissions[i]);
			if (all2.get(i).getUsername().equals(perm.getUsername()))
				hasName = true;
		}
		if (!hasName)
			all2.add(perm);

		permissions = new UserPermission[all2.size()];
		for (int i = 0 ; i < permissions.length;i++){
			permissions[i] = all2.get(i);
		}
	}

	/**
	 * Gets the users.
	 *
	 * @return the users
	 */
	public User[] getUsers() {
		return users;
	}

	/**
	 * Sets the users.
	 *
	 * @param users the users to set
	 */
	public void setUsers(User[] users) {
		this.users = users;
	}

	/**
	 * Gets the permissions.
	 *
	 * @return the permissions
	 */
	public UserPermission[] getPermissions() {
		return permissions;
	}

	/**
	 * Sets the permissions.
	 *
	 * @param permissions the permissions to set
	 */
	public void setPermissions(UserPermission[] permissions) {
		this.permissions = permissions;
	}

	/**
	 * Checks if is got users.
	 *
	 * @return the gotUsers
	 */
	public boolean isGotUsers() {
		return gotUsers;
	}

	/**
	 * Sets the got users.
	 *
	 * @param gotUsers the gotUsers to set
	 */
	public void setGotUsers(boolean gotUsers) {
		this.gotUsers = gotUsers;
	}

	/**
	 * Checks if is got permissions.
	 *
	 * @return the gotPermissions
	 */
	public boolean isGotPermissions() {
		return gotPermissions;
	}

	/**
	 * Sets the got permissions.
	 *
	 * @param gotPermissions the gotPermissions to set
	 */
	public void setGotPermissions(boolean gotPermissions) {
		this.gotPermissions = gotPermissions;
	}
}