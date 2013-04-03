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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.action.AdminPermissionAction;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.action.NonePermissionAction;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.action.UpdatePermissionAction;

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
	protected JList projectUsers;
	protected JList noneUsers;
	protected JList updateUsers;
	protected JList adminUsers;
	
	protected JButton btnNone;
	protected JButton btnUpdate;
	protected JButton btnAdmin;
	protected JButton updateBtn;
	
	protected GridBagLayout layout;
	
	protected static final int LABEL_ALIGNMENT = JLabel.TRAILING;
	
	Object[] listAll = {"1", "2","3", "4","5", "6","7", "8"};
	Object[] listNone = {};
	Object[] listUpdate = {};
	Object[] listAdmin = {};
	
	public UserPermissionPanel(UserPermissionView view){
		this.view = view;
		
		addComponents();
	}
	
	private void addComponents(){
		JPanel listPanel = new JPanel();
		
		projectUsers = new JList();
		projectUsers.setListData(listAll);
		noneUsers = new JList();
		noneUsers.setListData(listNone);
		updateUsers = new JList();
		updateUsers.setListData(listUpdate);
		adminUsers = new JList();
		adminUsers.setListData(listAdmin);
		
		btnNone = new JButton(" Move to None ");
		btnNone.addActionListener(new NonePermissionAction());
		btnUpdate = new JButton("Move to Update");
		btnUpdate.addActionListener(new UpdatePermissionAction());
		btnAdmin = new JButton("Move to Admin ");
		btnAdmin.addActionListener(new AdminPermissionAction());
		updateBtn = new JButton("Update Users");
		
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
		cPanel.gridheight = 3;
		cPanel.insets = new Insets(10,10,10,0); //top,left,bottom,right
		listPanel.add(projectUsers, cPanel);
		
		cPanel.anchor = GridBagConstraints.FIRST_LINE_START; 
		cPanel.gridx = 1;
		cPanel.gridy = 1;
		cPanel.weightx = 0.5;
		cPanel.weighty = 0.5;
		cPanel.gridheight = 1;
		cPanel.insets = new Insets(10,10,10,0); //top,left,bottom,right
		listPanel.add(btnNone, cPanel);
		
		cPanel.anchor = GridBagConstraints.FIRST_LINE_START; 
		cPanel.gridx = 1;
		cPanel.gridy = 2;
		cPanel.weightx = 0.5;
		cPanel.weighty = 0.5;
		cPanel.gridheight = 1;
		cPanel.insets = new Insets(10,10,10,0); //top,left,bottom,right
		listPanel.add(btnUpdate, cPanel);
		
		cPanel.anchor = GridBagConstraints.FIRST_LINE_START; 
		cPanel.gridx = 1;
		cPanel.gridy = 3;
		cPanel.weightx = 0.5;
		cPanel.weighty = 0.5;
		cPanel.gridheight = 1;
		cPanel.insets = new Insets(10,10,10,0); //top,left,bottom,right
		listPanel.add(btnAdmin, cPanel);
		
		cPanel.anchor = GridBagConstraints.FIRST_LINE_START; 
		cPanel.gridx = 2;
		cPanel.gridy = 0;
		cPanel.weightx = 0.5;
		cPanel.weighty = 0.5;
		cPanel.gridwidth = 1;
		cPanel.insets = new Insets(10,10,10,0); //top,left,bottom,right
		listPanel.add(lblNoneUsers, cPanel);
		
		cPanel.anchor = GridBagConstraints.FIRST_LINE_START; 
		cPanel.gridx = 2;
		cPanel.gridy = 1;
		cPanel.weightx = 0.5;
		cPanel.weighty = 0.5;
		cPanel.gridheight = 3;
		cPanel.insets = new Insets(10,10,10,0); //top,left,bottom,right
		listPanel.add(noneUsers, cPanel);
		
		cPanel.anchor = GridBagConstraints.FIRST_LINE_START; 
		cPanel.gridx = 3;
		cPanel.gridy = 0;
		cPanel.weightx = 0.5;
		cPanel.weighty = 0.5;
		cPanel.gridwidth = 1;
		cPanel.insets = new Insets(10,10,10,0); //top,left,bottom,right
		listPanel.add(lblUpdateUsers, cPanel);
		
		cPanel.anchor = GridBagConstraints.FIRST_LINE_START; 
		cPanel.gridx = 3;
		cPanel.gridy = 1;
		cPanel.weightx = 0.5;
		cPanel.weighty = 0.5;
		cPanel.gridheight = 3;
		cPanel.insets = new Insets(10,10,10,0); //top,left,bottom,right
		listPanel.add(updateUsers, cPanel);
		
		cPanel.anchor = GridBagConstraints.FIRST_LINE_START; 
		cPanel.gridx = 4;
		cPanel.gridy = 0;
		cPanel.weightx = 0.5;
		cPanel.weighty = 0.5;
		cPanel.gridwidth = 1;
		cPanel.insets = new Insets(10,10,10,0); //top,left,bottom,right
		listPanel.add(lblAdminUsers, cPanel);
		
		cPanel.anchor = GridBagConstraints.FIRST_LINE_START; 
		cPanel.gridx = 4;
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
	
	public class NonePermissionAction extends AbstractAction {
		
		public NonePermissionAction(){} 
		
		/* 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent ae) {
			Object[] selItems;
			Object[] projectItems = projectUsers.getSelectedValues();
			Object[] updateItems = updateUsers.getSelectedValues();
			Object[] adminItems = adminUsers.getSelectedValues();
			
			List<Object> newProjectItems = new ArrayList<Object>(Arrays.asList(listAll));
			newProjectItems.removeAll(Arrays.asList(projectItems));
			listAll = newProjectItems.toArray(); 
			projectUsers.setListData(newProjectItems.toArray());
			
			List<Object> newUpdateItems = new ArrayList<Object>(Arrays.asList(listUpdate));
			newUpdateItems.removeAll(Arrays.asList(updateItems));
			listUpdate = newUpdateItems.toArray();
			updateUsers.setListData(newUpdateItems.toArray());
			
			List<Object> newAdminItems = new ArrayList<Object>(Arrays.asList(listAdmin));
			newAdminItems.removeAll(Arrays.asList(adminItems));
			listAdmin = newAdminItems.toArray();
			adminUsers.setListData(newAdminItems.toArray());

			selItems = concat(listNone,concat(projectItems, concat(updateItems,adminItems)));
			listNone = selItems;
			
			noneUsers.setListData(selItems);	
		}
	}
	
	public class UpdatePermissionAction extends AbstractAction {
		
		public UpdatePermissionAction(){} 
		
		/* 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent ae) {
			Object[] selItems;
			Object[] projectItems = projectUsers.getSelectedValues();
			Object[] noneItems = noneUsers.getSelectedValues();
			Object[] adminItems = adminUsers.getSelectedValues();
			
			List<Object> newProjectItems = new ArrayList<Object>(Arrays.asList(listAll));
			newProjectItems.removeAll(Arrays.asList(projectItems));
			listAll = newProjectItems.toArray(); 
			projectUsers.setListData(newProjectItems.toArray());
			
			List<Object> newNoneItems = new ArrayList<Object>(Arrays.asList(listNone));
			newNoneItems.removeAll(Arrays.asList(noneItems));
			listNone = newNoneItems.toArray();
			noneUsers.setListData(newNoneItems.toArray());
			
			List<Object> newAdminItems = new ArrayList<Object>(Arrays.asList(listAdmin));
			newAdminItems.removeAll(Arrays.asList(adminItems));
			listAdmin = newAdminItems.toArray();
			adminUsers.setListData(newAdminItems.toArray());

			selItems = concat(listUpdate,concat(projectItems, concat(noneItems,adminItems)));
			listUpdate = selItems;
			
			updateUsers.setListData(selItems);	
		}
	}

	public class AdminPermissionAction extends AbstractAction {
		
		public AdminPermissionAction(){} 
		
		/* 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent ae) {
			Object[] selItems;
			Object[] projectItems = projectUsers.getSelectedValues();
			Object[] updateItems = updateUsers.getSelectedValues();
			Object[] noneItems = noneUsers.getSelectedValues();
			
			List<Object> newProjectItems = new ArrayList<Object>(Arrays.asList(listAll));
			newProjectItems.removeAll(Arrays.asList(projectItems));
			listAll = newProjectItems.toArray(); 
			projectUsers.setListData(newProjectItems.toArray());
			
			List<Object> newUpdateItems = new ArrayList<Object>(Arrays.asList(listUpdate));
			newUpdateItems.removeAll(Arrays.asList(updateItems));
			listUpdate = newUpdateItems.toArray();
			updateUsers.setListData(newUpdateItems.toArray());
			
			List<Object> newNoneItems = new ArrayList<Object>(Arrays.asList(listNone));
			newNoneItems.removeAll(Arrays.asList(noneItems));
			listNone = newNoneItems.toArray();
			noneUsers.setListData(newNoneItems.toArray());
	
			selItems = concat(listAdmin,concat(projectItems, concat(updateItems,noneItems)));
			listAdmin = selItems;
			
			adminUsers.setListData(selItems);	
		}
	}
		
		
		
		/**
		 * Function taken from http://stackoverflow.com/questions/80476/how-to-concatenate-two-arrays-in-java
		 * 
		 * @param A
		 * @param B
		 * @return
		 */
		Object[] concat(Object[] A, Object[] B) {
		   int aLen = A.length;
		   int bLen = B.length;
		   Object[] C= new Object[aLen+bLen];
		   System.arraycopy(A, 0, C, 0, aLen);
		   System.arraycopy(B, 0, C, aLen, bLen);
		   return C;
		}

}
