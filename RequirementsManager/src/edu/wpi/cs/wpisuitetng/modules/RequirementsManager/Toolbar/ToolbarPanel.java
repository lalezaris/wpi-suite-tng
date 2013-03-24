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
 * @author Tyler Stone 
 * @author Arica Liu
 * 
 * @version March 21, 2013 
 */
@SuppressWarnings("serial")
public class ToolbarPanel extends DefaultToolbarView {

	private JButton newRequirement;
//	private JButton listButton;
	private JButton newIteration;
	private JButton listIteration;
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
		JPanel content2 = new JPanel();
		SpringLayout layout  = new SpringLayout();
		content.setLayout(layout);
		content.setOpaque(false);
		content2.setLayout(layout);
		content2.setOpaque(false);

		// Construct the buttons
		newIteration = new JButton("Create Iteration");
		newIteration.setAction(new NewIterationAction(tabController));
		
		listIteration = new JButton("List Iterations");
	//	newIteration.setAction(new NewRequirementAction(tabController));
		
		newRequirement = new JButton("Create Requirement");
		newRequirement.setAction(new NewRequirementAction(tabController));
		
//		listButton = new JButton("List Requirements");
//		listButton.setAction(new ListAction(tabController));
		
		// Configure the layout of the buttons on the content panel
		layout.putConstraint(SpringLayout.NORTH, newIteration, 5, SpringLayout.NORTH, content);
		layout.putConstraint(SpringLayout.WEST, newIteration, 8, SpringLayout.WEST, content);
		layout.putConstraint(SpringLayout.WEST, listIteration, 10, SpringLayout.EAST, newIteration);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, listIteration, 0, SpringLayout.VERTICAL_CENTER, newIteration);
		layout.putConstraint(SpringLayout.NORTH, newRequirement, 5, SpringLayout.NORTH, content2);
		layout.putConstraint(SpringLayout.WEST, newRequirement, 8, SpringLayout.WEST, content2);
		
		// Add buttons to the content panel
		content.add(newIteration);
		content.add(listIteration);
		content2.add(newRequirement);
//		content.add(listButton);
		
		// Construct a new toolbar group to be added to the end of the toolbar
		ToolbarGroupView toolbarGroup = new ToolbarGroupView("Iteration", content);
		ToolbarGroupView toolbarGroup2 = new ToolbarGroupView("Requirement", content2);
		
		// Calculate the width of the toolbar
		Double toolbarGroupWidth = newIteration.getPreferredSize().getWidth() + listIteration.getPreferredSize().getWidth() + 40; // 40 accounts for margins between the buttons
		Double toolbarGroupWidth2 = newRequirement.getPreferredSize().getWidth() + 40; // 40 accounts for margins between the buttons
		toolbarGroup.setPreferredWidth(toolbarGroupWidth.intValue());
		toolbarGroup2.setPreferredWidth(toolbarGroupWidth2.intValue());
		addGroup(toolbarGroup);
		addGroup(toolbarGroup2);
	}
}
