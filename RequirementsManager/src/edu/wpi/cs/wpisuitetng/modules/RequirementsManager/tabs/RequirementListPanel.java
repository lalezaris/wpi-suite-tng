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
 *  Tyler Stone
 *  
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;


import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.RequirementStatus;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.Refresher;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RefresherMode;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementPanel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RetrieveRequirementController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RetrieveAllRequirementsController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementPanel.Mode;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.configuration.NetworkConfiguration;

/**
 * The innermost JPanel for the list of all requirements tab, which displays the requirement's information
 *
 * @author Tianyu Li
 * @modified by Chris H on Mar 24
 * @version Mar 21, 2013
 *
 */
public class RequirementListPanel extends JPanel{

	
	
	private JTextArea list;
	private JTable table;
	private JScrollPane scrollPane;
	private RetrieveAllRequirementsController retrieveController;
	private JPanel panel;
	private GridBagLayout layout;
	final JScrollPane mainPanelScrollPane;
	
	private ToolbarGroupView buttonGroup;
	private JButton refreshButton, deleteButton;
	private final MainTabController tabController;
	private Tab containingTab;
	
	public RequirementListPanel(MainTabController tabController){
		super(new GridLayout());
		
		this.tabController = tabController;
		
	
		panel = new JPanel();
		
		retrieveController = new RetrieveAllRequirementsController(RefresherMode.TABLE);
		
		
		

		TableModel model = new RequirementTableModel();
		
		table = new JTable(model);
		table.addMouseListener(new RetrieveRequirementController(this));
		
		((RequirementTableModel)table.getModel()).setColumnWidths(table);
		
		
		scrollPane = new JScrollPane(table);
		
		//table.setFillsViewportHeight(true);
		
		
		//retrieveController.refreshData();
		refreshButton = new JButton("Refresh");
		refreshButton.setAction(new RefreshAction(retrieveController));
		
		deleteButton = new JButton("Delete");
		
//		buttonGroup.getContent().add(refreshButton);
//		buttonGroup.setPreferredWidth(150);
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
//		c.insets = new Insets(10,10,10,0); //top,left,bottom,right
		panel.add(scrollPane, c);
		
		
		
		//this.setLayout(new BorderLayout());
		mainPanelScrollPane = new JScrollPane(panel);
		mainPanelScrollPane.getVerticalScrollBar().setUnitIncrement(10);
		
		// Prevent content of scroll pane from smearing (credit: https://gist.github.com/303464)
		mainPanelScrollPane.getVerticalScrollBar().addAdjustmentListener(new java.awt.event.AdjustmentListener(){
			public void adjustmentValueChanged(java.awt.event.AdjustmentEvent ae){
				//SwingUtilities.invokeLater(new Runnable(){
				//	public void run(){
						mainPanelScrollPane.repaint();
				//	}
				//});
			}
		});
		
		this.add(mainPanelScrollPane);
		
		
		//Did this panel come into view?
		final JPanel p = this;
		p.addHierarchyListener(new HierarchyListener() {

			@Override
			public void hierarchyChanged(HierarchyEvent e) {
				// TODO Auto-generated method stub
				if ( (HierarchyEvent.SHOWING_CHANGED & e.getChangeFlags()) != 0
						&& p.isShowing())
				//if (HierarchyEvent.SHOWING_CHANGED != 0 && p.isShowing())
				{
					
					Refresher.getInstance().refreshRequirementsFromServer(RefresherMode.TABLE);
				}
					
			}
			
		});
		
		
		
	}
	
	public void setTab(Tab tab)
	{
		containingTab = tab;
		if(containingTab == null) {
			containingTab = new DummyTab();
		}
		containingTab.setIcon(new ImageIcon());
		containingTab.setTitle("Requirement List");
		containingTab.setToolTipText("View all Requirements");
	}
	
	private void addRequirement(Requirement req){
		((RequirementTableModel)table.getModel()).addRow(req);
		
	}


	public void clearList() {
		((RequirementTableModel)table.getModel()).clear();
	}


	public void addRequirements(Requirement[] requirements) {
		clearList();

		//addRequirement(requirements[0]);
		
		for (int i = requirements.length -1; i > -1; i --){
			if (requirements[i].getStatus() != RequirementStatus.DELETED){
				addRequirement(requirements[i]);
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
	 * @return the panel's JTable of Requirements
	 */
	public JTable getTable() {
		return table;
	}
	
	/**
	 * @return the tab that this panel is being displayed in
	 */
	public Tab getContainingTab() {
		return containingTab;
	}
	
	/**
	 * @return the mainTabController
	 */
	public MainTabController getTabController() {
		return tabController;
	}
}
