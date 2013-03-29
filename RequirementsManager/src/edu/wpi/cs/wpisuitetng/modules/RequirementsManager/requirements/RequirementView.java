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
 *  Joe Spicola
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.IToolbarGroupProvider;
import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementPanel.Mode;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.SaveRequirementController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.model.DummyTab;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.model.Tab;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;

/**
 * Allows users to view Requirements
 * Adapted from DefectView in project DefectTracker
 * 
 * @author Chris Dunkers 
 * @author Joe Spicola
 *
 * @version Mar 17, 2013
 *
 */
public class RequirementView extends JPanel implements IToolbarGroupProvider {
	
	private ToolbarGroupView buttonGroup;
	private JButton saveButton;
	private RequirementPanel mainPanel;
	private SaveRequirementController controller;
	final JScrollPane mainPanelScrollPane;
	private Tab containingTab;
	private boolean inputEnabled;
	
	/**
	 * Constructs a new RequirementView where the user can view (and edit) a requirement.
	 * 
	 * @param requirement	The requirement to show.
	 * @param editMode	The editMode for editing the Requirement
	 * @param tab		The Tab holding this RequirementView (can be null)
	 */
	public RequirementView(Requirement requirement, Mode editMode, Tab tab) {
		containingTab = tab;
		if(containingTab == null) {
			containingTab = new DummyTab();
		}
		
		inputEnabled = true;
		
		containingTab.setIcon(new ImageIcon());
		if(editMode == Mode.CREATE) {
			containingTab.setTitle("Create Requirement");
			containingTab.setToolTipText("Create a new requirement");
		} else {
			setEditModeDescriptors(requirement);
		}
		
		// If this is a new requirement, set the creator
		if (editMode == Mode.CREATE) {
			requirement.setCreator(new User("", ConfigManager.getConfig().getUserName(), "", -1));
		}
		
		// Instantiate the main create requirement panel
		mainPanel = new RequirementPanel(this, requirement, editMode);
		
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
		controller = new SaveRequirementController(this);
	}


	/**
	 * Returns the main panel with the data fields
	 * 
	 * @return the main panel with the data fields
	 */
	public JPanel getRequirementPanel() {
		return mainPanel;
	}
	
	/* 
	 * @see edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.IToolbarGroupProvider#getGroup()
	 */
	@Override
	public ToolbarGroupView getGroup() {
		return buttonGroup;
	}
	
	/**
	 * @param defect Set the tab title, tooltip, and group name according to this Defect
	 */
	public void setEditModeDescriptors(Requirement requirement) {
		containingTab.setTitle("Requirement #" + requirement.getId());
		containingTab.setToolTipText("View requirement " + requirement.getTitle());
	}
	
	/**
	 * Sets whether the input is enabled
	 * 
	 * @param enabled
	 */
	public void setInputEnabled(boolean enabled) {
	    inputEnabled = enabled;
	
	    mainPanel.setInputEnabled(enabled);
	}
	
	/**
	 * @return the containing tab
	 */
	public Tab getTab() {
		return containingTab;
	}
	
	/**
	 * Revalidates and repaints the scroll pane containing the DefectPanel
	 */
	public void refreshScrollPane() {
		mainPanelScrollPane.revalidate();
		mainPanelScrollPane.repaint();
	}
}
