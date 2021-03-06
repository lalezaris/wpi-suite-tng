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
 *  Lauren Kahn
 **************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.IToolbarGroupProvider;
import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Iteration.controller.RetrieveAllIterationsController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.action.IterationsRefreshAction;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.model.Tab;

/**
 * The primary JPanel for the list of all iterations tab.
 *
 * @author Lauren Kahn
 * @version Apr 8, 2013
 */
@SuppressWarnings("serial")
public class IterationListView extends JPanel implements IToolbarGroupProvider {
	private ToolbarGroupView buttonGroup;
	private JButton refreshButton;
	private IterationListPanel mainPanel;
	private RetrieveAllIterationsController controller;
	final JScrollPane mainPanelScrollPane;
	private Tab containingTab;
	private boolean inputEnabled;

	/**
	 * Instantiates a new iteration list view.
	 *
	 * @param tab the tab
	 */
	public IterationListView(Tab tab){
		containingTab = tab;

		inputEnabled = true;

		// Instantiate the button panel
		buttonGroup = new ToolbarGroupView("Iterations List");

		containingTab.setIcon(new ImageIcon());
		containingTab.setTitle("Iterations List");

		// Instantiate the main create iteration panel
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
		controller.refreshData();
		// Instantiate the save button and add it to the button panel
		refreshButton = new JButton("Refresh");
		refreshButton.setAction(new IterationsRefreshAction(controller));
		buttonGroup.getContent().add(refreshButton);
		buttonGroup.setPreferredWidth(150);
	}

	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.IToolbarGroupProvider#getGroup()
	 */
	@Override
	public ToolbarGroupView getGroup() {
		return buttonGroup;
	}
}