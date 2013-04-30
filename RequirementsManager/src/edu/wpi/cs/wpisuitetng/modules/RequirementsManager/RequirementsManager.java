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
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

import edu.wpi.cs.wpisuitetng.janeway.gui.widgets.KeyboardShortcut;
import edu.wpi.cs.wpisuitetng.janeway.modules.IJanewayModule;
import edu.wpi.cs.wpisuitetng.janeway.modules.JanewayTabModel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Toolbar.ToolbarController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Toolbar.ToolbarPanel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Toolbar.action.OpenHelpAction;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.MainTabView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.controller.MainTabController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tree.TreeView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.view.MainView;

/**
 * Manages the Requirements.
 * Adapted from JanewayModule in the project DefectTracker.
 *
 * @author Tyler Stone
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
	 * Constructor for RequirementsManager.
	 * 
	 */
	public RequirementsManager() {
		tabs = new ArrayList<JanewayTabModel>();

		MainTabView mainTabView = new MainTabView();
		tabController = new MainTabController(mainTabView);
		new TreeView();

		mainTabView.createTableModel(tabController);
		mainTabView.createIterationTableModel(tabController);

		//new main panel
		MainView mainView = new MainView(mainTabView);

		mainView.makeRefresher();

		//new toolbar panel
		toolbarPanel = new ToolbarPanel(tabController);
		new ToolbarController(toolbarPanel, tabController);

		//create a tab model that contains toolbar and content panel
		JanewayTabModel tab1 = new JanewayTabModel(getName(), new ImageIcon(), toolbarPanel, mainView);

		//add to the list of tabs
		tabs.add(tab1);
		
		// add keyboard shortcuts to Requirements Manager
		registerKeyboardShortcuts(tab1);
	}

	/** 
	 * Returns the name of RequirementsManager.
	 * 
	 * @return The name of RequirementsManager
	 * @see edu.wpi.cs.wpisuitetng.janeway.modules.IJanewayModule#getName()
	 */
	@Override
	public String getName() {
		return "Requirements Manager";
	}

	/** 
	 * Returns the list of tabs displayed on RequirementsManager.
	 * 
	 * @return The list of tabs displayed on RequirementsManager
	 * @see edu.wpi.cs.wpisuitetng.janeway.modules.IJanewayModule#getTabs()
	 */
	@Override
	public List<JanewayTabModel> getTabs() {
		return tabs;
	}
	
	@SuppressWarnings("serial")
	private void registerKeyboardShortcuts(JanewayTabModel tab) {
		String osName = System.getProperty("os.name").toLowerCase();
		
		// control + tab: switch to right tab
		tab.addKeyboardShortcut(new KeyboardShortcut(KeyStroke.getKeyStroke("control TAB"), new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tabController.switchToRightTab();
			}
		}));
		
		// control + shift + tab: switch to left tab
		tab.addKeyboardShortcut(new KeyboardShortcut(KeyStroke.getKeyStroke("control shift TAB"), new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tabController.switchToLeftTab();
			}
		}));
		
		// command + w for mac or control + w for windows: close the current tab
		if (osName.contains("mac")) {
			tab.addKeyboardShortcut(new KeyboardShortcut(KeyStroke.getKeyStroke("meta W"), new AbstractAction() {
				@Override
				public void actionPerformed(ActionEvent e) {
					tabController.closeCurrentTab();
				}
			}));
		}
		else {
			tab.addKeyboardShortcut(new KeyboardShortcut(KeyStroke.getKeyStroke("control W"), new AbstractAction() {
				@Override
				public void actionPerformed(ActionEvent e) {
					tabController.closeCurrentTab();
				}
			}));
		}
		
			
		// command + r for mac or control + r for windows: List Requirements
		if (osName.contains("mac")) {
			tab.addKeyboardShortcut(new KeyboardShortcut(KeyStroke.getKeyStroke("meta R"), new AbstractAction() {
				@Override
				public void actionPerformed(ActionEvent e) {
					tabController.addListRequirementTab();
				}
			}));
		}
		else {
			tab.addKeyboardShortcut(new KeyboardShortcut(KeyStroke.getKeyStroke("control R"), new AbstractAction() {
				@Override
				public void actionPerformed(ActionEvent e) {
					tabController.addListRequirementTab();
				}
			}));
		}
		
		// command + w for mac or control + w for windows: List Iterations
		if (osName.contains("mac")) {
			tab.addKeyboardShortcut(new KeyboardShortcut(KeyStroke.getKeyStroke("meta I"), new AbstractAction() {
				@Override
				public void actionPerformed(ActionEvent e) {
					tabController.addListIterationTab();
				}
			}));
		}
		else {
			tab.addKeyboardShortcut(new KeyboardShortcut(KeyStroke.getKeyStroke("control I"), new AbstractAction() {
				@Override
				public void actionPerformed(ActionEvent e) {
					tabController.addListIterationTab();
				}
			}));
		}
		
		// command + w for mac or control + w for windows: Statistics
		if (osName.contains("mac")) {
			tab.addKeyboardShortcut(new KeyboardShortcut(KeyStroke.getKeyStroke("meta S"), new AbstractAction() {
				@Override
				public void actionPerformed(ActionEvent e) {
					tabController.addBarChartTab();
				}
			}));
		}
		else {
			tab.addKeyboardShortcut(new KeyboardShortcut(KeyStroke.getKeyStroke("control S"), new AbstractAction() {
				@Override
				public void actionPerformed(ActionEvent e) {
					tabController.addBarChartTab();
				}
			}));
		}
		
		// command + w for mac or control + w for windows: Statistics
		if (osName.contains("mac")) {
			tab.addKeyboardShortcut(new KeyboardShortcut(KeyStroke.getKeyStroke("meta H"), new AbstractAction() {
				@Override
				public void actionPerformed(ActionEvent e) {
					OpenHelpAction oHA = new OpenHelpAction();
					oHA.actionPerformed(null);
				}
			}));
		}
		else {
			tab.addKeyboardShortcut(new KeyboardShortcut(KeyStroke.getKeyStroke("control H"), new AbstractAction() {
				@Override
				public void actionPerformed(ActionEvent e) {
					OpenHelpAction oHA = new OpenHelpAction();
					oHA.actionPerformed(null);
				}
			}));
		}
	}
}