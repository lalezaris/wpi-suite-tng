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
 *  Arica Liu
 *  Tyler Stone
 *  Evan Polekoff
 *  Tushar Narayan
 *  Lauren Kahn
 *  Michael French
 *  Ned Shelton
 **************************************************/

package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Toolbar;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SpringLayout;
import javax.swing.border.EtchedBorder;

import org.gpl.JSplitButton.JSplitButton;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.DefaultToolbarView;
import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Toolbar.action.CreateSplitListener;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Toolbar.action.EditUserPermissionsAction;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Toolbar.action.ListAction;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Toolbar.action.ListIterationAction;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Toolbar.action.ListSplitListener;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Toolbar.action.NewIterationAction;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Toolbar.action.NewRequirementAction;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Toolbar.action.OpenHelpAction;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Toolbar.action.ViewChartsAction;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RMPermissionsLevel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.observers.CurrentUserPermissions;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.controller.MainTabController;

/**
 * Toolbar Panel for Requirements Manager.
 * 
 * @author Tyler Stone 
 * @author Arica Liu
 * @edited Michael French
 * @edited Evan Polekoff
 * @edited Ned Shelton
 * @edited Tushar Narayan
 * @edited Lauren Kahn
 * @edited Joe Spicola
 * 
 * @version April 7, 2013 
 */
@SuppressWarnings({"serial", "unused"})
public class ToolbarPanel extends DefaultToolbarView {
	private JButton newIteration;
	private JButton listIteration;
	private JButton editUserPermissions;
	private JLabel viewUserPermission;
	private JLabel viewUserName;
	private JButton viewStats;
	private JButton btnHelp;
	
	private JSplitButton createSplit;
	private JSplitButton listSplit;
	
	private JPopupMenu createMenu;
	private JPopupMenu listMenu;
	
	private JMenuItem createReq;
	private JMenuItem createIter;
	private JMenuItem listReq;
	private JMenuItem listIter;
	private String userName;

	private ToolbarGroupView toolbarGroupManage;
	private ToolbarGroupView toolbarGroupRequirement;
	private ToolbarGroupView toolbarGroupUserPermission;
	private ToolbarGroupView toolbarGroupViewUserPermission;
	private ToolbarGroupView toolbarGroupResources;

	/**
	 * Create a ToolbarPanel.
	 * 
	 * @param tabController The MainTabController this view should open tabs with
	 */
	public ToolbarPanel(MainTabController tabController) {	
		// Construct the content panel
		JPanel manageContent = new JPanel();
		JPanel requirementContent = new JPanel();
		JPanel permissionContent = new JPanel();
		JPanel viewUserPermissionPanel = new JPanel();
		JPanel resourcesContent = new JPanel();

		SpringLayout manageLayout  = new SpringLayout();
		SpringLayout requirementLayout = new SpringLayout();
		SpringLayout permissionLayout = new SpringLayout();
		SpringLayout viewUserPermissionLayout = new SpringLayout();
		SpringLayout resourcesLayout = new SpringLayout();

		manageContent.setLayout(manageLayout);
		manageContent.setOpaque(false);
		manageContent.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));

		requirementContent.setLayout(requirementLayout);
		requirementContent.setOpaque(false);
		requirementContent.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));

		permissionContent.setLayout(permissionLayout);
		permissionContent.setOpaque(false);
		permissionContent.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));

		resourcesContent.setLayout(resourcesLayout);
		resourcesContent.setOpaque(false);
		resourcesContent.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));

		viewUserPermissionPanel.setLayout(viewUserPermissionLayout);
		viewUserPermissionPanel.setOpaque(false);

		CurrentUserPermissions.updateCurrentUserPermissions(new PermissionDisplayUpdater(this));
		CurrentUserPermissions.updateCurrentUserPermissions(new ToolbarDisplayUpdater(this));

		// Construct the buttons
		newIteration = new JButton("Create Iteration");
		newIteration.setAction(new NewIterationAction(tabController));
		newIteration.setVisible(false);

		listIteration = new JButton("List Iterations");
		listIteration.setAction(new ListIterationAction(tabController));
		listIteration.setVisible(false);
		listIteration.setPreferredSize(newIteration.getPreferredSize()); //set equal to the button above

		//construct the edit user permissions button
		editUserPermissions = new JButton("Edit");
		editUserPermissions.setAction(new EditUserPermissionsAction(tabController));
		editUserPermissions.setVisible(false);

		//construct the user permission label
		viewUserName = new JLabel("");//new JLabel("User: " + userName);
		viewUserPermission = new JLabel("Permission Level: \n" + CurrentUserPermissions.getCurrentUserPermission().toString());

		//Create split button
		createSplit = new JSplitButton("Create");
		createSplit.setPreferredSize(new Dimension(createSplit.getPreferredSize().width + 20,createSplit.getPreferredSize().height));
		createSplit.addSplitButtonActionListener(new CreateSplitListener(tabController));
		createSplit.setToolTipText("Create a requirement or iteration");
		listSplit = new JSplitButton("List");
		listSplit.setPreferredSize(createSplit.getPreferredSize());
		listSplit.addSplitButtonActionListener(new ListSplitListener(tabController));
		listSplit.setToolTipText("List the requirements or iterations");
				
		//Construct Bar Chart Buttons
		viewStats = new JButton("Statistics");
		viewStats.setAction(new ViewChartsAction(tabController));
		btnHelp = new JButton("User Guide");
		viewStats.setPreferredSize(btnHelp.getPreferredSize());
		btnHelp.setAction(new OpenHelpAction());
		
		//construct create menu
		createMenu = new JPopupMenu("Create");
		createMenu.getAccessibleContext().setAccessibleDescription(
		        "Create a requirement or a iterations");
		
		//construct list menu
		listMenu = new JPopupMenu("List");
		listMenu.getAccessibleContext().setAccessibleDescription(
				"List the requirements or iterations");
		
		//add menu items
		createReq = new JMenuItem("Create Requirement");
		createReq.setAction(new NewRequirementAction(tabController,"Create Requirement"));
		createMenu.add(createReq);
		
		createIter = new JMenuItem("Create Iteration");
		createIter.setAction(new NewIterationAction(tabController));
		createMenu.add(createIter);
		
		listReq = new JMenuItem("List Requirements");
		listReq.setAction(new ListAction(tabController,"List Requirements"));
		listMenu.add(listReq);
		
		listIter = new JMenuItem("List Iterations");
		listIter.setAction(new ListIterationAction(tabController));
		listMenu.add(listIter);
		
		
		createSplit.setPopupMenu(createMenu);
		listSplit.setPopupMenu(listMenu);

		// Configure the layout of the buttons on the content panel
		
		manageLayout.putConstraint(SpringLayout.NORTH, createSplit, 5, SpringLayout.NORTH, manageContent);
		manageLayout.putConstraint(SpringLayout.WEST, createSplit, 10, SpringLayout.WEST, manageContent);
		
		manageLayout.putConstraint(SpringLayout.NORTH, listSplit, 10, SpringLayout.SOUTH, createSplit);
		manageLayout.putConstraint(SpringLayout.WEST, listSplit, 10, SpringLayout.WEST, manageContent);
				
		permissionLayout.putConstraint(SpringLayout.NORTH, viewUserPermission, 5, SpringLayout.NORTH, permissionContent);
		permissionLayout.putConstraint(SpringLayout.WEST, viewUserPermission, 10, SpringLayout.WEST, permissionContent);
		permissionLayout.putConstraint(SpringLayout.NORTH, editUserPermissions, 10, SpringLayout.SOUTH, viewUserPermission);
		permissionLayout.putConstraint(SpringLayout.WEST, editUserPermissions, (viewUserPermission.getPreferredSize().width/2) - (editUserPermissions.getPreferredSize().width/2) + 10, SpringLayout.WEST, permissionContent);
		
		resourcesLayout.putConstraint(SpringLayout.NORTH, viewStats, 5, SpringLayout.NORTH, resourcesContent);
		resourcesLayout.putConstraint(SpringLayout.WEST, viewStats, 10, SpringLayout.WEST, resourcesContent);
		resourcesLayout.putConstraint(SpringLayout.NORTH, btnHelp, 10, SpringLayout.SOUTH, viewStats);
		resourcesLayout.putConstraint(SpringLayout.WEST, btnHelp, 10, SpringLayout.WEST, resourcesContent);

		// Add buttons to the content panel
		manageContent.add(createSplit);
		manageContent.add(listSplit);
		
		// Add buttons to the content panel
		permissionContent.add(editUserPermissions);
		permissionContent.add(viewUserPermission);

		//Add chart buttons
		resourcesContent.add(viewStats);
		resourcesContent.add(btnHelp);

		// Construct a new toolbar group to be added to the end of the toolbar
		toolbarGroupManage = new ToolbarGroupView("Manage", manageContent);
		toolbarGroupManage.setVisible(false);
		
		toolbarGroupResources = new ToolbarGroupView("Resources", resourcesContent);

		toolbarGroupUserPermission = new ToolbarGroupView("Permission", permissionContent);

		// Calculate the width of the toolbar
		Double iterationGroupHeight = 0.0;
		for (Component b : manageContent.getComponents()){
			iterationGroupHeight += b.getPreferredSize().getHeight();
		}

		Double requirementGroupHeight = 0.0;
		for (Component b : requirementContent.getComponents()){
			requirementGroupHeight += b.getPreferredSize().getHeight();
		}

		Double userPermissionGroupHeight = 0.0;
		for (Component b : permissionContent.getComponents()){
			userPermissionGroupHeight += b.getPreferredSize().getHeight();
		}

		Double barChartGroupHeight = 0.0;
		for (Component b : permissionContent.getComponents()){
			barChartGroupHeight += b.getPreferredSize().getHeight();
		}

		Double viewUserPermissionGroupHeight = 0.0;
		for (Component b : permissionContent.getComponents()){
			viewUserPermissionGroupHeight += b.getPreferredSize().getHeight();
		}
		toolbarGroupManage.setPreferredSize(new Dimension(30 + ((int)(createSplit.getPreferredSize().getWidth())), iterationGroupHeight.intValue()));
		toolbarGroupUserPermission.setPreferredSize(new Dimension(45 + ((int)(viewUserPermission.getPreferredSize().getWidth())), userPermissionGroupHeight.intValue()));
		toolbarGroupResources.setPreferredSize(new Dimension(30 + ((int)(btnHelp.getPreferredSize().getWidth())), barChartGroupHeight.intValue()));

		
		toolbarGroupManage.setMinimumSize(toolbarGroupManage.getPreferredSize());
		toolbarGroupUserPermission.setMinimumSize(toolbarGroupUserPermission.getPreferredSize());
		toolbarGroupResources.setMinimumSize(toolbarGroupResources.getPreferredSize());
		
		addGroup(toolbarGroupManage);
		addGroup(toolbarGroupResources);
		addGroup(toolbarGroupUserPermission);


		final DefaultToolbarView p = this;
		p.addHierarchyListener(new HierarchyListener() {

			/**
			 * Shows changes to hierarchy
			 * @param e HierarchyEvent to respond to
			 * @see java.awt.event.HierarchyListener#hierarchyChanged(java.awt.event.HierarchyEvent)
			 */
			@Override
			public void hierarchyChanged(HierarchyEvent e) {
				if ( (HierarchyEvent.SHOWING_CHANGED & e.getChangeFlags()) != 0
						&& p.isShowing())
				{
					CurrentUserPermissions.updateCurrentUserPermissions();
				}
			}
		});
	}

	/**
	 * Set the viewUserPermission label.
	 * 
	 * @param s The string to be displayed.
	 */
	public void setPermissionText(String s){
		viewUserPermission.setText(s);
	}

	/**
	 * Set the viewUserPermission lable.
	 * 
	 * @param s The string to be displayed.
	 */
	public void setNameText(String s){
		viewUserPermission.setText(s);
	}

	/**
	 * Sets view level of buttons in the main toolbar panel, depending on permission level of user.
	 * 
	 * @param userPermissionLevel The permission level of the current user
	 */
	public void setToolbarDisplay(String userPermissionLevel){
		if(userPermissionLevel.equals("NONE")){
			createSplit.setVisible(false);
			editUserPermissions.setVisible(false);
			
			toolbarGroupManage.setVisible(true);
			toolbarGroupResources.setVisible(true);
			toolbarGroupUserPermission.setVisible(true);

		}
		else if(userPermissionLevel.equals("UPDATE")){	
			createSplit.setVisible(false);
			editUserPermissions.setVisible(false);
			
			toolbarGroupManage.setVisible(true);
			toolbarGroupResources.setVisible(true);
			toolbarGroupUserPermission.setVisible(true);

		}
		else{
			createSplit.setVisible(true);
			editUserPermissions.setVisible(true);
			
			toolbarGroupManage.setVisible(true);
			toolbarGroupResources.setVisible(true);
			toolbarGroupUserPermission.setVisible(true);
		}
		//override for the admin user - admin user should always be able to edit permissions
		if(CurrentUserPermissions.doesUserHavePermissionMaster(RMPermissionsLevel.ADMIN)){
			editUserPermissions.setVisible(true);
			toolbarGroupUserPermission.setVisible(true);
		}
	}
}
