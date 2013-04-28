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
 **************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

import javax.swing.table.DefaultTableCellRenderer;


import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.filter.FilterController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Iteration;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RequirementPriority;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RequirementStatus;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.action.Refresher;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.action.RefresherMode;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.RetrieveAllRequirementsController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.RetrieveRequirementController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.action.RefreshAction;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.action.RequirementTableSortAction;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.action.UpdateAllRequirementAction;
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
 * @version Apr 14, 2013
 */
@SuppressWarnings({"unused", "serial"})
public class RequirementListPanel extends JPanel{
	private RequirementListTable table;
	private JScrollPane scrollPane;
	private RetrieveAllRequirementsController retrieveController;
	private UpdateAllRequirementsController updateController;
	private JPanel panel, buttonPanel;
	private GridBagLayout layout;
	private RequirementTableModel model;
	final JScrollPane mainPanelScrollPane;

	private JButton refreshButton, updateButton, deleteButton, cancelButton;
	private final MainTabController tabController;
	private Tab containingTab;

	private FilterController filterController;
	/*TODO eventually, refactor this out into a model*/
	private Requirement[] content, filteredContent;
	private JLabel updateLabel;


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
		model = new RequirementTableModel(this);
		table = new RequirementListTable(this);
		table.setModel(model);
		table.setBackground(Color.WHITE);
		table.addMouseListener(new RetrieveRequirementController(this));	
		table.getTableHeader().addMouseListener(new RequirementTableSortAction(new RequirementTableSortController(table)));
		((RequirementTableModel)table.getModel()).setColumnWidths(table);		
		scrollPane = new JScrollPane(table);
		refreshButton = new JButton("Refresh");
		refreshButton.setAction(new RefreshAction(retrieveController));
		
		refreshButton.addActionListener(new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e) {
				((RequirementTableModel) table.getModel()).clearChangeVisualsDisregard();
			}
		});
		
		updateButton = new JButton("Update");
		updateController = new UpdateAllRequirementsController(this);
		updateButton.setAction(new UpdateAllRequirementAction(updateController));
		deleteButton = new JButton("Delete");
		updateLabel = new JLabel();
		
		cancelButton = new JButton("Cancel");
		
		updateButton.setVisible(false);
		cancelButton.setVisible(false);
		
		cancelButton.setAction(new AbstractAction("Cancel"){
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("CANCEL");
				model.cancelChanges();
			}
			
		});
		
		
		GridBagConstraints c = new GridBagConstraints();	
		layout = new GridBagLayout();	
		panel.setLayout(layout);

		filterController = new FilterController(this);
		c.anchor = GridBagConstraints.FIRST_LINE_START; 
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0;
		c.weighty = 1;
		c.gridwidth = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(10,10,10,0); //top,left,bottom,right
		panel.add(filterController.getPanel(), c);
		//panel.validate();

		buttonPanel = new JPanel();
		buttonPanel.add(refreshButton);
		buttonPanel.add(updateButton);
		buttonPanel.add(cancelButton);
		buttonPanel.add(updateLabel);

		c.anchor = GridBagConstraints.LINE_START; 
		c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 1;
		c.weighty = 1;
		c.gridwidth = 1;
		c.insets = new Insets(10,10,10,0); //top,left,bottom,right
		panel.add(buttonPanel,c);

		c.gridx = 1;
		c.gridy = 1;
		c.weightx = 1;
		c.weighty = 1;
		c.insets = new Insets(10,10,10,0); //top,left,bottom,right

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

			/**
			 * Shows changes to hierarchy
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

		
		System.out.println("GOT TO END OF REQLISTPANEL");

		//setUpPriorityColumn();
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
	 * Update requirement.
	 *
	 * @param row the row
	 * @param req the req
	 */
	public void updateRequirement(int row, Requirement req){
		((RequirementTableModel)table.getModel()).updateRow(row, req);
		
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
			addRequirement(requirements[i]);
		}
		
		table.updateUI();
		filterController.setFilteredInTable();
	}

	
	/**
	 * Filter requirements.
	 *
	 * @param requirements the requirements
	 */
	public void filterRequirements(Requirement[] requirements){
		this.filteredContent = requirements;
		


		
		clearList();
		RequirementTableModel model = ((RequirementTableModel) table.getModel());
		model.clearRequirements();
		for (int i = 0 ; i < requirements.length; i++){
			model.addRow(requirements[i]);
		}
		table.updateUI();
	}

	/**
	 * Demand a refresh command. TODO: This may be deprecated... Please hold.
	 */
	public void refreshList() {
		retrieveController.refreshData();
	}

	/**
	 * Set the drop down menu to the priority
	 */
	private void setUpPriorityColumn() {
		JComboBox<RequirementPriority> comboBox = new JComboBox<RequirementPriority>();
		comboBox.addItem(RequirementPriority.HIGH);
		comboBox.addItem(RequirementPriority.MEDIUM);
		comboBox.addItem(RequirementPriority.LOW);
		comboBox.addItem(RequirementPriority.BLANK);

		table.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(comboBox));
	}
	
	/**
	 * This will configure the buttons so that nothing can be done while a save is in progress.
	 * 
	 */
	public void setButtonsForSaving(){
		this.updateButton.setEnabled(false);
		this.refreshButton.setEnabled(false);
		this.cancelButton.setEnabled(false);
	}
	
	/**
	 * This will configure the buttons so that Update and Cancel are options
	 * 
	 */
	public void setButtonsForChanges(){
		this.updateButton.setEnabled(true);
		this.refreshButton.setEnabled(true);
		this.cancelButton.setEnabled(true);
		this.refreshButton.setVisible(true);
		this.updateButton.setVisible(true);
		this.cancelButton.setVisible(true);
	}
	
	/**
	 * This will configure the buttons so that Update and Cancel are not options
	 * 
	 */
	public void setButtonsForNoChanges(){
		this.updateButton.setEnabled(true);
		this.cancelButton.setEnabled(true);
		this.refreshButton.setEnabled(true);
		this.refreshButton.setVisible(true);
		this.updateButton.setVisible(false);
		this.cancelButton.setVisible(false);
	}
	
	/**
	 * If the requirements have been updated, show message to user.
	 */
	public void showUpdateSuccessfully(String message) {
		updateLabel.setText(message);
	}
	
	/**
	 * Hide the message of update successfully.
	 */
	public void hideUpdateSuccessfully() {
		updateLabel.setText("Press the Update button to save changes");
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

	/**
	 * Gets filter controller
	 * 
	 * @return the filter controller
	 */
	public FilterController getFilterController() {
		return filterController;
	}

	/**
	 * Gets the updateButton
	 * @return the updateButton
	 */
	public JButton getUpdateButton() {
		return updateButton;
	}

	public Requirement[] getOriginalContent(){
		Requirement[] fresh;
		if (content != null){
			fresh = new Requirement[content.length];
			for (int j = 0 ; j < fresh.length; j ++)
			{
				fresh[j] = content[j];
				for (int i = 0 ; i < getModel().getUnsavedRequirements().size() ; i ++){
					if (fresh[j].getId() == (Integer)getModel().getUnsavedRequirements().get(i)[0]){
						System.out.println("Swap");
						fresh[j].setTitle((String)getModel().getUnsavedRequirements().get(i)[1]); //name
						fresh[j].setDescription((String)getModel().getUnsavedRequirements().get(i)[2]); //desc
						fresh[j].setStatus((RequirementStatus)getModel().getUnsavedRequirements().get(i)[3]); //status
						fresh[j].setPriority((RequirementPriority)getModel().getUnsavedRequirements().get(i)[4]); //priority
						fresh[j].setEstimateEffort((Integer)getModel().getUnsavedRequirements().get(i)[5]); // estimate
						
						fresh[j].setIteration((Iteration)getModel().getUnsavedRequirements().get(i)[6]);//iteration
					}
				}
			} 
		}
		else {
			fresh = new Requirement[0];
		}
		return fresh;
	}
	
	public Requirement[] getFreshContent() {
		Requirement[] fresh;
		if (content != null){
			fresh = new Requirement[content.length];
			for (int j = 0 ; j < fresh.length; j ++)
			{
				fresh[j] = content[j];
				for (int i = 0 ; i < getModel().getRequirements().size() ; i ++){
					if (fresh[j].getId() == getModel().getRequirements().get(i).getId()){
						fresh[j] = getModel().getRequirements().get(i);
					}
				}
			} 
		}
		else {
			fresh = new Requirement[0];
		}
		return fresh;
	}

}