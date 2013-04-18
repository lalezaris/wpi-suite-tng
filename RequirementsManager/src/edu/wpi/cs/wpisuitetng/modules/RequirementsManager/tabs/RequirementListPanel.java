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

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.filter.FilterController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RequirementStatus;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.action.Refresher;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.action.RefresherMode;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.RetrieveAllRequirementsController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.RetrieveRequirementController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.action.RefreshAction;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.action.RequirementTableSortAction;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.action.UpdateAllEstimateAction;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.controller.MainTabController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.controller.RequirementTableSortController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.controller.UpdateAllRequirementsController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.model.DummyTab;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.model.RequirementTableModel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.model.Tab;

/**
 * The innermost JPanel for the list of all requirements tab, which displays the requirement's information.
 *
 * @author Tianyu Li
 * @modified by Chris H on Mar 24
 * @modified by Tianyu Li on Apr 9
 * @version Mar 21, 2013
 */
@SuppressWarnings({"unused", "serial"})
public class RequirementListPanel extends JPanel{

	private JTextArea list;
	private JTable table;
	private JScrollPane scrollPane;
	private RetrieveAllRequirementsController retrieveController;
	private UpdateAllRequirementsController updateController;
	private JPanel panel, buttonPanel;
	private GridBagLayout layout;
	private RequirementTableModel model;
	final JScrollPane mainPanelScrollPane;

	private ToolbarGroupView buttonGroup;
	private JButton refreshButton, updateButton, deleteButton;
	private final MainTabController tabController;
	private Tab containingTab;

	private FilterController filterController;
	//eventually, refactor this out into a model
	private Requirement[] content, filteredContent;
	
	
	/**
	 * Instantiates a new requirement list panel.
	 *
	 * @param tabController the tab controller
	 */
	public RequirementListPanel(MainTabController tabController){
		super(new GridLayout());	
		this.tabController = tabController;
		panel = new JPanel();		
		retrieveController = new RetrieveAllRequirementsController(RefresherMode.TABLE);
		model = new RequirementTableModel();		
		table = new JTable(model);
		table.addMouseListener(new RetrieveRequirementController(this));	
		table.getTableHeader().addMouseListener(new RequirementTableSortAction(new RequirementTableSortController(table)));
		((RequirementTableModel)table.getModel()).setColumnWidths(table);		
		scrollPane = new JScrollPane(table);
		refreshButton = new JButton("Refresh");
		refreshButton.setAction(new RefreshAction(retrieveController));	
		updateButton = new JButton("Update");
		updateController = new UpdateAllRequirementsController(this);
		updateButton.setAction(new UpdateAllEstimateAction(updateController));	
		deleteButton = new JButton("Delete");
	
		
		
		GridBagConstraints c = new GridBagConstraints();	
		layout = new GridBagLayout();	
		panel.setLayout(layout);	

		filterController = new FilterController(this);
		c.anchor = GridBagConstraints.FIRST_LINE_START; 
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.5;
		c.weighty = 1;
		c.gridwidth = 1;
		c.insets = new Insets(10,10,10,0); //top,left,bottom,right
		panel.add(filterController.getPanel(), c);
		panel.validate();
		
		buttonPanel = new JPanel();
		buttonPanel.add(refreshButton);
		buttonPanel.add(updateButton);
		
		c.anchor = GridBagConstraints.LINE_START; 
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 1;
		c.weighty = 1;
		c.gridwidth = 1;
		c.insets = new Insets(10,10,10,0); //top,left,bottom,right
		panel.add(buttonPanel,c);
		//panel.add(refreshButton, c);

		c.gridx = 1;
		c.gridy = 1;
		c.weightx = 1;
		c.weighty = 1;
		c.insets = new Insets(10,10,10,0); //top,left,bottom,right
		//panel.add(updateButton, c);

		c.anchor = GridBagConstraints.LINE_START; 
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 3;
		c.weightx = 0.5;
		c.weighty = 1;
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

					Refresher.getInstance().refreshRequirementsFromServer(RefresherMode.TABLE);
				}

			}

		});

		table.setDefaultEditor(Integer.class, new RequirementListEstimateEditor(0, 100));
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
		containingTab.setTitle("Requirement List");
		containingTab.setToolTipText("View all Requirements");
	}

	/**
	 * Add requirement.
	 *
	 * @param req the requirement to add
	 */
	private void addRequirement(Requirement req){
		((RequirementTableModel)table.getModel()).addRow(req);

	}


	/**
	 * Clears the list.
	 */
	public void clearList() {
		((RequirementTableModel)table.getModel()).clear();
	}


	
	
	/**
	 * Adds requirements. (If they are not deleted)
	 *
	 * @param requirements requirements to add
	 */
	public void addRequirements(Requirement[] requirements) {
		this.content = requirements;
		clearList();	
		((RequirementTableModel) table.getModel()).clearRequirements();
		for (int i = requirements.length -1; i > -1; i --){
			//if (requirements[i].getStatus() != RequirementStatus.DELETED){ //MOVED THIS CHECK INTO REQTABLEMODEL
				addRequirement(requirements[i]);
			//}
		}
		table.updateUI();
		filterController.setFilteredInTable();
	}
	
	public void filterRequirements(Requirement[] requirements){
		this.filteredContent = requirements;
		clearList();
		RequirementTableModel model = ((RequirementTableModel) table.getModel());
		model.clearRequirements();
		for (int i = 0 ; i < requirements.length; i++){
			System.out.println(requirements[i]);
			model.addRow(requirements[i]);
		}
		//model.setVisibleRequirements(requirements);
		table.updateUI();
	}

	/**
	 * Demand a refresh command. This may be depreciated... Please hold.
	 */
	public void refreshList() {
		retrieveController.refreshData();
	}

	/**
	 * Gets table.
	 *
	 * @return the panel's JTable of Requirements
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
	

	/**
	 * Gets the content
	 * @return the content
	 */
	public Requirement[] getContent() {
		return content;
	}

	/**
	 * Gets the filteredContent
	 * @return the filteredContent
	 */
	public Requirement[] getFilteredContent() {
		return filteredContent;
	}


	/**
	 * Gets the panel
	 * @return the panel
	 */
	public JPanel getPanel() {
		return panel;
	}

	/**
	 * Gets the model.
	 *
	 * @return the model
	 */
	public RequirementTableModel getModel() {
		return model;
	}
}