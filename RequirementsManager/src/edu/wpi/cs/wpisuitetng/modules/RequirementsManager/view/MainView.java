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
 * 	Tyler Stone
 *  Sam Lalezari
**************************************************/

package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.action.Refresher;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.MainTabView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tree.TreeView;

/**
 * Main View Panel class for our Requirements Manager.
 * 
 * @author Tyler Stone
 * @author Sam Lalezari
 *
 */
@SuppressWarnings("serial")
public class MainView extends JPanel {
	
	TreeView treeView;
	Refresher refresher;
	MainTabView view;
	/**
	 * Constructs the Main View Panel.
	 * 
	 * @param mainTabView
	 */
	public MainView(MainTabView mainTabView) {
		JSplitPane splitPane;
		this.view = mainTabView;
		this.setLayout(new BorderLayout());
		
		// Set up the split panels
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BorderLayout());
		leftPanel.setPreferredSize(new Dimension(200, this.getSize().height));
		
		// Add requirements tree to the left panel

		treeView = new TreeView();
		leftPanel.add(treeView);
		
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BorderLayout());
		rightPanel.add(mainTabView);		
		
		//new split pane to adjust size
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
				//add split pane to main view
		this.add(splitPane, BorderLayout.CENTER);
	}
	
	public void makeRefresher(){
		System.out.println("MainView make refresher");
		refresher = new Refresher(treeView.getTreeModel(), view.getTableModel(), view.getIterationTableModel());
		refresher.refreshIterationsFromServer(null);
	}
}