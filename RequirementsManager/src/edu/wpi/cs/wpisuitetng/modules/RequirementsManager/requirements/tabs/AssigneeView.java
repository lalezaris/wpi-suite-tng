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
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementView;
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
public class AssigneeView extends RequirementTab{

	private ArrayList<String> allUserAL;
	private ArrayList<String> assignedUserAL;
	private JList<String> allUserList;
	private DefaultListModel<String> allUserLM;
	private DefaultListModel<String> assignedUserLM;
	private JList<String> assignedUserList;
	private JButton btnAdd;
	private JButton btnRemove;
	private JPanel buttonPanel;
	private RequirementView parent;

	private boolean isButtonPressed; 

	/**
	 * Instantiates a new assignee view.
	 *
	 * @param p the requirement view for the assignee view
	 */
	public AssigneeView(RequirementView p){
		this.parent = p;
		FlowLayout flowLayout = new FlowLayout(FlowLayout.LEFT);
		flowLayout.setAlignOnBaseline(true);
		this.setLayout(flowLayout);
		isButtonPressed = false;

		allUserAL = new ArrayList<String>();

		allUserLM = new DefaultListModel<String>();
		assignedUserLM = new DefaultListModel<String>();

		allUserList = new JList<String>(allUserLM);
		allUserList.setFixedCellWidth(200);
		assignedUserList = new JList<String>(assignedUserLM);
		assignedUserList.setFixedCellWidth(200);

		buttonPanel = new JPanel(){
			/**
			 * @see javax.swing.JComponent#getBaselineResizeBehavior()
			 */
			@Override
			public Component.BaselineResizeBehavior getBaselineResizeBehavior() {
				return Component.BaselineResizeBehavior.CONSTANT_ASCENT;
			}

			/**
			 * @see javax.swing.JComponent#getBaseline(int, int)
			 */
			@Override
			public int getBaseline(int width, int height) {
				return 0;
			}
		};
		buttonPanel.setLayout(new GridLayout(2,1,0,5));

		btnAdd = new JButton("ADD");
		//btnAdd.addFocusListener(this);

		btnRemove = new JButton("REMOVE");
		//btnRemove.addFocusListener(this);

		buttonPanel.add(btnAdd);
		buttonPanel.add(btnRemove);

		allUserList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		assignedUserList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		JPanel leftPanel = new JPanel()	{
			/**
			 * @see javax.swing.JComponent#getBaselineResizeBehavior()
			 */
			@Override
			public Component.BaselineResizeBehavior getBaselineResizeBehavior() {
				return Component.BaselineResizeBehavior.CONSTANT_ASCENT;
			}

			/**
			 * @see javax.swing.JComponent#getBaseline(int, int)
			 */
			@Override
			public int getBaseline(int width, int height) {
				return height/2 - buttonPanel.getHeight()/2;
			}
		};
		
		

		leftPanel.setLayout(new BorderLayout());
		JLabel leftLabel = new JLabel("Not Assigned:");
		leftPanel.add(leftLabel, BorderLayout.NORTH);
		allUserList.setAlignmentX(CENTER_ALIGNMENT);
		allUserList.setFixedCellWidth(180);
		

		JScrollPane leftScrollPane = new JScrollPane(allUserList);
		leftScrollPane.setPreferredSize(new Dimension(200,300));
		
		leftPanel.add(leftScrollPane, BorderLayout.CENTER);
		this.add(leftPanel);

		this.add(buttonPanel);

		JPanel rightPanel = new JPanel(){	
			/**
			 * @see javax.swing.JComponent#getBaselineResizeBehavior()
			 */
			@Override
			public Component.BaselineResizeBehavior getBaselineResizeBehavior() {
				return Component.BaselineResizeBehavior.CONSTANT_ASCENT;
			}

			/**
			 * @see javax.swing.JComponent#getBaseline(int, int)
			 */
			@Override
			public int getBaseline(int width, int height) {
				return height/2 - buttonPanel.getHeight()/2;
			}
		};
		rightPanel.setLayout(new BorderLayout());
		JLabel rightLabel = new JLabel("Assigned:");
		rightPanel.add(rightLabel, BorderLayout.NORTH);
		assignedUserList.setAlignmentX(CENTER_ALIGNMENT);
		assignedUserList.setFixedCellWidth(180);
		
		JScrollPane rightScrollPane = new JScrollPane(assignedUserList);
		rightScrollPane.setPreferredSize(new Dimension(200,300));
		
		rightPanel.add(rightScrollPane, BorderLayout.CENTER);
		leftPanel.setAlignmentX(CENTER_ALIGNMENT);

		this.add(rightPanel);
	}

	/**
	 * Gets button object that adds users from a requirement.
	 * 
	 * @return The button that adds users from a requirement
	 */
	public JButton getBtnAdd(){
		return this.btnAdd;
	}

	/**
	 * Gets button object that removes users from a requirement.
	 * 
	 * @return button that removes users from a requirement
	 */
	public JButton getBtnRemove(){
		return this.btnRemove;
	}

	/**
	 * Set the Assignee list.
	 * 
	 * @param assignee the assignee to add to the list
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
		this.allUserAL = all;
		Collections.sort(allUserAL);

		allUserLM.clear();
		for(String s:allUserAL){
			allUserLM.addElement(s);
		}
	}

	/**
	 * Sets the lists.
	 */
	public void setLists(){
		User[] projectUsers = CurrentUserPermissions.getProjectUsers();

		if(projectUsers != null){
			for(int i=0;i<projectUsers.length;i++){
				if(!assignedUserAL.contains(projectUsers[i].getUsername())){
					allUserAL.add(projectUsers[i].getUsername());
				}
			}
		}
		for(int i=0;i<allUserAL.size();i++){
			allUserLM.addElement(allUserAL.get(i));
		}
	}

	/**
	 * Sets the array list of all users not assigned to the requirement.
	 * 
	 * @param users all users who are not assigned to the requirement
	 */
	public void setAllUserList(ArrayList<String> users){
		this.allUserAL = users;

	}

	/**
	 * Gets the array list containing all users.
	 * 
	 * @return the array list containing all users
	 */
	public ArrayList<String> getAllUserAL() {
		return allUserAL;
	}

	/**
	 * Gets the array list containing users assigned to this requirement.
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
	 * Gets assigned user list
	 * 
	 * @return the allUserList
	 */
	public JList<String> getAssignedUserList() {
		return assignedUserList;
	}

	/**
	 * Returns if button is pressed
	 * 
	 * @return the isButtonPressed
	 */
	public boolean isButtonPressed() {
		return isButtonPressed;
	}

	/**
	 * Sets if button is pressed
	 * 
	 * @param isButtonPressed the isButtonPressed to set
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
	
	/**
	 * Sets the background colors.
	 *
	 * @param c the new background colors
	 */
	public void setBackgroundColors(Color c) {
		allUserList.setBackground(c);
		assignedUserList.setBackground(c);
	}
	
	/**
	 * Gets the background color.
	 *
	 * @return the background color
	 */
	public Color getBackgroundColor() {
		return allUserList.getBackground();
	}


	@Override
	public String getTabTitle() {
		return "Assigned To";
	}

	@Override
	public ImageIcon getImageIcon() {
		return new ImageIcon();
	}

	@Override
	public String getTooltipText() {
		return "Add and modify assignees";
	}

	/* 
	 * call the background color refresher

	/**
	 * Refresh all backgrounds.
	 */
	public void refreshAllBackgrounds() {
		parent.getReqModel().updateBackgrounds();
	}
}