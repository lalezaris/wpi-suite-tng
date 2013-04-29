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
 * Controller for the button which when pushed will add an assignee.
 *
 * @author Joe Spicola
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
	 * Construct an AddAssigneeController.
	 *
	 * @param assigneeView the assignee view
	 */
	public AddAssigneeController(AssigneeView assigneeView) {
		view = assigneeView;

	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		allUserAL = view.getAllUserAL();
		assignedUserAL = view.getAssignedUserAL();
		selectedUsers = new ArrayList<String> (view.getAllUserList().getSelectedValuesList());
		selectedUsersIndex = view.getAllUserList().getSelectedIndices();
		if(selectedUsers.size() != 0){
			/*
			 * Iterates through loop backward, in order to remove elements from allusers without messing
			 * up the earlier indexes.
			 */
			for(int i = (selectedUsersIndex.length - 1); i >= 0; i--) {
				assignedUserAL.add(selectedUsers.get(i));//append selected user to assigned	
			}
			//add array list to the list models
			view.setAssigneeList(assignedUserAL);
			view.updateNotAssigned();
			view.setButtonPressed(true);
			view.refreshAllBackgrounds();
		}
	}

}
