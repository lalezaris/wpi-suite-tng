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
	private JButton viewChart;
	@SuppressWarnings("unused")
	private String userName;

	private ToolbarGroupView toolbarGroupIteration;
	private ToolbarGroupView toolbarGroupRequirement;
	private ToolbarGroupView toolbarGroupUserPermission;
	private ToolbarGroupView toolbarGroupViewUserPermission;
	private ToolbarGroupView toolbarGroupBarChart;

	/**
	 * Create a ToolbarPanel.
	 * 
	 * @param tabController The MainTabController this view should open tabs with
	 */
	public ToolbarPanel(MainTabController tabController) {	
		// Construct the content panel
		JPanel iterationContent = new JPanel();
		JPanel requirementContent = new JPanel();
		JPanel userPermissionContent = new JPanel();
		JPanel viewUserPermissionPanel = new JPanel();
		JPanel chartContent = new JPanel();

		SpringLayout iterationLayout  = new SpringLayout();
		SpringLayout requirementLayout = new SpringLayout();
		SpringLayout userPermissionLayout = new SpringLayout();
		SpringLayout viewUserPermissionLayout = new SpringLayout();
		SpringLayout chartLayout = new SpringLayout();

		iterationContent.setLayout(iterationLayout);
		iterationContent.setOpaque(false);
		iterationContent.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));

		requirementContent.setLayout(requirementLayout);
		requirementContent.setOpaque(false);
		requirementContent.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));

		userPermissionContent.setLayout(userPermissionLayout);
		userPermissionContent.setOpaque(false);
		userPermissionContent.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));

		chartContent.setLayout(chartLayout);
		chartContent.setOpaque(false);
		chartContent.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		
		viewUserPermissionPanel.setLayout(viewUserPermissionLayout);
		viewUserPermissionPanel.setOpaque(false);
		//viewUserPermissionPanel.setBorder(BorderFactory.createRaisedBevelBorder());

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
		
		newRequirement = new JButton("Create Requirement");
		newRequirement.setAction(new NewRequirementAction(tabController));
		newRequirement.setVisible(false);

		//construct the list button
		listAllRequirements = new JButton("List Requirements");
		listAllRequirements.setAction(new ListAction(tabController));
		listAllRequirements.setVisible(false);
		listAllRequirements.setPreferredSize(newRequirement.getPreferredSize());//set equal to button above

		//construct the edit user permissions button
		editUserPermissions = new JButton("Edit User Permissions");
		editUserPermissions.setAction(new EditUserPermissionsAction(tabController));
		editUserPermissions.setVisible(false);

		//construct the user permission label
		userName = ConfigManager.getConfig().getUserName(); //returns wrong value under certain circumstances
		viewUserName = new JLabel("");//new JLabel("User: " + userName);
		viewUserPermission = new JLabel("Permission Level: \n" + CurrentUserPermissions.getCurrentUserPermission().toString());
		//viewUserPermission.setText("Permission Level: " + CurrentUserPermissions.getCurrentUserPermission().toString());

		//Construct Bar Chart Buttons
		viewChart = new JButton("Chart");
		viewChart.setAction(new ViewChartsAction(tabController));

		// Configure the layout of the buttons on the content panel
		iterationLayout.putConstraint(SpringLayout.NORTH, newIteration, 5, SpringLayout.NORTH, iterationContent);
		iterationLayout.putConstraint(SpringLayout.WEST, newIteration, 10, SpringLayout.WEST, iterationContent);
		
		iterationLayout.putConstraint(SpringLayout.NORTH, listIteration, 10, SpringLayout.SOUTH, newIteration);
		iterationLayout.putConstraint(SpringLayout.WEST, listIteration, 10, SpringLayout.WEST, iterationContent);
		
		requirementLayout.putConstraint(SpringLayout.NORTH, newRequirement, 5, SpringLayout.NORTH, requirementContent);
		requirementLayout.putConstraint(SpringLayout.WEST, newRequirement, 10, SpringLayout.WEST, requirementContent);
		
		requirementLayout.putConstraint(SpringLayout.NORTH, listAllRequirements, 10, SpringLayout.SOUTH, newRequirement);
		requirementLayout.putConstraint(SpringLayout.WEST, listAllRequirements, 10, SpringLayout.WEST, requirementContent);
		
		userPermissionLayout.putConstraint(SpringLayout.NORTH, editUserPermissions, 25, SpringLayout.NORTH, userPermissionContent);
		userPermissionLayout.putConstraint(SpringLayout.WEST, editUserPermissions, 10, SpringLayout.WEST, userPermissionContent);
		
		chartLayout.putConstraint(SpringLayout.NORTH, viewChart, 25, SpringLayout.NORTH, chartContent);
		chartLayout.putConstraint(SpringLayout.WEST, viewChart, 10, SpringLayout.WEST, chartContent);
		
		viewUserPermissionLayout.putConstraint(SpringLayout.NORTH, viewUserName, 5, SpringLayout.NORTH, viewUserPermissionPanel);
		viewUserPermissionLayout.putConstraint(SpringLayout.WEST, viewUserName, 10, SpringLayout.WEST, viewUserPermissionPanel);

		viewUserPermissionLayout.putConstraint(SpringLayout.NORTH, viewUserPermission, 10, SpringLayout.SOUTH, viewUserName);
		viewUserPermissionLayout.putConstraint(SpringLayout.WEST, viewUserPermission, 10, SpringLayout.WEST, viewUserPermissionPanel);
		
		viewUserPermissionLayout.putConstraint(SpringLayout.NORTH, viewUserPermission, 10, SpringLayout.SOUTH, viewUserName);
		viewUserPermissionLayout.putConstraint(SpringLayout.WEST, viewUserPermission, 10, SpringLayout.WEST, viewUserPermissionPanel);

		// Add buttons to the content panel
		iterationContent.add(newIteration);
		iterationContent.add(listIteration);

		// Add buttons to the content panel
		requirementContent.add(newRequirement);
		requirementContent.add(listAllRequirements);
		
		// Add buttons to the content panel
		userPermissionContent.add(editUserPermissions);

		//Add chart buttons
		chartContent.add(viewChart);

		//add label to content panel
		viewUserPermissionPanel.add(viewUserPermission);
		viewUserPermissionPanel.add(viewUserName);


		// Construct a new toolbar group to be added to the end of the toolbar
		toolbarGroupIteration = new ToolbarGroupView("Iteration", iterationContent);
		toolbarGroupIteration.setVisible(false);
		
		toolbarGroupRequirement = new ToolbarGroupView("Requirement", requirementContent);
		toolbarGroupRequirement.setVisible(false);

		toolbarGroupUserPermission = new ToolbarGroupView("Permissions", userPermissionContent);
		toolbarGroupUserPermission.setVisible(false);
		
		toolbarGroupBarChart = new ToolbarGroupView("Charts", chartContent);
		
		toolbarGroupViewUserPermission = new ToolbarGroupView("User Information", viewUserPermissionPanel);


		// Calculate the width of the toolbar
		Double iterationGroupHeight = 0.0;
		for (Component b : iterationContent.getComponents()){
			iterationGroupHeight += b.getPreferredSize().getHeight();
		}

		Double requirementGroupHeight = 0.0;
		for (Component b : requirementContent.getComponents()){
			requirementGroupHeight += b.getPreferredSize().getHeight();
		}

		Double userPermissionGroupHeight = 0.0;
		for (Component b : userPermissionContent.getComponents()){
			userPermissionGroupHeight += b.getPreferredSize().getHeight();
		}
		
		Double barChartGroupHeight = 0.0;
		for (Component b : userPermissionContent.getComponents()){
			barChartGroupHeight += b.getPreferredSize().getHeight();
		}
		
		Double viewUserPermissionGroupHeight = 0.0;
		for (Component b : userPermissionContent.getComponents()){
			viewUserPermissionGroupHeight += b.getPreferredSize().getHeight();
		}
		
		toolbarGroupIteration.setPreferredSize(new Dimension(30 + ((int)(newIteration.getPreferredSize().getWidth())), iterationGroupHeight.intValue()));
		toolbarGroupRequirement.setPreferredSize(new Dimension(30 + ((int)(newRequirement.getPreferredSize().getWidth())), requirementGroupHeight.intValue()));
		toolbarGroupUserPermission.setPreferredSize(new Dimension(30 + ((int)(editUserPermissions.getPreferredSize().getWidth())), userPermissionGroupHeight.intValue()));
		toolbarGroupBarChart.setPreferredSize(new Dimension(30 + ((int)(viewChart.getPreferredSize().getWidth())), barChartGroupHeight.intValue()));
		toolbarGroupViewUserPermission.setPreferredSize(new Dimension(30 + ((int)(viewUserPermission.getPreferredSize().getWidth())), viewUserPermissionGroupHeight.intValue()));

		
		toolbarGroupIteration.setMinimumSize(toolbarGroupIteration.getPreferredSize());
		toolbarGroupRequirement.setMinimumSize(toolbarGroupRequirement.getPreferredSize());
		toolbarGroupUserPermission.setMinimumSize(toolbarGroupUserPermission.getPreferredSize());
		toolbarGroupBarChart.setMinimumSize(toolbarGroupBarChart.getPreferredSize());
		toolbarGroupViewUserPermission.setMinimumSize(toolbarGroupViewUserPermission.getPreferredSize());
		
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
			newIteration.setVisible(false);
			listIteration.setVisible(true);
			newRequirement.setVisible(false);
			listAllRequirements.setVisible(true);		
			editUserPermissions.setVisible(false);
			
			toolbarGroupIteration.setVisible(true);
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
		if(CurrentUserPermissions.doesUserHavePermissionMaster(RMPermissionsLevel.ADMIN)){
			editUserPermissions.setVisible(true);
			toolbarGroupUserPermission.setVisible(true);
		}
	}
}
