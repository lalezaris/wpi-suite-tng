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
	private JButton listAllRequirements;
	private int selectedRequirement;
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
		JPanel iterationContent = new JPanel();
		JPanel requirementContent = new JPanel();
		
		SpringLayout iterationLayout  = new SpringLayout();
		SpringLayout requirementLayout = new SpringLayout();
		
		iterationContent.setLayout(iterationLayout);
		iterationContent.setOpaque(false);
		
		requirementContent.setLayout(requirementLayout);
		requirementContent.setOpaque(false);

		// Construct the buttons
		newIteration = new JButton("Create Iteration");
		newIteration.setAction(new NewIterationAction(tabController));
		
		listIteration = new JButton("List Iterations");
	//	newIteration.setAction(new NewRequirementAction(tabController));
		
		newRequirement = new JButton("Create Requirement");
		newRequirement.setAction(new NewRequirementAction(tabController));
		
		//construct the list button
		listAllRequirements = new JButton("List Requirements");
		listAllRequirements.setAction(new ListAction(tabController));
		
		// Configure the layout of the buttons on the content panel
		requirementLayout.putConstraint(SpringLayout.NORTH, newRequirement, 25, SpringLayout.NORTH, requirementContent);
		requirementLayout.putConstraint(SpringLayout.WEST, newRequirement, 8, SpringLayout.WEST, requirementContent);
		requirementLayout.putConstraint(SpringLayout.WEST, listAllRequirements, 10, SpringLayout.EAST, newRequirement);
		requirementLayout.putConstraint(SpringLayout.VERTICAL_CENTER, listAllRequirements, 0, SpringLayout.VERTICAL_CENTER, newRequirement);

		
		iterationLayout.putConstraint(SpringLayout.NORTH, newIteration, 25, SpringLayout.NORTH, iterationContent);
		iterationLayout.putConstraint(SpringLayout.WEST, newIteration, 8, SpringLayout.WEST, iterationContent);
		iterationLayout.putConstraint(SpringLayout.WEST, listIteration, 10, SpringLayout.EAST, newIteration);
		iterationLayout.putConstraint(SpringLayout.VERTICAL_CENTER, listIteration, 0, SpringLayout.VERTICAL_CENTER, newIteration);

		//editRequirement.setEnabled(false);
		//deleteRequirement.setEnabled(false);
		
		// Add buttons to the content panel
		requirementContent.add(newRequirement);
		requirementContent.add(listAllRequirements);
//		listButton = new JButton("List Requirements");
//		listButton.setAction(new ListAction(tabController));
		
//		// Configure the layout of the buttons on the content panel
//		layout.putConstraint(SpringLayout.NORTH, newIteration, 5, SpringLayout.NORTH, content);
//		layout.putConstraint(SpringLayout.WEST, newIteration, 8, SpringLayout.WEST, content);
//		layout.putConstraint(SpringLayout.WEST, listIteration, 10, SpringLayout.EAST, newIteration);
//		layout.putConstraint(SpringLayout.VERTICAL_CENTER, listIteration, 0, SpringLayout.VERTICAL_CENTER, newIteration);
//		layout.putConstraint(SpringLayout.NORTH, newRequirement, 5, SpringLayout.NORTH, content2);
//		layout.putConstraint(SpringLayout.WEST, newRequirement, 8, SpringLayout.WEST, content2);
//		
		// Add buttons to the content panel
		iterationContent.add(newIteration);
		iterationContent.add(listIteration);
//		content.add(listButton);
		
		// Construct a new toolbar group to be added to the end of the toolbar
		ToolbarGroupView toolbarGroupIteration = new ToolbarGroupView("Iteration", iterationContent);
		ToolbarGroupView toolbarGroupRequirement = new ToolbarGroupView("Requirement", requirementContent);
		
		
		// Calculate the width of the toolbar
		//Double toolbarGroupWidth = 3 * newRequirement.getPreferredSize().getWidth() + 50; // 50 accounts for margins between the buttons
		
		//Chris Hanna changed the above calculation to this one...
		Double iterationGroupWidth = 0.0;
		for (Component b : iterationContent.getComponents()){
			iterationGroupWidth += b.getPreferredSize().getWidth() + 20;
		}
		
		Double requirementGroupWidth = 0.0;
		for (Component b : requirementContent.getComponents()){
			requirementGroupWidth += b.getPreferredSize().getWidth() + 20;
		}
		
		//Double toolbarGroupWidthIteration = newIteration.getPreferredSize().getWidth() + listIteration.getPreferredSize().getWidth() + 40; // 40 accounts for margins between the buttons
		//Double toolbarGroupWidthRequirement = newRequirement.getPreferredSize().getWidth() + 40; // 40 accounts for margins between the buttons
		
		toolbarGroupIteration.setPreferredWidth(iterationGroupWidth.intValue());
		toolbarGroupRequirement.setPreferredWidth(requirementGroupWidth.intValue());
		
		addGroup(toolbarGroupIteration);
		addGroup(toolbarGroupRequirement);
	}
	
	public int getSelectedRequirement() {
		return selectedRequirement;
	}
}
