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
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.controller;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.UserPermission;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RMPermissionsLevel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.UserPermissionPanel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.model.PermissionModel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.observers.CurrentUserPermissions;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.observers.PermissionsObserver;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.observers.UsersObserver;
import edu.wpi.cs.wpisuitetng.modules.core.models.Role;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;


/**
 * The controller to set up PermissionsPanel.
 * 
 * @author Chris Hanna
 * 
 */
public class SetUpPermissionsPanelController {

	private UserPermissionPanel panel;
	private PermissionModel model;
	
	/**
	 * Instantiates a new sets the up permissions panel controller.
	 *
	 * @param panel the UserPermissionPanel
	 * @param model the PermissionModel
	 */
	public SetUpPermissionsPanelController(UserPermissionPanel panel, PermissionModel model){
		this.panel = panel;
		this.model = model;
	}
	
	/**
	 * Set up the panel.
	 */
	public void setUp(){
		sendForUsers();
		sendForPermissions();
	}
	
	/**
	 * Send a request to get all the users from the server.
	 */
	private void sendForUsers(){
		Request request;
		request = Network.getInstance().makeRequest("core/user", HttpMethod.GET);
		request.addObserver(new UsersObserver(this));
		request.send();
	}
	
	/**
	 * Send a request to get all the permissions from the server.
	 */
	private void sendForPermissions(){
		Request request;
		request = Network.getInstance().makeRequest("requirementsmanager/permissions", HttpMethod.GET);
		request.addObserver(new PermissionsObserver(this));
		request.send();
	}
	
	/**
	 * An observer got a response, and wants to update the known users.
	 *
	 * @param users the users
	 */
	public void recieveServerUsers(User[] users){
		this.model.setUsers(users);
		this.model.setGotUsers(true);
		setUpPanel();
	}
	
	
	/**
	 * An observer got a response, and wants to update the known permissions.
	 *
	 * @param perms the perms
	 */
	public void receiveServerPermissions(UserPermission[] perms){
		this.model.setPermissions(perms);
		this.model.setGotPermissions(true);
		setUpPanel();
	}
	
	/**
	 * Sets up the panel.
	 */
	@SuppressWarnings("unchecked")
	private void setUpPanel(){
		
		if (this.model.isGotPermissions() && this.model.isGotUsers()){
			List<String> none = new ArrayList<String>();
			List<String> admin = new ArrayList<String>();
			List<String> update = new ArrayList<String>();
			
			SavePermissionsController controller = new SavePermissionsController(this.panel);
			for (int i = 0 ; i < this.model.getUsers().length ; i ++){
				boolean hasPermission = false;
				for (int j = 0 ; j < this.model.getPermissions().length ; j ++){
					if (this.model.getUsers()[i].getUsername().equals(this.model.getPermissions()[j].getUsername())){
						hasPermission = true;
						
						switch (this.model.getPermissions()[j].getPermissions()){
							case ADMIN: admin.add(this.model.getUsers()[i].getUsername());
							break;
							case UPDATE: update.add(this.model.getUsers()[i].getUsername());
							break;
							case NONE: none.add(this.model.getUsers()[i].getUsername());
							break;
						}
						
					}
					
				}
				
				if (!hasPermission){
					if (this.model.getUsers()[i].getRole() == Role.ADMIN){
						controller.save(new UserPermission(this.model.getUsers()[i].getUsername(),RMPermissionsLevel.ADMIN)
						, PermissionSaveMode.NEW);
						admin.add(this.model.getUsers()[i].getUsername());
					} else {
						controller.save(new UserPermission(this.model.getUsers()[i].getUsername(),RMPermissionsLevel.NONE)
								, PermissionSaveMode.NEW);
						none.add(this.model.getUsers()[i].getUsername());
					}
				}
			}
			
			this.panel.setNoneUsersList( panel.getView().getNewModel(none) );
			this.panel.setUpdateUsersList( panel.getView().getNewModel(update) );
			this.panel.setAdminUsersList( panel.getView().getNewModel(admin));
			
			this.panel.getNoneUsers().setModel(this.panel.getNoneUsersList());
			this.panel.getUpdateUsers().setModel(this.panel.getUpdateUsersList());
			this.panel.getAdminUsers().setModel(this.panel.getAdminUsersList());
		}
		
		if (this.model.isGotPermissions() || this.model.isGotUsers()){
			CurrentUserPermissions.updateCurrentUserPermissions();
		}
	}
}