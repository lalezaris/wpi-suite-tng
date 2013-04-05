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
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;


import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.action.AdminPermissionAction;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.action.UpdateAllPermissionsAction;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.action.UpdatePermissionAction;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.action.NonePermissionAction;

import com.google.gson.GsonBuilder;

import edu.wpi.cs.wpisuitetng.Permission;
import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.RMPermissionsLevel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.UserPermission;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.action.Refresher;
import edu.wpi.cs.wpisuitetng.modules.core.models.Role;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;


/**
 * The panel displayed when editing the permissions for the project users
 *
 * @author Chris Dunkers
 *
 * @version Apr 1, 2013
 *
 */
public class UserPermissionPanel extends JPanel{
	
	protected User[] allUsers;
	protected UserPermission[] allPermissions;
	protected boolean gotUsers, gotPermissions;
		
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
	 * Constructor
	 * 
	 * @param view the parent view of this panel
	 */
	public UserPermissionPanel(UserPermissionView view){
		Refresher.getInstance().getObjects(new UsersObserver(this), "core/user", "");
		Refresher.getInstance().getObjects(new PermissionsObserver(this), "requirementsmanager/permissions", "");
	
		
		this.view = view;
		
		this.gotUsers = false;
		this.gotPermissions = false;
		
		addComponents();
	}
	
	
	
	public boolean isGotUsers() {
		return gotUsers;
	}



	public boolean isGotPermissions() {
		return gotPermissions;
	}



	private void addComponents(){
		/* create a panel to store everything in*/
		JPanel listPanel = new JPanel();
		JPanel btnPanel = new JPanel();
		JPanel overallPanel = new JPanel();
		
		/*initialize all of the components to be displayed*/
		noneUsersList = new DefaultListModel();
		lstNoneUsers = new JList(noneUsersList);
		lstNoneUsers.setFixedCellWidth(75);
		updateUsersList = new DefaultListModel();
		lstUpdateUsers = new JList(updateUsersList);
		lstUpdateUsers.setFixedCellWidth(75);
		adminUsersList = new DefaultListModel();
		lstAdminUsers = new JList(adminUsersList);
		lstAdminUsers.setFixedCellWidth(75);

		/*initialize all of the buttons to be displayed*/
		btnNone = new JButton("Move to None");
		btnNone.addActionListener(new NonePermissionAction(this));
		btnUpdate = new JButton("Move to Update");
		btnUpdate.addActionListener(new UpdatePermissionAction(this));
		btnAdmin = new JButton("Move to Admin");
		btnAdmin.addActionListener(new AdminPermissionAction(this));
		btnUpdateAll = new JButton("Update Permissions");
		btnUpdateAll.addActionListener(new UpdateAllPermissionsAction(this));
		
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
		
		GridBagConstraints cBtn = new GridBagConstraints();	
		GridBagLayout layoutBtn = new GridBagLayout();	
		btnPanel.setLayout(layoutBtn);
		
		GridBagConstraints cOverall = new GridBagConstraints();	
		GridBagLayout layoutOverall = new GridBagLayout();	
		overallPanel.setLayout(layoutOverall);
		
		
		/*add all of the components to the btnPanel*/
		cBtn.anchor = GridBagConstraints.FIRST_LINE_START; 
		cBtn.fill = GridBagConstraints.HORIZONTAL;
		cBtn.gridx = 0;
		cBtn.gridy = 0;
		cBtn.weightx = 0.5;
		cBtn.weighty = 0.5;
		cBtn.gridheight = 1;
		cBtn.insets = new Insets(10,10,10,0); //top,left,bottom,right
		btnPanel.add(btnNone, cBtn);
		
		cBtn.anchor = GridBagConstraints.FIRST_LINE_START;
		cBtn.fill = GridBagConstraints.HORIZONTAL;
		cBtn.gridx = 0;
		cBtn.gridy = 1;
		cBtn.weightx = 0.5;
		cBtn.weighty = 0.5;
		cBtn.gridheight = 1;
		cBtn.insets = new Insets(10,10,10,0); //top,left,bottom,right
		btnPanel.add(btnUpdate, cBtn);
		
		cBtn.anchor = GridBagConstraints.FIRST_LINE_START; 
		cBtn.fill = GridBagConstraints.HORIZONTAL;
		cBtn.gridx = 0;
		cBtn.gridy = 2;
		cBtn.weightx = 0.5;
		cBtn.weighty = 0.5;
		cBtn.gridheight = 1;
		cBtn.insets = new Insets(10,10,10,0); //top,left,bottom,right
		btnPanel.add(btnAdmin, cBtn);
		
		cBtn.anchor = GridBagConstraints.FIRST_LINE_START; 
		cBtn.fill = GridBagConstraints.HORIZONTAL;
		cBtn.gridx = 0;
		cBtn.gridy = 3;
		cBtn.weightx = 0.5;
		cBtn.weighty = 0.5;
		cBtn.gridheight = 1;
		cBtn.insets = new Insets(10,10,10,0); //top,left,bottom,right
		btnPanel.add(btnUpdateAll, cBtn);
		
		/*add all of the components to the listPanel*/
		cPanel.anchor = GridBagConstraints.FIRST_LINE_START; 
		cPanel.gridx = 0;
		cPanel.gridy = 0;
		cPanel.weightx = 0.5;
		cPanel.weighty = 0.5;
		cPanel.gridwidth = 1;
		cPanel.insets = new Insets(10,10,10,0); //top,left,bottom,right
		listPanel.add(lblNoneUsers, cPanel);
		
		cPanel.anchor = GridBagConstraints.FIRST_LINE_START; 
		cPanel.gridx = 0;
		cPanel.gridy = 1;
		cPanel.weightx = 0.5;
		cPanel.weighty = 0.5;
		cPanel.gridheight = 3;
		cPanel.insets = new Insets(10,10,10,0); //top,left,bottom,right
		listPanel.add(lstNoneUsers, cPanel);
		
		cPanel.anchor = GridBagConstraints.FIRST_LINE_START; 
		cPanel.gridx = 1;
		cPanel.gridy = 0;
		cPanel.weightx = 0.5;
		cPanel.weighty = 0.5;
		cPanel.gridwidth = 1;
		cPanel.insets = new Insets(10,10,10,0); //top,left,bottom,right
		listPanel.add(lblUpdateUsers, cPanel);
		
		cPanel.anchor = GridBagConstraints.FIRST_LINE_START; 
		cPanel.gridx = 1;
		cPanel.gridy = 1;
		cPanel.weightx = 0.5;
		cPanel.weighty = 0.5;
		cPanel.gridheight = 3;
		cPanel.insets = new Insets(10,10,10,0); //top,left,bottom,right
		listPanel.add(lstUpdateUsers, cPanel);
		
		cPanel.anchor = GridBagConstraints.FIRST_LINE_START; 
		cPanel.gridx = 2;
		cPanel.gridy = 0;
		cPanel.weightx = 0.5;
		cPanel.weighty = 0.5;
		cPanel.gridwidth = 1;
		cPanel.insets = new Insets(10,10,10,0); //top,left,bottom,right
		listPanel.add(lblAdminUsers, cPanel);
		
		cPanel.anchor = GridBagConstraints.FIRST_LINE_START; 
		cPanel.gridx = 2;
		cPanel.gridy = 1;
		cPanel.weightx = 0.5;
		cPanel.weighty = 0.5;
		cPanel.gridheight = 3;
		cPanel.insets = new Insets(10,10,10,0); //top,left,bottom,right
		listPanel.add(lstAdminUsers, cPanel);
		
		//the the panels to the overall panel
		cOverall.anchor = GridBagConstraints.FIRST_LINE_START; 
		cOverall.gridx = 0;
		cOverall.gridy = 0;
		cOverall.weightx = 0.1;
		cOverall.weighty = 0.1;
		cOverall.gridwidth = 1;
		cOverall.insets = new Insets(10,10,10,0); //top,left,bottom,right
		overallPanel.add(btnPanel, cOverall);
		
		cOverall.anchor = GridBagConstraints.FIRST_LINE_START; 
		cOverall.gridx = 1;
		cOverall.gridy = 0;
		cOverall.weightx = 0.1;
		cOverall.weighty = 0.1;
		cOverall.gridwidth = 1;
		cOverall.insets = new Insets(10,10,10,0); //top,left,bottom,right
		overallPanel.add(listPanel, cOverall);
		
		//add the overall panel to this
		c.anchor = GridBagConstraints.FIRST_LINE_START; 
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.1;
		c.weighty = 0.1;
		c.gridwidth = 1;
		c.insets = new Insets(10,10,10,0); //top,left,bottom,right
		this.add(overallPanel, c);
		
		
		
	}
	
		protected void setAllPermissions(UserPermission[] all){
			this.allPermissions = all;
			for (int i = 0 ; i < all.length ; i ++)
				System.out.println("PERMISSION:" + all[i].getUsername() + " has " + all[i].getPermissions());

			this.gotPermissions = true;
			setUpUsersDisplay();
		}
		
		protected void setAllusers(User[] all){
			this.allUsers = all;
			this.gotUsers = true;
			
			for (int i = 0 ; i < all.length ; i ++)
				System.out.println("USER:" + all[i].getUsername() );
			
			setUpUsersDisplay();
		}		
		
		/**
		 * @param selected the selected names
		 * @param level the new permission level to put those names at
		 */
		public void updatePermissions(List<String> selected, RMPermissionsLevel level){
			SavePermissionsController controller = new SavePermissionsController(this);

			
			
			
			System.out.println("calling update");
			for (int i = 0 ; i < selected.size() ; i ++)
				System.out.println("SEL:" + ((String)selected.get(i)));
			
			//This loop goes through the selected names, and all the permissions
			//and if there is a match, it updates that permission to LEVEL (an input to this function)
			//and saves the permission
			for (int i = 0 ; i < this.allPermissions.length ; i ++){
				for (int j = 0 ; j < selected.size() ; j ++){
					System.out.println("IS " + this.allPermissions[i].getUsername() + " = TO " + (String)selected.get(j));
					if ( ((String)selected.get(j)).equals(this.allPermissions[i].getUsername()) && this.allPermissions[i].getPermissions() != level){
						
						String me = ConfigManager.getConfig().getUserName();
						Date now = new Date();
						
						String m = "[" + DateFormat.getDateTimeInstance().format(now) + "] CHANGE: " + me + " changed " + this.allPermissions[i].getUsername() + 
								" status from " + this.allPermissions[i].getPermissions() + " to " + level ;
						this.allPermissions[i].setMessage(m);
						
						this.allPermissions[i].setPermissions(level);
						controller.save(this.allPermissions[i], PermissionSaveMode.UPDATE);
						
						//System.out.println("Saving Perm " + this.allPermissions[i].getId() + " , "+ this.allPermissions[i].getUsername() + " has " + this.allPermissions[i].getPermissions());
						
					}
					
				}
			}
			
			//controller.save(permission, PermissionSaveMode.UPDATE);
			
			
		}
		
		protected void addPermission(UserPermission perm){
			ArrayList<UserPermission> all2 = new ArrayList<UserPermission>();
			boolean hasName = false;
			for (int i =0 ; i < this.allPermissions.length ; i ++){
				all2.add(this.allPermissions[i]);
				if (all2.get(i).getUsername().equals(perm.getUsername()))
					hasName = true;
			}
			//if (!hasID)
			if (!hasName)
				all2.add(perm);
			
			
			this.allPermissions =  new UserPermission[all2.size()];
			for (int i = 0 ; i < this.allPermissions.length;i++){
				this.allPermissions[i] = all2.get(i);
			}
			
			System.out.println("ADDING PERM\nhere are all the perms");
			for (int i = 0 ; i < this.allPermissions.length ; i ++)
				System.out.println("PERM:" + this.allPermissions[i].getUsername());
			
		}

		protected void setUpUsersDisplay(){
			System.out.println("Setting up Users");
			List<String> none = new ArrayList<String>();
			List<String> admin = new ArrayList<String>();
			List<String> view = new ArrayList<String>();
			List<String> update = new ArrayList<String>();
			
			SavePermissionsController controller = new SavePermissionsController(this);
			
			if (this.gotUsers && this.gotPermissions){
				
				
				for (int i = 0 ; i < this.allUsers.length ; i ++){
					
					
					boolean hasPermission = false;
					for (int j = 0 ; j < this.allPermissions.length ; j ++){
						
						if (this.allUsers[i].getUsername().equals(this.allPermissions[j].getUsername())){
							hasPermission = true;
							
							switch (this.allPermissions[j].getPermissions()){
								case ADMIN: admin.add(this.allUsers[i].getUsername());
								break;
								case UPDATE: update.add(this.allUsers[i].getUsername());
								break;
								case NONE: none.add(this.allUsers[i].getUsername());
								break;
							}
							
						}
						
					}
					
					if (!hasPermission){
						//System.out.println("Making Perm:" + this.allUsers[i].getName() + "with perm = " + "NONE");
						
						if (this.allUsers[i].getRole() == Role.ADMIN){
							controller.save(new UserPermission(this.allUsers[i].getUsername(), RMPermissionsLevel.ADMIN)
							, PermissionSaveMode.NEW);
							admin.add(this.allUsers[i].getUsername());
						} else {
							controller.save(new UserPermission(this.allUsers[i].getUsername(), RMPermissionsLevel.NONE)
									, PermissionSaveMode.NEW);
							none.add(this.allUsers[i].getUsername());
						}
					}
					
					
				}
				
				
			}
			
			this.noneUsersList = getNewModel(none);
			this.updateUsersList = getNewModel(update);
			this.adminUsersList = getNewModel(admin);
			
			lstNoneUsers.setModel(this.noneUsersList);
			lstUpdateUsers.setModel(this.updateUsersList);
			lstAdminUsers.setModel(this.adminUsersList);
		}
		
		
		
		/**
		 * The function takes a DefaultListModel and converts it to a list of string
		 * 
		 * @param model the model to be converted
		 * @return a list of the items in the model
		 */
		private List<String> getAllElementsInModel(DefaultListModel model){
			List<String> modelElements = new ArrayList<String>();
			for(int i = 0; i < model.getSize(); i++){
				modelElements.add((String)model.getElementAt(i));
			}
			return modelElements;
		}
		
		/**
		 * the function takes in a List and takes all of the elements from the list and adds them to the default list model
		 * 
		 * @param newElements a list of the elements to be put into the model
		 * @return the model with the given elements
		 */
		private DefaultListModel getNewModel(List<String> newElements){
			DefaultListModel newModel = new DefaultListModel();
			for(int i = 0; i < newElements.size(); i++){
				newModel.addElement((Object)newElements.get(i));
			}
			return newModel;
		}



		/**
		 * Enter description here.
		 * Make sure the method's name starts with get (delete this statement)
		 * @return the noneUsers
		 */
		public JList getNoneUsers() {
			return lstNoneUsers;
		}



		/**
		 * Enter description here.
		 * Make sure the method's name starts with get (delete this statement)
		 * @return the updateUsers
		 */
		public JList getUpdateUsers() {
			return lstUpdateUsers;
		}



		/**
		 * Enter description here.
		 * Make sure the method's name starts with get (delete this statement)
		 * @return the adminUsers
		 */
		public JList getAdminUsers() {
			return lstAdminUsers;
		}



		/**
		 * Enter description here.
		 * Make sure the method's name starts with get (delete this statement)
		 * @return the view
		 */
		public UserPermissionView getView() {
			return view;
		}
		
		
		
		
}
