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

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.DefaultToolbarView;
import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.MainTabController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Toolbar.*;

/**
 * Toolbar Panel for Requirements Manager
 * 
 * @author Tyler Stone and Arica Liu
 */
@SuppressWarnings("serial")
public class ToolbarPanel extends DefaultToolbarView {

	private JButton newRequirement;
//	private JButton editRequirement;
//	private JButton deleteRequirement;
	
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

//		// Construct the edit button
//		editRequirement = new JButton("Edit");

		// Configure the layout of the buttons on the content panel
		layout.putConstraint(SpringLayout.NORTH, newRequirement, 5, SpringLayout.NORTH, content);
		layout.putConstraint(SpringLayout.WEST, newRequirement, 8, SpringLayout.WEST, content);
//		layout.putConstraint(SpringLayout.WEST, editRequirement, 10, SpringLayout.EAST, newRequirement);
//		layout.putConstraint(SpringLayout.VERTICAL_CENTER, editRequirement, 0, SpringLayout.VERTICAL_CENTER, newRequirement);

		// Add buttons to the content panel
		content.add(newRequirement);
//		content.add(editRequirement);
		
		// Construct a new toolbar group to be added to the end of the toolbar
		ToolbarGroupView toolbarGroup = new ToolbarGroupView("Home", content);

		// Calculate the width of the toolbar
		Double toolbarGroupWidth = newRequirement.getPreferredSize().getWidth() + 40; // 40 accounts for margins between the buttons
		toolbarGroup.setPreferredWidth(toolbarGroupWidth.intValue());
		addGroup(toolbarGroup);
	}
}
