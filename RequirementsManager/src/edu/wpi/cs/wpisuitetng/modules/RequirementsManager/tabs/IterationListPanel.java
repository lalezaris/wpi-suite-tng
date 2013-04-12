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

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableModel;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Iteration.IterationView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Iteration.controller.RetrieveAllIterationsController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Iteration.controller.RetrieveIterationController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Iteration;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.IterationStatus;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.action.Refresher;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.action.IterationsRefreshAction;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.controller.MainTabController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.model.DummyTab;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.model.IterationTableModel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.model.Tab;

/**
 * The innermost JPanel for the list of all iterations tab,
 * which displays the iteration's information.
 *
 * @author Lauren Kahn
 *
 * @version Apr 8, 2013
 *
 */
@SuppressWarnings("serial")
public class IterationListPanel extends JPanel {
	private JTextArea list;
	private JTable table;
	private JScrollPane scrollPane;
	private RetrieveAllIterationsController retrieveController;
	private JPanel panel;
	private GridBagLayout layout;
	final JScrollPane mainPanelScrollPane;
	private IterationView iterationview;

	private ToolbarGroupView buttonGroup;
	private JButton refreshButton, deleteButton;
	private final MainTabController tabController;
	private Tab containingTab;

	/**
	 * Instantiates a new iteration list panel.
	 *
	 * @param tabController the tab controller
	 */
	public IterationListPanel(MainTabController tabController){
		super(new GridLayout());	
		this.tabController = tabController;
		panel = new JPanel();		
		retrieveController = new RetrieveAllIterationsController(iterationview);
		TableModel model = new IterationTableModel();		
		table = new JTable(model);
		table.addMouseListener(new RetrieveIterationController(this));		
		((IterationTableModel)table.getModel()).setColumnWidths(table);		
		scrollPane = new JScrollPane(table);
		refreshButton = new JButton("Refresh");
		refreshButton.setAction(new IterationsRefreshAction(retrieveController));	
		deleteButton = new JButton("Delete");

		GridBagConstraints c = new GridBagConstraints();	
		layout = new GridBagLayout();	
		panel.setLayout(layout);	

		c.anchor = GridBagConstraints.LINE_START; 
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.5;
		c.weighty = 0;
		c.gridwidth = 1;
		c.insets = new Insets(10,10,10,0); //top,left,bottom,right
		panel.add(refreshButton, c);		

		c.anchor = GridBagConstraints.LINE_START; 
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 2;
		c.weightx = 0.5;
		c.weighty = 0;
		c.gridwidth = 1;
		panel.add(scrollPane, c);

		mainPanelScrollPane = new JScrollPane(panel);
		mainPanelScrollPane.getVerticalScrollBar().setUnitIncrement(10);

		// Prevent content of scroll pane from smearing (credit: https://gist.github.com/303464)
		mainPanelScrollPane.getVerticalScrollBar().addAdjustmentListener(new java.awt.event.AdjustmentListener(){
			public void adjustmentValueChanged(java.awt.event.AdjustmentEvent ae){
				mainPanelScrollPane.repaint();
			}
		});

		this.add(mainPanelScrollPane);


		final JPanel p = this;
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

					Refresher.getInstance().refreshIterationsFromServer(iterationview);
				}
			}
		});
	}

	/**
	 * Sets the tab.
	 *
	 * @param tab tab to set
	 */
	public void setTab(Tab tab)
	{
		containingTab = tab;
		if(containingTab == null) {
			containingTab = new DummyTab();
		}
		containingTab.setIcon(new ImageIcon());
		containingTab.setTitle("Iteration List");
		containingTab.setToolTipText("View all Iterations");
	}

	/**
	 * Add iteration.
	 *
	 * @param it the iteration to add
	 */
	private void addIteration(Iteration it){
		((IterationTableModel)table.getModel()).addRow(it);
	}

	/**
	 * Clears the list.
	 */
	public void clearList() {
		((IterationTableModel)table.getModel()).clear();
	}

	/**
	 * Adds iterations.
	 *
	 * @param iterations iterations to add
	 */
	public void addIterations(Iteration[] iterations) {
		clearList();		
		for (int i = iterations.length -1; i > -1; i --){
			if (iterations[i].getStatus() != IterationStatus.CLOSED){
				addIteration(iterations[i]);
			}
		}
		table.updateUI();
	}

	/**
	 * Demand a refresh command. This may be depreciated... Please hold.
	 */
	public void refreshList(){
		retrieveController.refreshData();
	}

	/**
	 * Gets table.
	 *
	 * @return the panel's JTable of Iterations
	 */
	public JTable getTable() {
		return table;
	}

	/**
	 * Gets containing tab.
	 *
	 * @return the tab that this panel is being displayed in
	 */
	public Tab getContainingTab() {
		return containingTab;
	}

	/**
	 * Gets main tab controller.
	 *
	 * @return the mainTabController
	 */
	public MainTabController getTabController() {
		return tabController;
	}
}