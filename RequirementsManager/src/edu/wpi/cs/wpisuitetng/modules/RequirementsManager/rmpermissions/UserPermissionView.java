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

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RMPermissionsLevel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.UserPermission;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.RetrieveAllRequirementsController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.RequirementListPanel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.action.RefreshAction;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.model.Tab;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.UserPermissionPanel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.controller.AdminPermissionController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.controller.NonePermissionController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.controller.SetUpPermissionsPanelController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.controller.UpdateAllPermissionsController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.controller.UpdatePermissionController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.model.PermissionModel;
import edu.wpi.cs.wpisuitetng.modules.core.models.Role;

/**
 * View for user permissions
 *
 * @author CDUNKERS
 *
 * @version Apr 1, 2013
 *
 */
public class UserPermissionView extends JPanel {
	
	private UserPermissionPanel mainPanel;
	private RetrieveAllRequirementsController controller;
	final JScrollPane mainPanelScrollPane;
	private Tab containingTab;
	private boolean inputEnabled;
	
	private PermissionModel permModel;
	
	public UserPermissionView(Tab tab){
		containingTab = tab;
		containingTab.setTitle("Edit User Permissions");

		inputEnabled = true;
		
		//make the model
		permModel = new PermissionModel();
		
		// Instantiate the main create requirement panel
		mainPanel = new UserPermissionPanel(this);
		
		mainPanel.getBtnAdmin().addActionListener(new AdminPermissionController(mainPanel));
		mainPanel.getBtnNone().addActionListener(new NonePermissionController(mainPanel));
		mainPanel.getBtnUpdate().addActionListener(new UpdatePermissionController(mainPanel));
		mainPanel.getBtnUpdateAll().addActionListener(new UpdateAllPermissionsController(mainPanel, permModel));
		
		SetUpPermissionsPanelController setUp = new SetUpPermissionsPanelController(mainPanel, permModel);
		setUp.setUp();
		
		this.setLayout(new BorderLayout());
		mainPanelScrollPane = new JScrollPane(mainPanel);
		mainPanelScrollPane.getVerticalScrollBar().setUnitIncrement(10);
		
		// Prevent content of scroll pane from smearing (credit: https://gist.github.com/303464)
		mainPanelScrollPane.getVerticalScrollBar().addAdjustmentListener(new java.awt.event.AdjustmentListener(){
			public void adjustmentValueChanged(java.awt.event.AdjustmentEvent ae){
						mainPanelScrollPane.repaint();
			}
		});
		
		this.add(mainPanelScrollPane, BorderLayout.CENTER);
	}

	/**
	 * the function takes in a List and takes all of the elements from the list and adds them to the default list model
	 * 
	 * @param newElements a list of the elements to be put into the model
	 * @return the model with the given elements
	 */
	public DefaultListModel getNewModel(List<String> newElements){
		DefaultListModel newModel = new DefaultListModel();
		for(int i = 0; i < newElements.size(); i++){
			newModel.addElement((Object)newElements.get(i));
		}
		return newModel;
	}
	

//	public void setUpPanel(){
//		
//		if (this.permModel.isGotUsers() && this.permModel.isGotPermissions()){
//			List<String> none = new ArrayList<String>();
//			List<String> admin = new ArrayList<String>();
//			List<String> view = new ArrayList<String>();
//			List<String> update = new ArrayList<String>();
//			
//			SavePermissionsController controller = new SavePermissionsController(this.panel);
//			for (int i = 0 ; i < this.users.length ; i ++){
//				boolean hasPermission = false;
//				for (int j = 0 ; j < this.permissions.length ; j ++){
//					if (this.users[i].getUsername().equals(this.permissions[j].getUsername())){
//						hasPermission = true;
//						
//						switch (this.permissions[j].getPermissions()){
//							case ADMIN: admin.add(this.users[i].getUsername());
//							break;
//							case UPDATE: update.add(this.users[i].getUsername());
//							break;
//							case NONE: none.add(this.users[i].getUsername());
//							break;
//						}
//						
//					}
//					
//				}
//				
//				if (!hasPermission){
//					if (this.users[i].getRole() == Role.ADMIN){
//						controller.save(new UserPermission(this.users[i].getUsername(), RMPermissionsLevel.ADMIN)
//						, PermissionSaveMode.NEW);
//						admin.add(this.users[i].getUsername());
//					} else {
//						controller.save(new UserPermission(this.users[i].getUsername(), RMPermissionsLevel.NONE)
//								, PermissionSaveMode.NEW);
//						none.add(this.users[i].getUsername());
//					}
//				}
//			}
//			
//			this.panel.setNoneUsersList( panel.getView().getNewModel(none) );
//			this.panel.setUpdateUsersList( panel.getView().getNewModel(update) );
//			this.panel.setAdminUsersList( panel.getView().getNewModel(admin));
//			
//			this.panel.getNoneUsers().setModel(this.panel.getNoneUsersList());
//			this.panel.getUpdateUsers().setModel(this.panel.getUpdateUsersList());
//			this.panel.getAdminUsers().setModel(this.panel.getAdminUsersList());
//		}
//		
//		if (this.permModel.isGotUsers() || this.permModel.isGotPermissions()){
//			CurrentUserPermissions.updateCurrentUserPermissions();
//		}
//	}
	
	/**
	 * Gets tab
	 * 
	 * @return the containingTab
	 */
	public Tab getTab() {
		return containingTab;
	}

	/**
	 * @return the permModel
	 */
	public PermissionModel getPermModel() {
		return permModel;
	}
	
	
	
}
