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
	

	
	protected static User[] allUsers;
	protected static UserPermission[] allPermissions;
	protected boolean gotUsers, gotPermissions;
		
	/* the parent view*/
	protected UserPermissionView view;
	
	/* the List's that will display the usernames*/
	protected JList noneUsers;
	protected JList updateUsers;
	protected JList adminUsers;

	
	/* the Default list models for the lists*/
	DefaultListModel noneUsersList;
	DefaultListModel updateUsersList;
	DefaultListModel adminUsersList;
	
	/* the Buttons for moving things between lists and updating the changes*/
	protected JButton btnNone;
	protected JButton btnUpdate;
	protected JButton btnAdmin;
	
	/*layout manager for this panel*/
	protected GridBagLayout layout;
	
	protected static final int LABEL_ALIGNMENT = JLabel.TRAILING;
	
	//TODO: get rid of
	/* the Arrays which store the usernames for each permission*/
//	Object[] listAll = {};//"1", "2","3", "4","5", "6","7", "8"};
//	Object[] listNone = {};
//	Object[] listUpdate = {};
//	Object[] listAdmin = {};

	/**
	 * Constructor
	 * 
	 * @param view the parent view of this panel
	 */
	public UserPermissionPanel(UserPermissionView view){
		//this.allUsers = Refresher.getInstance().getUsers(new UsersObserver(this));
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
		
		/*initialize all of the components to be displayed*/
		noneUsersList = new DefaultListModel();
		noneUsers = new JList(noneUsersList);
		
		updateUsersList = new DefaultListModel();
		updateUsers = new JList(updateUsersList);
		adminUsersList = new DefaultListModel();
		adminUsers = new JList(adminUsersList);

		//TODO: get rid of
		noneUsers = new JList<Object>();
		updateUsers = new JList<Object>();
		adminUsers = new JList<Object>();
		
		/*initialize all of the buttons to be displayed*/
		btnNone = new JButton(" Move to None ");
		btnNone.addActionListener(new NonePermissionAction(noneUsers,updateUsers,adminUsers,this));
		btnUpdate = new JButton("Move to Update");
		btnUpdate.addActionListener(new UpdatePermissionAction(noneUsers,updateUsers,adminUsers,this));
		btnAdmin = new JButton("Move to Admin ");
		btnAdmin.addActionListener(new AdminPermissionAction(noneUsers,updateUsers,adminUsers,this));
		
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
		
		public static User[] getLastKnownUsers(){
			return allUsers;
		}
		public static UserPermission[] getLastKnownPermissions(){
			return allPermissions;
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
					if ( ((String)selected.get(j)).equals(this.allPermissions[i].getUsername())){
						
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
			
			noneUsers.setModel(this.noneUsersList);
			updateUsers.setModel(this.updateUsersList);
			adminUsers.setModel(this.adminUsersList);
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
		
}
