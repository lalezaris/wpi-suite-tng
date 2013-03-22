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
 *  Chris Dunkers
 *  Sam Lalezari
 *  Joe Spicola
 *  Tyler Stone
 *  Arica Liu
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager;

import java.awt.event.ActionEvent;
import java.util.List;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.KeyStroke;


import edu.wpi.cs.wpisuitetng.janeway.gui.widgets.KeyboardShortcut;
import edu.wpi.cs.wpisuitetng.janeway.modules.IJanewayModule;
import edu.wpi.cs.wpisuitetng.janeway.modules.JanewayTabModel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Toolbar.ToolbarController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Toolbar.ToolbarPanel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.MainTabController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.view.MainView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Toolbar.ToolbarPanel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.MainTabView;

/**
 * Manages the Requirements
 * Adapted from JanewayModule
 *
 * @author Chris Dunkers, Sam Lalezari, Joe Spicola, Tyler Stone, Arica Liu
 *
 * @version Mar 20, 2013
 *
 */
public class RequirementsManager implements IJanewayModule {
	//initialize tabs
	private List<JanewayTabModel> tabs;
	public final MainTabController tabController;
	public ToolbarPanel toolbarPanel;
	
	/**
	 * Class Constructor 
	 * 
	 */
	public RequirementsManager() {
		tabs = new ArrayList<JanewayTabModel>();
		
		MainTabView mainTabView = new MainTabView();
		tabController = new MainTabController(mainTabView);
		
		//new main panel
		MainView mainView = new MainView(mainTabView);
		
		//new toolbar panel
		toolbarPanel = new ToolbarPanel(tabController);
		ToolbarController toolbarController = new ToolbarController(toolbarPanel, tabController);
	
		//create a tab model that contains toolbar and content panel
		JanewayTabModel tab1 = new JanewayTabModel(getName(), new ImageIcon(), toolbarPanel, mainView);
		
		//add to the list of tabs
		tabs.add(tab1);
	}
	
	@Override
	public String getName() {
		return "Requirements Manager";
	}

	@Override
	public List<JanewayTabModel> getTabs() {
		return tabs;
	}
}
