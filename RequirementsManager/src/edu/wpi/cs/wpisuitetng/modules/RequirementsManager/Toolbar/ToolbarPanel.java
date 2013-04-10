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
**************************************************/

package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Toolbar;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.border.EtchedBorder;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.DefaultToolbarView;
import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Toolbar.action.EditUserPermissionsAction;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Toolbar.action.ListAction;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Toolbar.action.ListIterationAction;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Toolbar.action.NewIterationAction;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Toolbar.action.NewRequirementAction;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Toolbar.action.ViewChartsAction;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.observers.CurrentUserPermissions;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.controller.MainTabController;

/**
 * Toolbar Panel for Requirements Manager
 * 
 * @author Tyler Stone 
 * @author Arica Liu
 * @edited Michael French
 * @edited Evan Polekoff
 * @edited Ned Shelton
 * @edited Tushar Narayan
 * 
 * @version April 7, 2013 
 */
@SuppressWarnings("serial")
public class ToolbarPanel extends DefaultToolbarView {

	private JButton newRequirement;
	private JButton listAllRequirements;
	private JButton newIteration;
	private JButton listIteration;
	private JButton editUserPermissions;
	private JLabel viewUserPermission;
	private JLabel viewUserName;
	private JButton barChart;
	private String userName;

	private ToolbarGroupView toolbarGroupIteration;
	private ToolbarGroupView toolbarGroupRequirement;
	private ToolbarGroupView toolbarGroupUserPermission;
	private ToolbarGroupView toolbarGroupViewUserPermission;
	private ToolbarGroupView toolbarGroupBarChart;
		
	
	
	//TODO: Reorder everything so that barchart comes before viewuserpermissionpanel
	/**
	 * Create a ToolbarPanel.
	 * Commented out parts are not needed for iteration 1 but are needed in the future
	 * 
	 * @param tabController The MainTabController this view should open tabs with
	 */
	public ToolbarPanel(MainTabController tabController) {	
		// Construct the content panel
		JPanel iterationContent = new JPanel();
		JPanel requirementContent = new JPanel();
		JPanel userPermissionContent = new JPanel();
		JPanel viewUserPermissionPanel = new JPanel();
		JPanel barChartContent = new JPanel();
		
		SpringLayout iterationLayout  = new SpringLayout();
		SpringLayout requirementLayout = new SpringLayout();
		SpringLayout userPermissionLayout = new SpringLayout();
		SpringLayout viewUserPermissionLayout = new SpringLayout();
		SpringLayout barChartLayout = new SpringLayout();
		
		iterationContent.setLayout(iterationLayout);
		iterationContent.setOpaque(false);
		iterationContent.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		
		requirementContent.setLayout(requirementLayout);
		requirementContent.setOpaque(false);
		requirementContent.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		
		userPermissionContent.setLayout(userPermissionLayout);
		userPermissionContent.setOpaque(false);
		userPermissionContent.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));

		viewUserPermissionPanel.setLayout(viewUserPermissionLayout);
		viewUserPermissionPanel.setOpaque(false);
		//viewUserPermissionPanel.setBorder(BorderFactory.createRaisedBevelBorder());
		
		barChartContent.setLayout(barChartLayout);
		barChartContent.setOpaque(false);
		barChartContent.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		
		CurrentUserPermissions.updateCurrentUserPermissions(new PermissionDisplayUpdater(this));
		CurrentUserPermissions.updateCurrentUserPermissions(new ToolbarDisplayUpdater(this));
		
		// Construct the buttons
		newIteration = new JButton("Create Iteration");
		newIteration.setAction(new NewIterationAction(tabController));
		newIteration.setVisible(false);
		
		listIteration = new JButton("List Iterations");
		listIteration.setAction(new ListIterationAction("List Iterations"));
		listIteration.setVisible(false);
		
		newRequirement = new JButton("Create Requirement");
		newRequirement.setAction(new NewRequirementAction(tabController));
		newRequirement.setVisible(false);
		
		//construct the list button
		listAllRequirements = new JButton("List Requirements");
		listAllRequirements.setAction(new ListAction(tabController));
		listAllRequirements.setVisible(false);
		
		//construct the edit user permissions button
		editUserPermissions = new JButton("Edit User Permissions");
		editUserPermissions.setAction(new EditUserPermissionsAction(tabController));
		editUserPermissions.setVisible(false);
		
		//construct the user permission label
		userName = ConfigManager.getConfig().getUserName(); //returns wrong value under certain circumstances
		viewUserName = new JLabel("User: " + userName);
		viewUserPermission = new JLabel("Permission Level: " + CurrentUserPermissions.getCurrentUserPermission().toString());
		//viewUserPermission.setText("Permission Level: " + CurrentUserPermissions.getCurrentUserPermission().toString());
		
		//Construct Bar Chart Buttons
		barChart = new JButton("Bar Chart");
		barChart.setAction(new ViewChartsAction(tabController));
		
		// Configure the layout of the buttons on the content panel
		requirementLayout.putConstraint(SpringLayout.NORTH, newRequirement, 25, SpringLayout.NORTH, requirementContent);
		requirementLayout.putConstraint(SpringLayout.WEST, newRequirement, 8, SpringLayout.WEST, requirementContent);
		requirementLayout.putConstraint(SpringLayout.WEST, listAllRequirements, 10, SpringLayout.EAST, newRequirement);
		requirementLayout.putConstraint(SpringLayout.VERTICAL_CENTER, listAllRequirements, 0, SpringLayout.VERTICAL_CENTER, newRequirement);

		
		iterationLayout.putConstraint(SpringLayout.NORTH, newIteration, 25, SpringLayout.NORTH, iterationContent);
		iterationLayout.putConstraint(SpringLayout.WEST, newIteration, 8, SpringLayout.WEST, iterationContent);
		iterationLayout.putConstraint(SpringLayout.WEST, listIteration, 10, SpringLayout.EAST, newIteration);
		iterationLayout.putConstraint(SpringLayout.VERTICAL_CENTER, listIteration, 0, SpringLayout.VERTICAL_CENTER, newIteration);
		
		userPermissionLayout.putConstraint(SpringLayout.NORTH, editUserPermissions, 25, SpringLayout.NORTH, userPermissionContent);
		userPermissionLayout.putConstraint(SpringLayout.WEST, editUserPermissions, 8, SpringLayout.WEST, userPermissionContent);
		
		viewUserPermissionLayout.putConstraint(SpringLayout.NORTH, viewUserPermission, 40, SpringLayout.NORTH, viewUserPermissionPanel);
		viewUserPermissionLayout.putConstraint(SpringLayout.WEST, viewUserPermission, 8, SpringLayout.WEST, viewUserPermissionPanel);
		viewUserPermissionLayout.putConstraint(SpringLayout.NORTH, viewUserName, 20, SpringLayout.NORTH, viewUserPermissionPanel);
		viewUserPermissionLayout.putConstraint(SpringLayout.WEST, viewUserName, 8, SpringLayout.WEST, viewUserPermissionPanel);
		
		barChartLayout.putConstraint(SpringLayout.NORTH, barChart, 25, SpringLayout.NORTH, barChartContent);
		barChartLayout.putConstraint(SpringLayout.WEST, barChart, 8, SpringLayout.WEST, barChartContent);
		
		// Add buttons to the content panel
		requirementContent.add(newRequirement);
		requirementContent.add(listAllRequirements);
		
		// Add buttons to the content panel
		iterationContent.add(newIteration);
//		iterationContent.add(listIteration);
		
		// Add buttons to the content panel
		userPermissionContent.add(editUserPermissions);
		
		//Add bar chart buttons
		barChartContent.add(barChart);
		
		//add label to content panel
		viewUserPermissionPanel.add(viewUserPermission);
		viewUserPermissionPanel.add(viewUserName);

		
		// Construct a new toolbar group to be added to the end of the toolbar
		toolbarGroupIteration = new ToolbarGroupView("Iteration", iterationContent);
		toolbarGroupIteration.setVisible(false);
		toolbarGroupRequirement = new ToolbarGroupView("Requirement", requirementContent);
		toolbarGroupRequirement.setVisible(false);
		toolbarGroupUserPermission = new ToolbarGroupView("Edit User Permissions", userPermissionContent);
		toolbarGroupUserPermission.setVisible(false);
		toolbarGroupViewUserPermission = new ToolbarGroupView("User Information", viewUserPermissionPanel);
		toolbarGroupBarChart = new ToolbarGroupView("View Charts", barChartContent);

		// Calculate the width of the toolbar
		Double iterationGroupWidth = 0.0;
		for (Component b : iterationContent.getComponents()){
			iterationGroupWidth += b.getPreferredSize().getWidth() + 20;
		}
		
		Double requirementGroupWidth = 0.0;
		for (Component b : requirementContent.getComponents()){
			requirementGroupWidth += b.getPreferredSize().getWidth() + 20;
		}
		
		Double userPermissionGroupWidth = 0.0;
		for (Component b : userPermissionContent.getComponents()){
			userPermissionGroupWidth += b.getPreferredSize().getWidth() + 20;
		}
		
		Double viewUserPermissionGroupWidth = 0.0;
		for (Component b : userPermissionContent.getComponents()){
			viewUserPermissionGroupWidth += b.getPreferredSize().getWidth() + 40;
		}
		
		Double barChartGroupWidth = 0.0;
		for (Component b : userPermissionContent.getComponents()){
			barChartGroupWidth += b.getPreferredSize().getWidth() + 40;
		}

		toolbarGroupIteration.setPreferredWidth(iterationGroupWidth.intValue());
		toolbarGroupRequirement.setPreferredWidth(requirementGroupWidth.intValue());
		toolbarGroupUserPermission.setPreferredWidth(userPermissionGroupWidth.intValue());
		toolbarGroupViewUserPermission.setPreferredWidth(viewUserPermissionGroupWidth.intValue());
		toolbarGroupBarChart.setPreferredWidth(barChartGroupWidth.intValue());
		
		addGroup(toolbarGroupIteration);
		addGroup(toolbarGroupRequirement);
		addGroup(toolbarGroupUserPermission);
		addGroup(toolbarGroupBarChart);
		addGroup(toolbarGroupViewUserPermission);
		
		final DefaultToolbarView p = this;
		p.addHierarchyListener(new HierarchyListener() {

			/* Shows changes to hierarchy
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
	
	public void setPermissionText(String s){
		viewUserPermission.setText(s);
	}
	
	public void setNameText(String s){
		viewUserPermission.setText(s);
	}
	
	/**
	 * Sets view level of buttons in the main toolbar panel, depending on permission level of user.
	 * 
	 * @param userPermissionLevel the permission level of the current user
	 */
	public void setToolbarDisplay(String userPermissionLevel){
		if(userPermissionLevel.equals("NONE")){
			newIteration.setVisible(false);
			listIteration.setVisible(true);
			newRequirement.setVisible(false);
			listAllRequirements.setVisible(true);		
			editUserPermissions.setVisible(false);
			toolbarGroupIteration.setVisible(false);
			toolbarGroupRequirement.setVisible(true);
			toolbarGroupUserPermission.setVisible(false);
		}
		else if(userPermissionLevel.equals("UPDATE")){
			newIteration.setVisible(false);
			listIteration.setVisible(true);
			newRequirement.setVisible(false);
			listAllRequirements.setVisible(true);		
			editUserPermissions.setVisible(false);
			toolbarGroupIteration.setVisible(true);
			toolbarGroupRequirement.setVisible(true);
			toolbarGroupUserPermission.setVisible(false);
		}
		else{//must be ADMIN
			newIteration.setVisible(true);
			listIteration.setVisible(true);
			newRequirement.setVisible(true);
			listAllRequirements.setVisible(true);		
			editUserPermissions.setVisible(true);
			toolbarGroupIteration.setVisible(true);
			toolbarGroupRequirement.setVisible(true);
			toolbarGroupUserPermission.setVisible(true);
		}
		//override for the admin user - admin user should always be able to edit permissions
		if(this.userName.equals("admin")){
			editUserPermissions.setVisible(true);
			toolbarGroupUserPermission.setVisible(true);
		}
	}
}
