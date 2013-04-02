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
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

/**
 * Insert Description Here
 *
 * @author CDUNKERS
 *
 * @version Apr 1, 2013
 *
 */
public class UserPermissionPanel extends JPanel {
	
	protected UserPermissionView view;
	protected JList<String> projectUsers;
	protected JList<String> noneUsers;
	protected JList<String> updateUsers;
	protected JList<String> adminUsers;
	
	protected GridBagLayout layout;
	
	protected static final int LABEL_ALIGNMENT = JLabel.TRAILING;
	
	public UserPermissionPanel(UserPermissionView view){
		this.view = view;
		
		addComponents();
	}
	
	private void addComponents(){
		JPanel listPanel = new JPanel();
		
		String[] listOne = {"1", "2"};
		String[] listTwo = {"3", "4"};
		String[] listThree = {"5", "6"};
		String[] listFour = {"7", "8"};		
		
		projectUsers = new JList<String>(listOne);
		noneUsers = new JList<String>(listTwo);
		updateUsers = new JList<String>(listThree);
		adminUsers = new JList<String>(listFour);
		
		JLabel lblProjectUsers = new JLabel("Project Users:", LABEL_ALIGNMENT);
		JLabel lblNoneUsers = new JLabel("None Users:", LABEL_ALIGNMENT);
		JLabel lblUpdateUsers = new JLabel("Update Users:", LABEL_ALIGNMENT);
		JLabel lblAdminUsers = new JLabel("Admin Users:", LABEL_ALIGNMENT);
		
		GridBagConstraints c = new GridBagConstraints();	
		layout = new GridBagLayout();	
		this.setLayout(layout);
		
		GridBagConstraints cPanel = new GridBagConstraints();	
		GridBagLayout layoutPanel = new GridBagLayout();	
		listPanel.setLayout(layoutPanel);
		
		cPanel.anchor = GridBagConstraints.FIRST_LINE_START; 
		cPanel.gridx = 0;
		cPanel.gridy = 0;
		cPanel.weightx = 0.5;
		cPanel.weighty = 0.5;
		cPanel.gridwidth = 1;
		cPanel.insets = new Insets(10,10,10,0); //top,left,bottom,right
		listPanel.add(lblProjectUsers, cPanel);
		
		cPanel.anchor = GridBagConstraints.FIRST_LINE_START; 
		cPanel.gridx = 0;
		cPanel.gridy = 1;
		cPanel.weightx = 0.5;
		cPanel.weighty = 0.5;
		cPanel.gridwidth = 1;
		cPanel.insets = new Insets(10,10,10,0); //top,left,bottom,right
		listPanel.add(projectUsers, cPanel);
		
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
		cPanel.gridwidth = 1;
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
		cPanel.gridwidth = 1;
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
		cPanel.gridwidth = 1;
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
