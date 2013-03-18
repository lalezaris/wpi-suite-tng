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
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BorderFactory;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.MainTabController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.MainTabView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tree.TreeView;

/**
 * Main View Panel class for our Requirements Manager
 * 
 * @author Tyler Stone, Sam Lalezari
 *
 */
@SuppressWarnings("serial")
public class MainView extends JPanel {
	public final MainTabController mainTabController;
	
	public MainView() {
		MainTabView mainTabView = new MainTabView();
		mainTabController = new MainTabController(mainTabView);
		
		// Simple outline, can be changed later
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createLineBorder(Color.black, 2));
		
		// Set up the split panels
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BorderLayout());
		leftPanel.setPreferredSize(new Dimension(200, this.getSize().height));
		this.add(leftPanel, BorderLayout.LINE_START);
		
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BorderLayout());
		this.add(rightPanel, BorderLayout.CENTER);
		rightPanel.add(mainTabView);
		
		// Add requirements tree to the left panel
		TreeView treeView = new TreeView();
		leftPanel.add(treeView);
	}
}
