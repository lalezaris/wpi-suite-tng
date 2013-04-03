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

/**
 * Insert Description Here
 *
 * @author CDUNKERS
 *
 * @version Apr 3, 2013
 *
 */
/**
 * The controller for the button to move a object to the None Permissions list
 *
 * @author Chris Dunkers
 *
 * @version Apr 2, 2013
 *
 */
public class NonePermissionAction extends AbstractAction {
	
	protected JList noneUsers,updateUsers,adminUsers;
	
	public NonePermissionAction(JList noneUsers,JList updateUsers,JList adminUsers){
//		this.projectUsers = projectUsers;
		this.noneUsers = noneUsers;
		this.updateUsers = updateUsers;
		this.adminUsers = adminUsers;
	}  
	
	/* 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent ae) {
//		List<String> selectedProjectUsers = projectUsers.getSelectedValuesList();
		List<String> selectedUpdateUsers = updateUsers.getSelectedValuesList();
		List<String> selectedAdminUsers = adminUsers.getSelectedValuesList();
		
//		DefaultListModel projectListModel = (DefaultListModel) projectUsers.getModel();
		DefaultListModel noneListModel = (DefaultListModel) noneUsers.getModel();
		DefaultListModel updateListModel = (DefaultListModel) updateUsers.getModel();
		DefaultListModel adminListModel = (DefaultListModel) adminUsers.getModel();
		
//		List<String> allProjectUsers = this.getAllElementsInModel(projectListModel);
		List<String> allNoneUsers = this.getAllElementsInModel(noneListModel);
		List<String> allUpdateUsers = this.getAllElementsInModel(updateListModel);
		List<String> allAdminUsers = this.getAllElementsInModel(adminListModel);
		
//		List<String> newProjectUsers = allProjectUsers;
//		newProjectUsers.removeAll(selectedProjectUsers);
//		DefaultListModel newProjectModel = this.getNewModel(newProjectUsers);
//		projectUsers.setModel(newProjectModel);
		
		List<String> newUpdateUsers = allUpdateUsers;
		newUpdateUsers.removeAll(selectedUpdateUsers);
		DefaultListModel newUpdateModel = this.getNewModel(newUpdateUsers);
		updateUsers.setModel(newUpdateModel);
		
		List<String> newAdminUsers = allAdminUsers;
		newAdminUsers.removeAll(selectedAdminUsers);
		DefaultListModel newAdminModel = this.getNewModel(newAdminUsers);
		adminUsers.setModel(newAdminModel);

//		allNoneUsers.addAll(selectedProjectUsers);
		allNoneUsers.addAll(selectedUpdateUsers);
		allNoneUsers.addAll(selectedAdminUsers);
		DefaultListModel newNoneModel = this.getNewModel(allNoneUsers);
					
		noneUsers.setModel(newNoneModel);	
	}
	
	
	private List<String> getAllElementsInModel(DefaultListModel model){
		List<String> modelElements = new ArrayList<String>();
		for(int i = 0; i < model.getSize(); i++){
			modelElements.add((String)model.getElementAt(i));
		}
		return modelElements;
	}
	
	private DefaultListModel getNewModel(List<String> newElements){
		DefaultListModel newModel = new DefaultListModel();
		for(int i = 0; i < newElements.size(); i++){
			newModel.addElement((Object)newElements.get(i));
		}
		return newModel;
	}
}
