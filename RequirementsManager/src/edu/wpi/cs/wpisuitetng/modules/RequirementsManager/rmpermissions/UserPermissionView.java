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
 *  CDUNKERS
 **************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.controller.AdminPermissionController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.controller.NonePermissionController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.controller.SetUpPermissionsPanelController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.controller.UpdateAllPermissionsController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.controller.UpdatePermissionController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.model.PermissionModel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.model.Tab;

/**
 * View for user permissions.
 *
 * @author CDUNKERS
 * @version Apr 1, 2013
 */
public class UserPermissionView extends JPanel {

	private UserPermissionPanel mainPanel;
//	private RetrieveAllRequirementsController controller;
	final JScrollPane mainPanelScrollPane;
	private Tab containingTab;
//	private boolean inputEnabled;

	private PermissionModel permModel;

	/**
	 * Instantiates a new user permission view.
	 *
	 * @param tab the tab
	 */
	public UserPermissionView(Tab tab){
		containingTab = tab;
		containingTab.setTitle("Edit User Permissions");

//		inputEnabled = true;

		//make the model
		permModel = new PermissionModel();

		// Instantiate the main create requirement panel
		mainPanel = new UserPermissionPanel(this);

		mainPanel.getBtnAdmin().addActionListener(new AdminPermissionController(mainPanel));
		mainPanel.getBtnNone().addActionListener(new NonePermissionController(mainPanel));
		mainPanel.getBtnUpdate().addActionListener(new UpdatePermissionController(mainPanel));
		mainPanel.getBtnUpdateAll().addActionListener(new UpdateAllPermissionsController(mainPanel, permModel));

		
		
		SetUpPermissionsPanelController setUp = new SetUpPermissionsPanelController(mainPanel, permModel);
		setUp.setUp();

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
	}

	/**
	 * The function takes in a List and takes all of the elements
	 * from the list and adds them to the default list model.
	 *
	 * @param newElements a list of the elements to be put into the model
	 * @return the model with the given elements
	 */
	public DefaultListModel getNewModel(List<String> newElements){
		DefaultListModel newModel = new DefaultListModel(); 
		for(int i = 0; i < newElements.size(); i++){
			newModel.addElement((Object)newElements.get(i));
		}
		return newModel;
	}

	/**
	 * Gets tab.
	 *
	 * @return the containingTab
	 */
	public Tab getTab() {
		return containingTab;
	}

	/**
	 * Gets the PermissionModel.
	 *
	 * @return the PermissionModel
	 */
	public PermissionModel getPermModel() {
		return permModel;
	}

	/**
	 * Gets the user permission panel.
	 *
	 * @return the permModel
	 */
	public JPanel getUserPermissionPanel() {
		return mainPanel;
	}
}