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
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import com.google.gson.GsonBuilder;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.RMPermissionsLevel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.UserPermission;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.action.Refresher;
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
	protected JList<Object> projectUsers;
	protected JList<Object> noneUsers;
	protected JList<Object> updateUsers;
	protected JList<Object> adminUsers;
	
	/* the Buttons for moving things between lists and updating the changes*/
	protected JButton btnNone;
	protected JButton btnUpdate;
	protected JButton btnAdmin;
	protected JButton updateBtn;
	
	/*layout manager for this panel*/
	protected GridBagLayout layout;
	
	protected static final int LABEL_ALIGNMENT = JLabel.TRAILING;
	
	/* the Arrays which store the usernames for each permission*/
	Object[] listAll = {};//"1", "2","3", "4","5", "6","7", "8"};
	Object[] listNone = {};
	Object[] listUpdate = {};
	Object[] listAdmin = {};
	
	
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
	
	private void addComponents(){
		/* create a panel to store everything in*/
		JPanel listPanel = new JPanel();
		
		/*initialize all of the components to be displayed*/
		projectUsers = new JList<Object>();
		//projectUsers.setListData(listAll);
		noneUsers = new JList<Object>();
		//noneUsers.setListData(listNone);
		updateUsers = new JList<Object>();
		//updateUsers.setListData(listUpdate);
		adminUsers = new JList<Object>();
		//adminUsers.setListData(listAdmin);
		
		/*initialize all of the buttons to be displayed*/
		btnNone = new JButton(" Move to None ");
		btnNone.addActionListener(new NonePermissionAction());
		btnUpdate = new JButton("Move to Update");
		btnUpdate.addActionListener(new UpdatePermissionAction());
		btnAdmin = new JButton("Move to Admin ");
		btnAdmin.addActionListener(new AdminPermissionAction());
		updateBtn = new JButton("Update Users");
		
		/*labels for the components*/
		JLabel lblProjectUsers = new JLabel("Project Users:", LABEL_ALIGNMENT);
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
	
	/**
	 * The controller for the button to move a object to the None Permissions list
	 *
	 * @author Chris Dunkers
	 *
	 * @version Apr 2, 2013
	 *
	 */
	public class NonePermissionAction extends AbstractAction {
		
		public NonePermissionAction(){} 
		
		/* 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent ae) {
			
			if (gotUsers && gotPermissions){
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
				updatePermissions(concat(projectItems, concat(updateItems,adminItems)), RMPermissionsLevel.NONE);
				selItems = concat(listNone,concat(projectItems, concat(updateItems,adminItems)));
				listNone = selItems;
				
				noneUsers.setListData(selItems);
			}
		}
	}
	
	/**
	 * The controller for the button to move a object to the Update Permissions list
	 *
	 * @author Chris Dunkers
	 *
	 * @version Apr 2, 2013
	 *
	 */
	public class UpdatePermissionAction extends AbstractAction {
		
		public UpdatePermissionAction(){} 
		
		/* 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent ae) {
			
			if (gotUsers && gotPermissions){
				
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
				
				//CAST all your errors away. AWAY THEY ARE CAST
				List<Object> newAdminItems = new ArrayList<Object>(Arrays.asList(listAdmin));
				newAdminItems.removeAll(Arrays.asList(adminItems));
				listAdmin = newAdminItems.toArray();
				adminUsers.setListData(newAdminItems.toArray());
				updatePermissions(concat(projectItems, concat(noneItems,adminItems)), RMPermissionsLevel.UPDATE);
				selItems = concat(listUpdate,concat(projectItems, concat(noneItems,adminItems)));
				listUpdate = selItems;
				
				updateUsers.setListData(selItems);	
			}
		}
	}

	/**
	 * The controller for the button to move a object to the None Permissions list
	 *
	 * @author Chris Dunkers
	 *
	 * @version Apr 2, 2013
	 *
	 */
	public class AdminPermissionAction extends AbstractAction {
		
		public AdminPermissionAction(){} 
		
		/* 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent ae) {
			
			if (gotUsers && gotPermissions){
			
			
				
				Object[] selItems;
				
				//Get the selected objects from each of the lists
				Object[] projectItems = projectUsers.getSelectedValues();
				Object[] updateItems = updateUsers.getSelectedValues();
				Object[] noneItems = noneUsers.getSelectedValues();
				
				//Remove the objects selected from the list
				List<Object> newProjectItems = new ArrayList<Object>(Arrays.asList(listAll));
				newProjectItems.removeAll(Arrays.asList(projectItems));
				listAll = newProjectItems.toArray(); //update the list globally
				projectUsers.setListData(newProjectItems.toArray()); //display the new list with the items removed
				
				//Remove the objects selected from the list
				List<Object> newUpdateItems = new ArrayList<Object>(Arrays.asList(listUpdate));
				newUpdateItems.removeAll(Arrays.asList(updateItems));
				listUpdate = newUpdateItems.toArray(); //update the list globally
				updateUsers.setListData(newUpdateItems.toArray()); //display the new list with the items removed
				
				//Remove the objects selected from the list
				List<Object> newNoneItems = new ArrayList<Object>(Arrays.asList(listNone));
				newNoneItems.removeAll(Arrays.asList(noneItems));
				listNone = newNoneItems.toArray(); //update the list globally
				noneUsers.setListData(newNoneItems.toArray()); //display the new list with the items removed
		
				updatePermissions(concat(projectItems, concat(updateItems,noneItems)), RMPermissionsLevel.ADMIN);
				
				//Combine all fo the selected items and add to the current list
				selItems = concat(listAdmin,concat(projectItems, concat(updateItems,noneItems)));
				listAdmin = selItems; //update the list globally
				
				
				
				adminUsers.setListData(selItems); //display the new list with the items removed
			}
		}
	}
		
		
		
		/**
		 * Function taken from http://stackoverflow.com/questions/80476/how-to-concatenate-two-arrays-in-java
		 * 
		 * @param A the first array of objects to merge together
		 * @param B the second array of objects to merge together
		 * @return returns a array of objects which is merged together and has the elements of both arrays
		 */
		Object[] concat(Object[] A, Object[] B) {
		   int aLen = A.length;
		   int bLen = B.length;
		   Object[] C= new Object[aLen+bLen];
		   System.arraycopy(A, 0, C, 0, aLen);
		   System.arraycopy(B, 0, C, aLen, bLen);
		   return C;
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
				System.out.println("USER:" + all[i].getName());
			
			setUpUsersDisplay();
		}

	
		
		
		/**
		 * @param selected the selected names
		 * @param level the new permission level to put those names at
		 */
		public void updatePermissions(Object[] selected, RMPermissionsLevel level){
			SavePermissionsController controller = new SavePermissionsController(this);

			
			
			
			System.out.println("calling update");
			for (int i = 0 ; i < selected.length ; i ++)
				System.out.println("SEL:" + ((String)selected[i]));
			
			//This loop goes through the selected names, and all the permissions
			//and if there is a match, it updates that permission to LEVEL (an input to this function)
			//and saves the permission
			for (int i = 0 ; i < this.allPermissions.length ; i ++){
				for (int j = 0 ; j < selected.length ; j ++){
					System.out.println("IS " + this.allPermissions[i].getUsername() + " = TO " + (String)selected[j]);
					if ( ((String)selected[j]).equals(this.allPermissions[i].getUsername())){
						this.allPermissions[i].setPermissions(level);
						controller.save(this.allPermissions[i], PermissionSaveMode.UPDATE);
						
						System.out.println("Saving Perm " + this.allPermissions[i].getId() + " , "+ this.allPermissions[i].getUsername() + " has " + this.allPermissions[i].getPermissions());
						
					}
					
				}
			}
			
			//controller.save(permission, PermissionSaveMode.UPDATE);
			
			
		}
		
		protected void addPermission(UserPermission perm){
			ArrayList<UserPermission> all2 = new ArrayList<UserPermission>();
			boolean hasID = false;
			for (int i =0 ; i < this.allPermissions.length ; i ++){
				all2.add(this.allPermissions[i]);
				if (all2.get(i).getId() == perm.getId())
					hasID = true;
			}
			if (!hasID)
				all2.add(perm);
			this.allPermissions =  new UserPermission[all2.size()];
			for (int i = 0 ; i < this.allPermissions.length;i++){
				this.allPermissions[i] = all2.get(i);
			}
		}
		
		
		protected void setUpUsersDisplay(){
			System.out.println("Setting up Users");
			ArrayList<Object> none = new ArrayList<Object>();
			ArrayList<Object> admin = new ArrayList<Object>();
			ArrayList<Object> view = new ArrayList<Object>();
			ArrayList<Object> update = new ArrayList<Object>();
			
			SavePermissionsController controller = new SavePermissionsController(this);
			
			if (this.gotUsers && this.gotPermissions){
				
				
				for (int i = 0 ; i < this.allUsers.length ; i ++){
					
					
					boolean hasPermission = false;
					for (int j = 0 ; j < this.allPermissions.length ; j ++){
						
						if (this.allUsers[i].getName().equals(this.allPermissions[j].getUsername())){
							hasPermission = true;
							
							switch (this.allPermissions[j].getPermissions()){
								case ADMIN: admin.add(this.allUsers[i].getName());
								break;
								case UPDATE: update.add(this.allUsers[i].getName());
								break;
								case NONE: none.add(this.allUsers[i].getName());
								break;
							}
							
						}
						
					}
					
					if (!hasPermission){
						System.out.println("Making Perm:" + this.allUsers[i].getName() + "with perm = " + "NONE");
						controller.save(new UserPermission(this.allUsers[i].getName(), RMPermissionsLevel.NONE)
								, PermissionSaveMode.NEW);
						
						none.add(this.allUsers[i].getName());
					}
					
					
				}
				
				
			}
			
			
			//projectUsers = new JList<User>();
			projectUsers.setListData(view.toArray());
			//noneUsers = new JList<User>();
			noneUsers.setListData(none.toArray());
			//updateUsers = new JList<User>();
			updateUsers.setListData(update.toArray());
			//adminUsers = new JList<User>();
			adminUsers.setListData(admin.toArray());
			
			
			this.listAdmin = admin.toArray();
			this.listNone = none.toArray();
			this.listUpdate = update.toArray();
			
			
		}
		
}
