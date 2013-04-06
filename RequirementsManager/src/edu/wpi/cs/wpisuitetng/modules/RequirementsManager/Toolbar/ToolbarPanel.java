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
 *  Tushar Narayan
**************************************************/

package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Toolbar;

import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.DefaultToolbarView;
import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Toolbar.action.EditUserPermissionsAction;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Toolbar.action.ListAction;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Toolbar.action.ListIterationAction;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Toolbar.action.NewIterationAction;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Toolbar.action.NewRequirementAction;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.RMPermissionsLevel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.action.Refresher;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.action.RefresherMode;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.CurrentUserPermissions;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.controller.MainTabController;

/**
 * Toolbar Panel for Requirements Manager
 * 
 * @author Tyler Stone 
 * @author Arica Liu
 * 
 * @version March 21, 2013 
 */
@SuppressWarnings("serial")
public class ToolbarPanel extends DefaultToolbarView {

	private JButton newRequirement;
	private JButton listAllRequirements;
	private JButton newIteration;
	private JButton listIteration;
	private JButton editUserPermissions;
	private RMPermissionsLevel pLevel;
	
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
		
		SpringLayout iterationLayout  = new SpringLayout();
		SpringLayout requirementLayout = new SpringLayout();
		SpringLayout userPermissionLayout = new SpringLayout();
		
		iterationContent.setLayout(iterationLayout);
		iterationContent.setOpaque(false);
		
		requirementContent.setLayout(requirementLayout);
		requirementContent.setOpaque(false);
		
		userPermissionContent.setLayout(userPermissionLayout);
		userPermissionContent.setOpaque(false);

		//this.pLevel = CurrentUserPermissions.getCurrentUserPermission();

		CurrentUserPermissions.updateCurrentUserPermissions();
		this.pLevel = CurrentUserPermissions.getCurrentUserPermission();
		System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n" + pLevel + "\n\n\n\n\n\n\n\n\n");
		
		/*
		 * The problem is that the server doesn't return the permissions in time.
		 * Thus, permission checks always get a NONE by default for the permission level
		 * and no buttons are displayed on the toolbar.
		 */
		
		// Construct the buttons
		newIteration = new JButton("Create Iteration");
		newIteration.setAction(new NewIterationAction(tabController));
		
		listIteration = new JButton("List Iterations");
		listIteration.setAction(new ListIterationAction("List Iterations"));
		
		newRequirement = new JButton("Create Requirement");
		newRequirement.setAction(new NewRequirementAction(tabController));
		
		//construct the list button
		listAllRequirements = new JButton("List Requirements");
		listAllRequirements.setAction(new ListAction(tabController));
		
		//construct the edit user permissions button
		editUserPermissions = new JButton("Edit User Permissions");
		editUserPermissions.setAction(new EditUserPermissionsAction(tabController));
		
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

		//debug message:
		System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n" + pLevel + "\n\n\n\n\n\n\n\n\n");
		// Add buttons to the content panel
		//all users can view the list of requirements
		//all users can view the list of iterations
		//TOCHECK: What can update users do and not do?
		if(pLevel == RMPermissionsLevel.ADMIN){
			requirementContent.add(newRequirement);
			requirementContent.add(listAllRequirements);
			iterationContent.add(newIteration);
			//to be put back in once we have an iteration view
			//iterationContent.add(listIteration);
			userPermissionContent.add(editUserPermissions);
		}
		
		// Construct a new toolbar group to be added to the end of the toolbar
		ToolbarGroupView toolbarGroupIteration = new ToolbarGroupView("Iteration", iterationContent);
		ToolbarGroupView toolbarGroupRequirement = new ToolbarGroupView("Requirement", requirementContent);
		ToolbarGroupView toolbarGroupUserPermission = new ToolbarGroupView("Edit User Permissions", userPermissionContent);
		
		// Calculate the width of the toolbar
		//Chris Hanna changed the above calculation to this one...
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

		toolbarGroupIteration.setPreferredWidth(iterationGroupWidth.intValue());
		toolbarGroupRequirement.setPreferredWidth(requirementGroupWidth.intValue());
		toolbarGroupUserPermission.setPreferredWidth(userPermissionGroupWidth.intValue());
		
		addGroup(toolbarGroupIteration);
		addGroup(toolbarGroupRequirement);
		addGroup(toolbarGroupUserPermission);
		
		
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
}
