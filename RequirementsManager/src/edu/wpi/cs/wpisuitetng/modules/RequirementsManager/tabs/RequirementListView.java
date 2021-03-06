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
 *  Tianyu Li
 *  Mike Perrone
 *  Chris Hanna
 *  
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.IToolbarGroupProvider;
import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.BatchRequirementEditController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.RetrieveAllRequirementsController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.model.Tab;

/**
 * The primary JPanel for the list of all requirements tab
 *
 * @author Tianyu Li
 *
 * @version Apr 11, 2013
 *
 */
@SuppressWarnings("serial")
public class RequirementListView extends JPanel implements IToolbarGroupProvider {

	private ToolbarGroupView buttonGroup;
	private JButton refreshButton, updateButton;
	private RequirementListPanel mainPanel;
	private RetrieveAllRequirementsController refreshController;
	private BatchRequirementEditController<Integer> updateController;
	final JScrollPane mainPanelScrollPane;
	private Tab containingTab;
	private boolean inputEnabled;
	
	/**
	 * Instantiates a new requirement list view.
	 *
	 * @param tab the tab
	 */
	public RequirementListView(Tab tab){
		containingTab = tab;

		inputEnabled = true;
		
		// Instantiate the button panel
		buttonGroup = new ToolbarGroupView("Requirements List");
		
		containingTab.setIcon(new ImageIcon());
		containingTab.setTitle("Requirements List");
		
		
		// Instantiate the main create requirement panel
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
		refreshController.refreshData();
	}
	
	/* Gets ToolbarGroupView
	 * @see edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.IToolbarGroupProvider#getGroup()
	 */
	@Override
	public ToolbarGroupView getGroup() {
		return buttonGroup;
	}

	/**
	 * Gets the mainPanel.
	 * 
	 * @return the mainPanel
	 */
	public RequirementListPanel getMainPanel() {
		return mainPanel;
	}
}