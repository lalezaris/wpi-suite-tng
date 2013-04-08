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
 *  Sam Lalezari
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.tabs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.observers.CurrentUserPermissions;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;

/**
 * Insert Description Here
 *
 * @author Sam Lalezari
 *
 * @version Apr 7, 2013
 *
 */
public class AssigneeView extends JPanel{
	
	private ArrayList<String> allUserAL;
	private ArrayList<String> assignedUserAL;
	private JList<String> allUserList;
	private DefaultListModel<String> allUserLM;
	private DefaultListModel<String> assignedUserLM;
	private JList<String> assignedUserList;
	private JButton btnAdd;
	private JButton btnRemove;
	private JPanel buttonPanel;
	
	public AssigneeView(Requirement req){
		this.setLayout(new FlowLayout());

		assignedUserAL = req.getAssignee();
		
		allUserLM = new DefaultListModel<String>();
		assignedUserLM = new DefaultListModel<String>();
		
		User[] projectUsers = CurrentUserPermissions.getProjectUsers();

		System.out.println();
		System.out.println(">> FILLING projectUsers ArrayList <<");
		for(int i=0;i<projectUsers.length;i++){
			System.out.println("USER: " + projectUsers[i].getName());
			allUserLM.addElement(projectUsers[i].getName()); 	//TODO pull list from database
		}
		System.out.println(">> DONE FILLING projectUsers ArrayList <<");
		System.out.println();
		
		allUserList = new JList<String>(allUserLM);
		assignedUserList = new JList<String>(assignedUserLM);
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(2,1,0,5));
		
		btnAdd = new    JButton("ADD");
		btnRemove = new JButton("REMOVE");
		buttonPanel.add(btnAdd);
		buttonPanel.add(btnRemove);
		
		this.add(allUserList);
		this.add(buttonPanel);
		this.add(assignedUserList);
	}
	
	/**
	 * Returns button object that adds users from a requirement.
	 * 
	 * @return btnAdd
	 */
	public JButton getBtnAdd(){
		return this.btnAdd;
	}
	/**
	 * Returns button object that removes users from a requirement
	 * 
	 * @return btnRemove
	 */
	public JButton getBtnRemove(){
		return this.btnRemove;
	}

	/**
	 * Enter description here.
	 * 
	 * @param assignee
	 */
	public void setAssigneeList(ArrayList<String> assignee) {
		this.assignedUserAL = assignee;
		Collections.sort(assignee);
		
		assignedUserLM.clear();
		for(String s:assignedUserAL){
			assignedUserLM.addElement(s);
		}
	}

	/**
	 * returns the array list containing all users
	 * @return the allUserAL
	 */
	public ArrayList<String> getAllUserAL() {
		return allUserAL;
	}

	/**
	 * returns the array list containing users assigned to this requirement
	 * @return the assignedUserAL
	 */
	public ArrayList<String> getAssignedUserAL() {
		return assignedUserAL;
	}

}
