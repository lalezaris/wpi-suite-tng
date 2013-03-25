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
**************************************************/

package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Toolbar;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.DefaultToolbarView;
import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.DashboardTab;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.MainTabController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Toolbar.*;

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
	private JButton editRequirement;
	private JButton deleteRequirement;
	private JButton listAllRequirements;
	private int selectedRequirement;
	
	/**
	 * Create a ToolbarPanel.
	 * Commented out parts are not needed for iteration 1 but are needed in the future
	 * 
	 * @param tabController The MainTabController this view should open tabs with
	 */
	
	public ToolbarPanel(MainTabController tabController) {	
		// Construct the content panel
		JPanel content = new JPanel();
		SpringLayout layout  = new SpringLayout();
		content.setLayout(layout);
		content.setOpaque(false);

		// Construct the new button
		newRequirement = new JButton("New");
		newRequirement.setAction(new NewRequirementAction(tabController));
		
		// Construct the edit button
		editRequirement = new JButton("Edit");
		//editRequirement.setAction(new EditRequirementAction(tabController));
		
		deleteRequirement = new JButton("Delete");
		//deleteRequirement.setAction(new DeleteRequirementAction(tabController));
		
		//construct the list button
		listAllRequirements = new JButton("List Requirements");
		listAllRequirements.setAction(new ListAction(tabController));
		
		// Configure the layout of the buttons on the content panel
		layout.putConstraint(SpringLayout.NORTH, newRequirement, 25, SpringLayout.NORTH, content);
		layout.putConstraint(SpringLayout.WEST, newRequirement, 8, SpringLayout.WEST, content);
		layout.putConstraint(SpringLayout.WEST, editRequirement, 10, SpringLayout.EAST, newRequirement);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, editRequirement, 0, SpringLayout.VERTICAL_CENTER, newRequirement);
		layout.putConstraint(SpringLayout.WEST, deleteRequirement, 10, SpringLayout.EAST, editRequirement);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, deleteRequirement, 0, SpringLayout.VERTICAL_CENTER, editRequirement);
		layout.putConstraint(SpringLayout.WEST, listAllRequirements, 10, SpringLayout.EAST, deleteRequirement);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, listAllRequirements, 0, SpringLayout.VERTICAL_CENTER, deleteRequirement);
		
		editRequirement.setEnabled(false);
		deleteRequirement.setEnabled(false);
		
		// Add buttons to the content panel
		content.add(newRequirement);
		content.add(editRequirement);
		content.add(deleteRequirement);
		content.add(listAllRequirements);
		// Construct a new toolbar group to be added to the end of the toolbar
		ToolbarGroupView toolbarGroup = new ToolbarGroupView("Home", content);
		
		// Calculate the width of the toolbar
		//Double toolbarGroupWidth = 3 * newRequirement.getPreferredSize().getWidth() + 50; // 50 accounts for margins between the buttons
		
		//Chris Hanna changed the above calculation to this one...
		Double toolbarGroupWidth = 0.0;
		for (Component b : content.getComponents()){
			toolbarGroupWidth += b.getPreferredSize().getWidth() + 20;
		}
		
		toolbarGroup.setPreferredWidth(toolbarGroupWidth.intValue());
		addGroup(toolbarGroup);
	}
	
	public void setSelectedRequirement(int selectedRequirement) {
		this.selectedRequirement = selectedRequirement;
		
		if (selectedRequirement < 0) {
			editRequirement.setEnabled(false);
			deleteRequirement.setEnabled(false);
		} else {
			editRequirement.setEnabled(true);
			deleteRequirement.setEnabled(true);
		}
	}
	
	public int getSelectedRequirement() {
		return selectedRequirement;
	}
}
