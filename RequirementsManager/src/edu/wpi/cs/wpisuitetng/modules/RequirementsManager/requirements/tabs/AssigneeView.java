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
 * Joe Spicola
 * Sam Lalezari
 **************************************************/

package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.tabs;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.AddAssigneeController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.RemoveAssigneeController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.observers.CurrentUserPermissions;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;

/**
 * The Class to hold AssigneeView.
 *
 * @author Sam Lalezari
 *
 * @version Apr 7, 2013
 *
 */
@SuppressWarnings("serial")
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


	private boolean isButtonPressed; 

	/**
	 * Instantiates a new assignee view.
	 *
	 * @param req the requirement
	 */
	@SuppressWarnings("serial")
	public AssigneeView(Requirement req){
		FlowLayout flowLayout = new FlowLayout();
		flowLayout.setAlignOnBaseline(true);
		this.setLayout(flowLayout);
		isButtonPressed = false;

		allUserAL = new ArrayList<String>();

		assignedUserAL = req.getAssignee();

		allUserLM = new DefaultListModel<String>();
		assignedUserLM = new DefaultListModel<String>();

		User[] projectUsers = CurrentUserPermissions.getProjectUsers();

		System.out.println();
		System.out.println("assignedUserLM: " + assignedUserAL);
		System.out.println(">> FILLING projectUsers ArrayList <<");
		for(int i=0;i<projectUsers.length;i++){
			System.out.println("USER: " + projectUsers[i].getUsername());
			if(!assignedUserAL.contains(projectUsers[i].getUsername())){
				System.out.println("     added to allUserAL");
				allUserAL.add(projectUsers[i].getUsername());
			}
		}
		for(int i=0;i<allUserAL.size();i++){
			allUserLM.addElement(allUserAL.get(i));
		}
		System.out.println(">> DONE FILLING projectUsers ArrayList <<");
		System.out.println();

		allUserList = new JList<String>(allUserLM);
		assignedUserList = new JList<String>(assignedUserLM);


		buttonPanel = new JPanel(){
			@Override
			public Component.BaselineResizeBehavior getBaselineResizeBehavior() {
				return Component.BaselineResizeBehavior.CONSTANT_ASCENT;
			}

			@Override
			public int getBaseline(int width, int height) {
				return 0;
			}
		};
		buttonPanel.setLayout(new GridLayout(2,1,0,5));

		btnAdd = new    JButton("ADD");
		btnAdd.addActionListener(new AddAssigneeController(this));

		btnRemove = new JButton("REMOVE");
		btnRemove.addActionListener(new RemoveAssigneeController(this));

		buttonPanel.add(btnAdd);
		buttonPanel.add(btnRemove);

		allUserList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		assignedUserList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		JPanel leftPanel = new JPanel()	{
			@Override
			public Component.BaselineResizeBehavior getBaselineResizeBehavior() {
				return Component.BaselineResizeBehavior.CONSTANT_ASCENT;
			}

			@Override
			public int getBaseline(int width, int height) {
				return 0;
			}
		};
		leftPanel.setLayout(new BorderLayout());
		JLabel leftLabel = new JLabel("Not Assigned");
		leftPanel.add(leftLabel, BorderLayout.NORTH);
		allUserList.setAlignmentX(CENTER_ALIGNMENT);
		leftPanel.add(allUserList);
		this.add(leftPanel, BorderLayout.CENTER);

		this.add(buttonPanel);

		JPanel rightPanel = new JPanel(){	
			@Override
			public Component.BaselineResizeBehavior getBaselineResizeBehavior() {
				return Component.BaselineResizeBehavior.CONSTANT_ASCENT;
			}

			@Override
			public int getBaseline(int width, int height) {
				return 0;
			}
		};
		rightPanel.setLayout(new BorderLayout());
		JLabel rightLabel = new JLabel("Assigned");
		rightPanel.add(rightLabel, BorderLayout.NORTH);
		assignedUserList.setAlignmentX(CENTER_ALIGNMENT);
		rightPanel.add(assignedUserList, BorderLayout.CENTER);
		leftPanel.setAlignmentX(CENTER_ALIGNMENT);

		this.add(rightPanel);
	}

	/**
	 * Returns button object that adds users from a requirement.
	 * 
	 * @return The button that adds users from a requirement
	 */
	public JButton getBtnAdd(){
		return this.btnAdd;
	}

	/**
	 * Returns button object that removes users from a requirement.
	 * 
	 * @return button that removes users from a requirement
	 */
	public JButton getBtnRemove(){
		return this.btnRemove;
	}

	/**
	 * Set the Assignee list.
	 * 
	 * @param assignee
	 */
	public void setAssigneeList(ArrayList<String> assignee) {
		this.assignedUserAL = assignee;
		Collections.sort(assignedUserAL);

		assignedUserLM.clear();
		for(String s:assignedUserAL){
			assignedUserLM.addElement(s);
		}
	}

	/**
	 * Sets the array list of all users.
	 *
	 * @param all the new all users list
	 */
	public void setAllList(ArrayList<String> all) {
		// TODO Auto-generated method stub
		this.allUserAL = all;
		Collections.sort(allUserAL);

		allUserLM.clear();
		for(String s:allUserAL){
			allUserLM.addElement(s);
		}
	}

	/**
	 * Sets the array list of all users not assigned to the requirement.
	 * 
	 * @param all users who are not assigned to the requirement
	 */
	public void setAllUserList(ArrayList<String> users){
		this.allUserAL = users;

	}

	/**
	 * Returns the array list containing all users.
	 * 
	 * @return the array list containing all users
	 */
	public ArrayList<String> getAllUserAL() {
		return allUserAL;
	}

	/**
	 * Returns the array list containing users assigned to this requirement.
	 * 
	 * @return the array list containing users assigned to this requirement
	 */
	public ArrayList<String> getAssignedUserAL() {
		return assignedUserAL;
	}

	/**
	 * Get a list of all users.
	 * 
	 * @return a list of all users
	 */
	public JList<String> getAllUserList() {
		return allUserList;
	}

	/**
	 * Returns allUserLM for editing purposes.
	 * 
	 * @return the allUserLM
	 */
	public DefaultListModel<String> getAllUserLM() {
		return allUserLM;
	}

	/**
	 * Returns allUserLM for editing purposes.
	 * 
	 * @return the allUserLM
	 */
	public DefaultListModel<String> getAssignedUserLM() {
		return assignedUserLM;
	}

	/**
	 * @return the allUserList
	 */
	public JList<String> getAssignedUserList() {
		return assignedUserList;
	}

	/**
	 * @return the isButtonPressed
	 */
	public boolean isButtonPressed() {
		return isButtonPressed;
	}

	/**
	 * @param isButtonPressed: the isButtonPressed to set
	 */
	public void setButtonPressed(boolean isButtonPressed) {
		this.isButtonPressed = isButtonPressed;
	}

	/**
	 * Updates the not assigned list
	 * 
	 */
	public void updateNotAssigned() {
		User[] projectUsers = CurrentUserPermissions.getProjectUsers();

		allUserAL.clear();
		for(int i=0;i<projectUsers.length;i++){
			if(!assignedUserAL.contains(projectUsers[i].getUsername())){
				allUserAL.add(projectUsers[i].getUsername());
			}
		}
		
		allUserLM.clear();

		for(int i=0;i<allUserAL.size();i++){
			allUserLM.addElement(allUserAL.get(i));
		}		
	}

}