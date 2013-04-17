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
 *  Joe Spicola
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.tabs.AssigneeView;


/**
 * Controller to remove an assigned user, and update display.
 *
 * @author Joe Spicola
 *
 * @version Apr 9, 2013
 *
 */
public class RemoveAssigneeController implements ActionListener {

	private AssigneeView view;
	private ArrayList<String> allUserAL;
	private ArrayList<String> assignedUserAL;
	private ArrayList<String> selectedUsers;
	private int[] selectedUsersIndex; //index of where the users are in the list
	
	/**
	 * Construct a RemoveAssigneeController.
	 * 
	 * @param assigneeView
	 */
	public RemoveAssigneeController(AssigneeView assigneeView) {
		this.view = assigneeView;

	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		this.allUserAL = view.getAllUserAL();
		this.assignedUserAL = view.getAssignedUserAL();
		this.selectedUsers = new ArrayList<String> (view.getAssignedUserList().getSelectedValuesList());
		this.selectedUsersIndex = view.getAssignedUserList().getSelectedIndices();
		
		/**
		 * Iterates through loop backward, in order to remove elements from allusers without messing
		 * up the earlier indexes.
		 */
		System.out.println(selectedUsersIndex.length);
		System.out.println(selectedUsers);
		System.out.println(selectedUsers.get(0));
		System.out.println(allUserAL);
		for(int i = (selectedUsersIndex.length - 1); i >= 0; i--) {
			assignedUserAL.remove(selectedUsersIndex[i]);//removes user according to selected users' index
			view.getAssignedUserLM().remove(i); //removes the user from the list model
		}
		
		//add array list to the list models
		//view.setUserList(allUserAL);
		System.out.println("Remove worked");
		view.setAssigneeList(assignedUserAL);
		view.updateNotAssigned();
		view.setButtonPressed(true);
		view.refreshAllBackgrounds();

	}
}
