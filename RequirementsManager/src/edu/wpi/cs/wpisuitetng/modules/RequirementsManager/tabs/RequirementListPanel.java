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
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementPanel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RetrieveAllRequirementsController;
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
	
	private int selectedRequirement;
	
	private ToolbarGroupView buttonGroup;
	private JButton refreshButton;
	
	
	public RequirementListPanel(){
		
		super(new GridLayout());
		
		// Instantiate the button panel
//		buttonGroup = new ToolbarGroupView("Requirements List");
				
		panel = new JPanel();
		
		//TODO: if we have more time, we should have some sort of local storage unit for all requirements
		//Network.getInstance().setDefaultNetworkConfiguration(new NetworkConfiguration("http://localhost:8080/WPISuite/"));
		retrieveController = new RetrieveAllRequirementsController(this);
		
		
		
		TableModel model = new RequirementTable();
		
		table = new JTable(model);
		table.addMouseListener(new RequirementTableEventListener(this));
		
		table.getTableHeader().setReorderingAllowed(false);
		for (int i = 0 ; i < 7 ; i ++){
			TableColumn column = table.getColumnModel().getColumn(i);
		    if (i == 0) {
		    	column.setPreferredWidth(30); // ID
		    } else if (i == 1) {
		        column.setPreferredWidth(100); //NAME COLUMN
		    } else if (i == 2) {
		    	column.setPreferredWidth(550); //DESC COLUMN
		    } else if (i == 3) {
		    	column.setPreferredWidth(90); //DESC STATUS
		    } else if (i == 4) {
		    	column.setPreferredWidth(90); //DESC PRIORITY
		    } else if (i == 5) {
		    	column.setPreferredWidth(30); //DESC ESTIMATE
		    } else if (i == 6) {
		    	column.setPreferredWidth(100); //ASSIGNEE
		    }
		}
		
		
		
		scrollPane = new JScrollPane(table);
		
		//table.setFillsViewportHeight(true);
		
		
		//retrieveController.refreshData();
		refreshButton = new JButton("Refresh");
		refreshButton.setAction(new RefreshAction(retrieveController));
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
		c.gridy = 1;
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
		
//		final JPanel p = this;
//		p.addHierarchyListener(new HierarchyListener() {
//
//			@Override
//			public void hierarchyChanged(HierarchyEvent e) {
//				// TODO Auto-generated method stub
//				if ( (HierarchyEvent.SHOWING_CHANGED & e.getChangeFlags()) != 0
//						&& p.isShowing())
//				{
//					System.out.println("Dashboard Gained View");
//					retrieveController.refreshData();
//				}
//					
//			}
//			
//		});
		//this.add(panel);
		
	}
	

	
	
	public void addRequirement(Requirement req){
		((RequirementTable)table.getModel()).addRow(req);
		table.updateUI();
	}


	public void clearList() {
		((RequirementTable)table.getModel()).clear();
	}


	public void addRequirements(Requirement[] requirements) {
		clearList();
		
		//for(Requirement r : requirements){
		//	addRequirement(r);
		//}
		
		for (int i = requirements.length -1; i > -1 ; i --){
			addRequirement(requirements[i]);
		}
	}

	public void refreshList(){
		retrieveController.refreshData();
	}
	
	public JTable getTable() {
		return table;
	}
	
	public void setSelectedRequirement(int selectedRequirement) {
		this.selectedRequirement = selectedRequirement;
	}
	
	public int getSelectedRequirement() {
		return selectedRequirement;
	}
}
