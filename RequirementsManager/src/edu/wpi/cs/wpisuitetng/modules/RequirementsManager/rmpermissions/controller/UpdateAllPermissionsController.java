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
 *  CDUNKERS
 **************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.controller;

import java.awt.event.ActionEvent;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.DefaultListModel;
import javax.swing.JList;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RMPermissionsLevel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.UserPermissionPanel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.model.PermissionModel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.observers.CurrentUserPermissions;

/**
 * Action to update all permissions.
 *
 * @author CDUNKERS
 *
 * @version Apr 4, 2013
 *
 */
@SuppressWarnings({"rawtypes","serial"})
public class UpdateAllPermissionsController extends AbstractAction {

	protected UserPermissionPanel panel;
	protected JList noneUsers,updateUsers,adminUsers;
	private PermissionModel permModel;

	/**
	 * Instantiates a new update all permissions controller.
	 *
	 * @param panel the UserPermissionPanel
	 * @param permModel the PermissionModel
	 */
	public UpdateAllPermissionsController(UserPermissionPanel panel, PermissionModel permModel){
		this.panel = panel;
		this.noneUsers = panel.getNoneUsers();
		this.updateUsers = panel.getUpdateUsers();
		this.adminUsers = panel.getAdminUsers();
		this.permModel = permModel;
		//		putValue(MNEMONIC_KEY, KeyEvent.VK_P);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent ae) {
		if (permModel.isGotUsers()
				&& permModel.isGotPermissions()
				&& CurrentUserPermissions.doesUserHavePermissionMaster(RMPermissionsLevel.ADMIN)){

			CurrentUserPermissions.updateCurrentUserPermissions();

			//gets all model for each of the list
			DefaultListModel noneListModel = (DefaultListModel) noneUsers.getModel();
			DefaultListModel updateListModel = (DefaultListModel) updateUsers.getModel();
			DefaultListModel adminListModel = (DefaultListModel) adminUsers.getModel();

			//gets the list of items in each of the lists from the model
			List<String> allNoneUsers = this.getAllElementsInModel(noneListModel);
			List<String> allUpdateUsers = this.getAllElementsInModel(updateListModel);
			List<String> allAdminUsers = this.getAllElementsInModel(adminListModel);

			//updates the new 
			//panel.getView().setUpPanel(allAdminUsers, RMPermissionsLevel.ADMIN);
			//panel.updatePermissions(allUpdateUsers, RMPermissionsLevel.UPDATE);
			//panel.updatePermissions(allNoneUsers, RMPermissionsLevel.NONE);
			this.updatePermissions(allAdminUsers, RMPermissionsLevel.ADMIN);
			this.updatePermissions(allUpdateUsers, RMPermissionsLevel.UPDATE);
			this.updatePermissions(allNoneUsers, RMPermissionsLevel.NONE);

			//close tab
			panel.getView().getTab().getView().removeTabAt(panel.getView().getTab().getThisIndex());
		}
	}

	/**
	 * Update permissions.
	 *
	 * @param selected the selected names
	 * @param level the new permission level to put those names at
	 */
	public void updatePermissions(List<String> selected, RMPermissionsLevel level){

		SavePermissionsController controller = new SavePermissionsController(panel);

		System.out.println("calling update");
		for (int i = 0 ; i < selected.size() ; i ++)
			System.out.println("SEL:" + ((String)selected.get(i)));

		//This loop goes through the selected names, and all the permissions
		//and if there is a match, it updates that permission to LEVEL (an input to this function)
		//and saves the permission
		for (int i = 0 ; i < this.permModel.getPermissions().length ; i ++){
			for (int j = 0 ; j < selected.size() ; j ++){
				System.out.println("IS " + this.permModel.getPermissions()[i].getUsername() + " = TO " + (String)selected.get(j));
				if ( ((String)selected.get(j)).equals(this.permModel.getPermissions()[i].getUsername()) && this.permModel.getPermissions()[i].getPermissions() != level){

					String me = ConfigManager.getConfig().getUserName();
					Date now = new Date();

					String m = "[" + DateFormat.getDateTimeInstance().format(now) + "] CHANGE: " + me + " changed " + this.permModel.getPermissions()[i].getUsername() + 
							" status from " + this.permModel.getPermissions()[i].getPermissions() + " to " + level ;
					this.permModel.getPermissions()[i].setMessage(m);

					this.permModel.getPermissions()[i].setPermissions(level);
					controller.save(this.permModel.getPermissions()[i], PermissionSaveMode.UPDATE);					
				}	
			}
		}
	}

	/**
	 * The function takes a DefaultListModel and converts it to a list of string.
	 *
	 * @param model the model to be converted
	 * @return a list of the items in the model
	 */
	private List<String> getAllElementsInModel(DefaultListModel model){
		List<String> modelElements = new ArrayList<String>();
		for(int i = 0; i < model.getSize(); i++){
			modelElements.add((String)model.getElementAt(i));
		}
		return modelElements;
	}

	/**
	 * The function takes in a List and takes all of the elements
	 * from the list and adds them to the default list model.
	 *
	 * @param newElements a list of the elements to be put into the model
	 * @return the model with the given elements
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	private DefaultListModel getNewModel(List<String> newElements){
		DefaultListModel newModel = new DefaultListModel();
		for(int i = 0; i < newElements.size(); i++){
			newModel.addElement((Object)newElements.get(i));
		}
		return newModel;
	}
}