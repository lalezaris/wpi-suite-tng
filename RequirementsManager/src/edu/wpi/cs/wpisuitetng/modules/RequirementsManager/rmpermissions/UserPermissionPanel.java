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

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * The panel displayed when editing the permissions for the project users.
 *
 * @author Chris Dunkers
 *
 * @version Apr 1, 2013
 *
 */
@SuppressWarnings({"rawtypes", "serial"})
public class UserPermissionPanel extends JPanel{

	boolean hasChanged = false;

	/* the parent view*/
	protected UserPermissionView view;

	/* the List's that will display the usernames*/
	protected JList lstNoneUsers;
	protected JList lstUpdateUsers;
	protected JList lstAdminUsers;

	/* the Default list models for the lists*/
	DefaultListModel noneUsersList;
	DefaultListModel updateUsersList;
	DefaultListModel adminUsersList;

	/* the Buttons for moving things between lists and updating the changes*/
	protected JButton btnNone;
	protected JButton btnUpdate;
	protected JButton btnAdmin;
	protected JButton btnUpdateAll;

	/*layout manager for this panel*/
	protected GridBagLayout layout;

	protected static final int LABEL_ALIGNMENT = JLabel.TRAILING;

	/**
	 * Constructor for UserPermissionPanel.
	 * 
	 * @param view the parent view of this panel
	 */
	public UserPermissionPanel(UserPermissionView view){
		this.view = view;

		addComponents();
	}

	/**
	 * Adds the components.
	 */
	@SuppressWarnings("unchecked")
	private void addComponents(){
		/* create a panel to store everything in*/
		JPanel listPanel = new JPanel();
		JPanel btnPanel = new JPanel();
		JPanel overallPanel = new JPanel();

		/*initialize all of the components to be displayed*/
		noneUsersList = new DefaultListModel();
		lstNoneUsers = new JList(noneUsersList);
		lstNoneUsers.setFixedCellWidth(250);
		updateUsersList = new DefaultListModel();
		lstUpdateUsers = new JList(updateUsersList);
		lstUpdateUsers.setFixedCellWidth(250);
		adminUsersList = new DefaultListModel();
		lstAdminUsers = new JList(adminUsersList);
		lstAdminUsers.setFixedCellWidth(250);


		/*initialize all of the buttons to be displayed*/
		btnNone = new JButton("Move to None");
		btnUpdate = new JButton("Move to Update");
		btnAdmin = new JButton("Move to Admin");
		btnUpdateAll = new JButton("Update Permissions");

		/*labels for the components*/
		JLabel lblNoneUsers = new JLabel("None Users:", LABEL_ALIGNMENT);
		JLabel lblUpdateUsers = new JLabel("Update Users:", LABEL_ALIGNMENT);
		JLabel lblAdminUsers = new JLabel("Admin Users:", LABEL_ALIGNMENT);

		/*set the layout manager for this and the nested panel*/
		GridBagConstraints c = new GridBagConstraints();	
		layout = new GridBagLayout();	
		this.setLayout(layout);

		GridBagConstraints cPanel = new GridBagConstraints();	
		GridBagLayout layoutPanel = new GridBagLayout();	
		listPanel.setLayout(layoutPanel);

		GridBagConstraints cBtn = new GridBagConstraints();	
		GridBagLayout layoutBtn = new GridBagLayout();	
		btnPanel.setLayout(layoutBtn);

		GridBagConstraints cOverall = new GridBagConstraints();	
		GridBagLayout layoutOverall = new GridBagLayout();	
		overallPanel.setLayout(layoutOverall);


		/*add all of the components to the btnPanel*/
		cBtn.anchor = GridBagConstraints.FIRST_LINE_START; 
		cPanel.fill = GridBagConstraints.HORIZONTAL;
		cPanel.gridx = 0;
		cPanel.gridy = 0;
		cPanel.weightx = 0.5;
		cPanel.weighty = 0.5;
		cPanel.gridheight = 1;
		cPanel.insets = new Insets(10,10,10,0); //top,left,bottom,right
		listPanel.add(btnNone, cPanel);

		cBtn.anchor = GridBagConstraints.FIRST_LINE_START;
		cPanel.fill = GridBagConstraints.HORIZONTAL;
		cPanel.gridx = 1;
		cPanel.gridy = 0;
		cPanel.weightx = 0.5;
		cPanel.weighty = 0.5;
		cPanel.gridheight = 1;
		cPanel.insets = new Insets(10,10,10,0); //top,left,bottom,right
		listPanel.add(btnUpdate, cPanel);

		cBtn.anchor = GridBagConstraints.FIRST_LINE_START; 
		cPanel.fill = GridBagConstraints.HORIZONTAL;
		cPanel.gridx = 2;
		cPanel.gridy = 0;
		cPanel.weightx = 0.5;
		cPanel.weighty = 0.5;
		cPanel.gridheight = 1;
		cPanel.insets = new Insets(10,10,10,0); //top,left,bottom,right
		listPanel.add(btnAdmin, cPanel);

		/*add all of the components to the listPanel*/
		cPanel.anchor = GridBagConstraints.FIRST_LINE_START; 
		cPanel.fill = GridBagConstraints.NONE;
		cPanel.gridx = 0;
		cPanel.gridy = 1;
		cPanel.weightx = 0.5;
		cPanel.weighty = 0.5;
		cPanel.gridwidth = 1;
		cPanel.insets = new Insets(10,10,10,0); //top,left,bottom,right
		listPanel.add(lblNoneUsers, cPanel);

		JScrollPane noneScrollPane = new JScrollPane(lstNoneUsers);
		noneScrollPane.setPreferredSize(new Dimension(300,600));
		cPanel.anchor = GridBagConstraints.FIRST_LINE_START; 
		cPanel.gridx = 0;
		cPanel.gridy = 2;
		cPanel.weightx = 0.5;
		cPanel.weighty = 0.5;
		cPanel.gridheight = 3;
		cPanel.insets = new Insets(10,10,10,0); //top,left,bottom,right
		listPanel.add(noneScrollPane, cPanel);

		cPanel.anchor = GridBagConstraints.FIRST_LINE_START; 
		cPanel.gridx = 1;
		cPanel.gridy = 1;
		cPanel.weightx = 0.5;
		cPanel.weighty = 0.5;
		cPanel.gridwidth = 1;
		cPanel.insets = new Insets(10,10,10,0); //top,left,bottom,right
		listPanel.add(lblUpdateUsers, cPanel);

		JScrollPane updateScrollPane = new JScrollPane(lstUpdateUsers);
		updateScrollPane.setPreferredSize(new Dimension(300,600));
		cPanel.anchor = GridBagConstraints.FIRST_LINE_START; 
		cPanel.gridx = 1;
		cPanel.gridy = 2;
		cPanel.weightx = 0.5;
		cPanel.weighty = 0.5;
		cPanel.gridheight = 3;
		cPanel.insets = new Insets(10,10,10,0); //top,left,bottom,right
		listPanel.add(updateScrollPane, cPanel);

		cPanel.anchor = GridBagConstraints.FIRST_LINE_START; 
		cPanel.gridx = 2;
		cPanel.gridy = 1;
		cPanel.weightx = 0.5;
		cPanel.weighty = 0.5;
		cPanel.gridwidth = 1;
		cPanel.insets = new Insets(10,10,10,0); //top,left,bottom,right
		listPanel.add(lblAdminUsers, cPanel);

		JScrollPane adminScrollPane = new JScrollPane(lstAdminUsers);
		adminScrollPane.setPreferredSize(new Dimension(300,600));
		cPanel.anchor = GridBagConstraints.FIRST_LINE_START; 
		cPanel.gridx = 2;
		cPanel.gridy = 2;
		cPanel.weightx = 0.5;
		cPanel.weighty = 0.5;
		cPanel.gridheight = 3;
		cPanel.insets = new Insets(10,10,10,0); //top,left,bottom,right
		listPanel.add(adminScrollPane, cPanel);

		//the the panels to the overall panel
		cOverall.anchor = GridBagConstraints.FIRST_LINE_START;
		cOverall.gridx = 0;
		cOverall.gridy = 0;
		cOverall.weightx = 0.1;
		cOverall.weighty = 0.1;
		cOverall.gridwidth = 1;
		cOverall.insets = new Insets(10,10,10,0); //top,left,bottom,right
		overallPanel.add(btnUpdateAll, cOverall);

		cOverall.anchor = GridBagConstraints.FIRST_LINE_START; 
		cOverall.gridx = 0;
		cOverall.gridy = 1;
		cOverall.weightx = 0.1;
		cOverall.weighty = 0.1;
		cOverall.gridwidth = 1;
		cOverall.insets = new Insets(0,0,0,0); //top,left,bottom,right
		overallPanel.add(listPanel, cOverall);

		//add the overall panel to this
		c.anchor = GridBagConstraints.FIRST_LINE_START; 
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.1;
		c.weighty = 0.1;
		c.gridwidth = 1;
		this.add(overallPanel, c);
	}


	/**
	 * Gets none users.
	 *
	 * @return the noneUsers
	 */
	public JList getNoneUsers() {
		return lstNoneUsers;
	}

	/**
	 * Gets update users.
	 *
	 * @return the updateUsers
	 */
	public JList getUpdateUsers() {
		return lstUpdateUsers;
	}

	/**
	 * Gets admin users.
	 *
	 * @return the adminUsers
	 */
	public JList getAdminUsers() {
		return lstAdminUsers;
	}

	/**
	 * Gets view.
	 *
	 * @return the view
	 */
	public UserPermissionView getView() {
		return view;
	}

	/**
	 * Gets the button None.
	 *
	 * @return the button None
	 */
	public JButton getBtnNone() {
		return btnNone;
	}

	/**
	 * Gets the button Update.
	 *
	 * @return the button Update
	 */
	public JButton getBtnUpdate() {
		return btnUpdate;
	}

	/**
	 * Gets the button admin.
	 *
	 * @return the button Admin
	 */
	public JButton getBtnAdmin() {
		return btnAdmin;
	}

	/**
	 * Gets the button UpdateAll.
	 *
	 * @return the button UpdateAll
	 */
	public JButton getBtnUpdateAll() {
		return btnUpdateAll;
	}

	/**
	 * Gets the noneUsersList.
	 *
	 * @return the noneUsersList
	 */
	public DefaultListModel getNoneUsersList() {
		return noneUsersList;
	}

	/**
	 * Sets the noneUsersList.
	 *
	 * @param noneUsersList the noneUsersList to set
	 */
	public void setNoneUsersList(DefaultListModel noneUsersList) {
		this.noneUsersList = noneUsersList;
	}

	/**
	 * Gets the updateUsersList.
	 *
	 * @return the updateUsersList
	 */
	public DefaultListModel getUpdateUsersList() {
		return updateUsersList;
	}

	/**
	 * Sets the updateUsersList.
	 *
	 * @param updateUsersList the updateUsersList to set
	 */
	public void setUpdateUsersList(DefaultListModel updateUsersList) {
		this.updateUsersList = updateUsersList;
	}

	/**
	 * Gets the adminUsersList.
	 *
	 * @return the adminUsersList
	 */
	public DefaultListModel getAdminUsersList() {
		return adminUsersList;
	}

	/**
	 * Sets the adminUsersList.
	 *
	 * @param adminUsersList the adminUsersList to set
	 */
	public void setAdminUsersList(DefaultListModel adminUsersList) {
		this.adminUsersList = adminUsersList;
	}

	/**
	 * Checks if changed.
	 *
	 * @return A boolean indicating whether or not is changed
	 */
	public boolean isHasChanged() {
		return hasChanged;
	}

	/**
	 * Sets hasChanged.
	 *
	 * @param hasChanged A boolean indicating whether or not is changed
	 */
	public void setHasChanged(boolean hasChanged) {
		this.hasChanged = hasChanged;
	}
}