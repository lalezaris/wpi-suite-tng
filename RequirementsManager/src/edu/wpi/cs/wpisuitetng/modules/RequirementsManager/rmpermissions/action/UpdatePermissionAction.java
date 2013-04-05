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
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.action;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.DefaultListModel;
import javax.swing.JList;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.RMPermissionsLevel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.CurrentUserPermissions;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.UserPermissionPanel;

/**
 * Insert Description Here
 *
 * @author CDUNKERS
 *
 * @version Apr 3, 2013
 *
 */
/**
 * The controller for the button to move a object to the Update Permissions list
 *
 * @author Chris Dunkers
 *
 * @version Apr 2, 2013
 *
 */
public class UpdatePermissionAction extends AbstractAction {
	
	protected JList noneUsers,updateUsers,adminUsers;
	protected UserPermissionPanel panel;
	
	public UpdatePermissionAction(UserPermissionPanel panel){
		this.noneUsers = panel.getNoneUsers();
		this.updateUsers = panel.getUpdateUsers();
		this.adminUsers = panel.getAdminUsers();
		this.panel = panel;
	}  
	
	/* 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent ae) {

		//gets the selected items from the none and admin lists
		List<String> allSelectedUsers = new ArrayList<String>();
		List<String> selectedNoneUsers = noneUsers.getSelectedValuesList();
		List<String> selectedAdminUsers = adminUsers.getSelectedValuesList();
		
		//gets all model for each of the list
		DefaultListModel noneListModel = (DefaultListModel) noneUsers.getModel();
		DefaultListModel updateListModel = (DefaultListModel) updateUsers.getModel();
		DefaultListModel adminListModel = (DefaultListModel) adminUsers.getModel();
		
		//gets the list of items in each of the lists from the model
		List<String> allNoneUsers = this.getAllElementsInModel(noneListModel);
		List<String> allUpdateUsers = this.getAllElementsInModel(updateListModel);
		List<String> allAdminUsers = this.getAllElementsInModel(adminListModel);
		
		//remove the selected items from the none total list 
		List<String> newNoneUsers = allNoneUsers;
		newNoneUsers.removeAll(selectedNoneUsers);
		
		//create a new model with the remaining items
		DefaultListModel newNoneModel = this.getNewModel(newNoneUsers);
		
		//assign the new model
		noneUsers.setModel(newNoneModel);
		
		//remove the selected items from the admin total list 
		List<String> newAdminUsers = allAdminUsers;
		newAdminUsers.removeAll(selectedAdminUsers);
		
		//create a new model with the remaining items
		DefaultListModel newAdminModel = this.getNewModel(newAdminUsers);
		//assign the new model
		adminUsers.setModel(newAdminModel);
		
		//update the list for Update users to contain the selected items and convert it to a new default list model
		allUpdateUsers.addAll(selectedNoneUsers);
		allUpdateUsers.addAll(selectedAdminUsers);
		DefaultListModel newUpdateModel = this.getNewModel(allUpdateUsers);
		
		//Assign the new model			
		updateUsers.setModel(newUpdateModel);	
	}
	
	
	/**
	 * The function takes a DefaultListModel and converts it to a list of string
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
	 * the function takes in a List and takes all of the elements from the list and adds them to the default list model
	 * 
	 * @param newElements a list of the elements to be put into the model
	 * @return the model with the given elements
	 */
	private DefaultListModel getNewModel(List<String> newElements){
		DefaultListModel newModel = new DefaultListModel();
		for(int i = 0; i < newElements.size(); i++){
			newModel.addElement((Object)newElements.get(i));
		}
		return newModel;
	}
	
}
