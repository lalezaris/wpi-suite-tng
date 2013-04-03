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
 *  Chris Dunkers
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.action.AdminPermissionAction;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.action.UpdatePermissionAction;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.action.NonePermissionAction;

/**
 * The panel displayed when editing the permissions for the project users
 *
 * @author Chris Dunkers
 *
 * @version Apr 1, 2013
 *
 */
public class UserPermissionPanel extends JPanel {
	
	/* the parent view*/
	protected UserPermissionView view;
	
	/* the List's that will display the usernames*/
//	protected JList projectUsers;
	protected JList noneUsers;
	protected JList updateUsers;
	protected JList adminUsers;
	
	/* the Default list models for the lists*/
//	DefaultListModel projectUsersList;
	DefaultListModel noneUsersList;
	DefaultListModel updateUsersList;
	DefaultListModel adminUsersList;
	
	/* the Buttons for moving things between lists and updating the changes*/
	protected JButton btnNone;
	protected JButton btnUpdate;
	protected JButton btnAdmin;
	protected JButton updateBtn;
	
	/*layout manager for this panel*/
	protected GridBagLayout layout;
	
	protected static final int LABEL_ALIGNMENT = JLabel.TRAILING;
	

	/**
	 * Constructor
	 * 
	 * @param view the parent view of this panel
	 */
	public UserPermissionPanel(UserPermissionView view){
		this.view = view;
		
		addComponents();
	}
	
	private void addComponents(){
		/* create a panel to store everything in*/
		JPanel listPanel = new JPanel();
		
		/*initialize all of the components to be displayed*/
//		projectUsersList = new DefaultListModel();
//		projectUser = new JList(projectUsersList);
		noneUsersList = new DefaultListModel();
		noneUsers = new JList(noneUsersList);
		updateUsersList = new DefaultListModel();
		updateUsers = new JList(updateUsersList);
		adminUsersList = new DefaultListModel();
		adminUsers = new JList(adminUsersList);
		
		/*initialize all of the buttons to be displayed*/
		btnNone = new JButton(" Move to None ");
		btnNone.addActionListener(new NonePermissionAction(noneUsers,updateUsers,adminUsers));
		btnUpdate = new JButton("Move to Update");
		btnUpdate.addActionListener(new UpdatePermissionAction(noneUsers,updateUsers,adminUsers));
		btnAdmin = new JButton("Move to Admin ");
		btnAdmin.addActionListener(new AdminPermissionAction(noneUsers,updateUsers,adminUsers));
		updateBtn = new JButton("Update Users");
		
		/*labels for the components*/
//		JLabel lblProjectUsers = new JLabel("Project Users:", LABEL_ALIGNMENT);
		JLabel lblNoneUsers = new JLabel("None Users:", LABEL_ALIGNMENT);
		JLabel lblUpdateUsers = new JLabel("Update Users:", LABEL_ALIGNMENT);
		JLabel lblAdminUsers = new JLabel("Admin Users:", LABEL_ALIGNMENT);
		
		/*se the layout manager for this an the nested panel*/
		GridBagConstraints c = new GridBagConstraints();	
		layout = new GridBagLayout();	
		this.setLayout(layout);
		
		GridBagConstraints cPanel = new GridBagConstraints();	
		GridBagLayout layoutPanel = new GridBagLayout();	
		listPanel.setLayout(layoutPanel);
		
		/*add all of the components to the listPanel*/
//		cPanel.anchor = GridBagConstraints.FIRST_LINE_START; 
//		cPanel.gridx = 0;
//		cPanel.gridy = 0;
//		cPanel.weightx = 0.5;
//		cPanel.weighty = 0.5;
//		cPanel.gridwidth = 1;
//		cPanel.insets = new Insets(10,10,10,0); //top,left,bottom,right
//		listPanel.add(lblProjectUsers, cPanel);
//		
//		cPanel.anchor = GridBagConstraints.FIRST_LINE_START; 
//		cPanel.gridx = 0;
//		cPanel.gridy = 1;
//		cPanel.weightx = 0.5;
//		cPanel.weighty = 0.5;
//		cPanel.gridheight = 3;
//		cPanel.insets = new Insets(10,10,10,0); //top,left,bottom,right
//		listPanel.add(projectUsers, cPanel);
		
		cPanel.anchor = GridBagConstraints.FIRST_LINE_START; 
		cPanel.gridx = 0;
		cPanel.gridy = 1;
		cPanel.weightx = 0.5;
		cPanel.weighty = 0.5;
		cPanel.gridheight = 1;
		cPanel.insets = new Insets(10,10,10,0); //top,left,bottom,right
		listPanel.add(btnNone, cPanel);
		
		cPanel.anchor = GridBagConstraints.FIRST_LINE_START; 
		cPanel.gridx = 0;
		cPanel.gridy = 2;
		cPanel.weightx = 0.5;
		cPanel.weighty = 0.5;
		cPanel.gridheight = 1;
		cPanel.insets = new Insets(10,10,10,0); //top,left,bottom,right
		listPanel.add(btnUpdate, cPanel);
		
		cPanel.anchor = GridBagConstraints.FIRST_LINE_START; 
		cPanel.gridx = 0;
		cPanel.gridy = 3;
		cPanel.weightx = 0.5;
		cPanel.weighty = 0.5;
		cPanel.gridheight = 1;
		cPanel.insets = new Insets(10,10,10,0); //top,left,bottom,right
		listPanel.add(btnAdmin, cPanel);
		
		cPanel.anchor = GridBagConstraints.FIRST_LINE_START; 
		cPanel.gridx = 1;
		cPanel.gridy = 0;
		cPanel.weightx = 0.5;
		cPanel.weighty = 0.5;
		cPanel.gridwidth = 1;
		cPanel.insets = new Insets(10,10,10,0); //top,left,bottom,right
		listPanel.add(lblNoneUsers, cPanel);
		
		cPanel.anchor = GridBagConstraints.FIRST_LINE_START; 
		cPanel.gridx = 1;
		cPanel.gridy = 1;
		cPanel.weightx = 0.5;
		cPanel.weighty = 0.5;
		cPanel.gridheight = 3;
		cPanel.insets = new Insets(10,10,10,0); //top,left,bottom,right
		listPanel.add(noneUsers, cPanel);
		
		cPanel.anchor = GridBagConstraints.FIRST_LINE_START; 
		cPanel.gridx = 2;
		cPanel.gridy = 0;
		cPanel.weightx = 0.5;
		cPanel.weighty = 0.5;
		cPanel.gridwidth = 1;
		cPanel.insets = new Insets(10,10,10,0); //top,left,bottom,right
		listPanel.add(lblUpdateUsers, cPanel);
		
		cPanel.anchor = GridBagConstraints.FIRST_LINE_START; 
		cPanel.gridx = 2;
		cPanel.gridy = 1;
		cPanel.weightx = 0.5;
		cPanel.weighty = 0.5;
		cPanel.gridheight = 3;
		cPanel.insets = new Insets(10,10,10,0); //top,left,bottom,right
		listPanel.add(updateUsers, cPanel);
		
		cPanel.anchor = GridBagConstraints.FIRST_LINE_START; 
		cPanel.gridx = 3;
		cPanel.gridy = 0;
		cPanel.weightx = 0.5;
		cPanel.weighty = 0.5;
		cPanel.gridwidth = 1;
		cPanel.insets = new Insets(10,10,10,0); //top,left,bottom,right
		listPanel.add(lblAdminUsers, cPanel);
		
		cPanel.anchor = GridBagConstraints.FIRST_LINE_START; 
		cPanel.gridx = 3;
		cPanel.gridy = 1;
		cPanel.weightx = 0.5;
		cPanel.weighty = 0.5;
		cPanel.gridheight = 3;
		cPanel.insets = new Insets(10,10,10,0); //top,left,bottom,right
		listPanel.add(adminUsers, cPanel);
		
		c.anchor = GridBagConstraints.FIRST_LINE_START; 
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.gridwidth = 1;
		c.insets = new Insets(10,10,10,0); //top,left,bottom,right
		this.add(listPanel, c);
	}
	
}
