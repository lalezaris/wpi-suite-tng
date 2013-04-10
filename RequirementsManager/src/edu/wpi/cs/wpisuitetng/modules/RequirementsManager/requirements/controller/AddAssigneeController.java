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
 *  Spicola
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.tabs.AssigneeView;

/**
 * Insert Description Here
 *
 * @author Spicola
 *
 * @version Apr 7, 2013
 *
 */
public class AddAssigneeController implements ActionListener {
	
	private AssigneeView view;
	private ArrayList<String> allUserAL;
	private ArrayList<String> assignedUserAL;
	private ArrayList<String> selectedUsers;
	private int[] selectedUsersIndex; //index of where the users are in the list
	
	/**
	 * Enter Description here.
	 * 
	 * @param assigneeView
	 */
	public AddAssigneeController(AssigneeView assigneeView) {
		this.view = assigneeView;

	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		this.allUserAL = view.getAllUserAL();
		this.assignedUserAL = view.getAssignedUserAL();
		this.selectedUsers = new ArrayList<String> (view.getAllUserList().getSelectedValuesList());
		this.selectedUsersIndex = view.getAllUserList().getSelectedIndices();
		
		/**
		 * Iterates through loop backward, in order to remove elements from allusers without messing
		 * up the earlier indexes.
		 */
		for(int i = 0; i < selectedUsersIndex.length; i++) {
			assignedUserAL.add(selectedUsers.get(i));//append selected user to assigned
			allUserAL.remove(selectedUsersIndex[i]);//removes user according to selected users' index
			view.getAllUserLM().remove(i); //removes the user from the list model
		}
		
		view.setAssigneeList(assignedUserAL);
		view.setAllUserList(allUserAL);
	}

}
