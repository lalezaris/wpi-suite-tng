/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.model;

import java.util.ArrayList;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.UserPermission;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;

/**
 * @author Chris Hanna
 *
 */
public class PermissionModel {

	private User[] users;
	private UserPermission[] permissions;
	private boolean gotUsers;
	private boolean gotPermissions;
	
	
	public PermissionModel(){
		this.gotUsers = false;
		this.gotPermissions = false;
	}

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
	 * @return the users
	 */
	public User[] getUsers() {
		return users;
	}

	/**
	 * @param users the users to set
	 */
	public void setUsers(User[] users) {
		this.users = users;
	}

	/**
	 * @return the permissions
	 */
	public UserPermission[] getPermissions() {
		return permissions;
	}

	/**
	 * @param permissions the permissions to set
	 */
	public void setPermissions(UserPermission[] permissions) {
		this.permissions = permissions;
	}

	/**
	 * @return the gotUsers
	 */
	public boolean isGotUsers() {
		return gotUsers;
	}

	/**
	 * @param gotUsers the gotUsers to set
	 */
	public void setGotUsers(boolean gotUsers) {
		this.gotUsers = gotUsers;
	}

	/**
	 * @return the gotPermissions
	 */
	public boolean isGotPermissions() {
		return gotPermissions;
	}

	/**
	 * @param gotPermissions the gotPermissions to set
	 */
	public void setGotPermissions(boolean gotPermissions) {
		this.gotPermissions = gotPermissions;
	}
	
	
	
	
}
