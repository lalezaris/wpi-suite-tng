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
 *  Tyler Stone
 *  Arica Liu
 *  Tushar Narayan
 *  Evan Polekoff
 *  Chris Hanna
 *  Lauren Kahn
 **************************************************/

package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.controller;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

import javax.swing.Icon;
import javax.swing.event.ChangeListener;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Iteration.IterationPanel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Iteration.IterationView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.charts.BarPieChartView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Iteration;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementPanel.Mode;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.UserPermissionView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.snake.GamePanel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.IterationListPanel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.MainTabView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.RequirementListPanel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.model.Tab;

/**
 * Controls the behavior of a given MainTabView.
 * Provides convenient public methods for controlling the MainTabView.
 * Keep in mind that this controller is visible as a public field in the module.
 * Adapted from MainTabController in the project Defect Tracker
 * 
 * @author Tyler Stone 
 * @author Arica Liu
 *
 * @version Mar 17, 2013
 *
 */
public class MainTabController {

	MainTabView view;
	HashMap<Integer, RequirementView> reqViewHashMap;

	private static MainTabController staticView;

	/**
	 * Controls the MainTab.
	 * 
	 * @param view Create a controller that controls this MainTabView
	 */
	public MainTabController(MainTabView view) {
		this.reqViewHashMap = new HashMap<Integer, RequirementView>();
		this.view = view;
		staticView = this;
		this.view.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				MainTabController.this.onMouseClick(event);
			}
		});
	}

	/**
	 * Gets MainTabController
	 * 
	 * @return staticView 
	 */
	public static MainTabController getController(){
		return staticView;
	}

	/**
	 * Adds a tab.
	 * 
	 * @param title			The tab's title.
	 * @param icon			The icon for the tab.
	 * @param component		The component that will be displayed inside the tab.
	 * @param tip			The tooltip to display when the cursor hovers over the tab title.
	 * @return				The created Tab
	 */
	public Tab addTab(String title, Icon icon, Component component, String tip) {
		view.addTab(title, icon, component, tip);
		int index = view.getTabCount() - 1;
		view.setSelectedIndex(index);
		return new Tab(view, view.getTabComponentAt(index));
	}

	/**
	 * Adds a tab.
	 * 
	 * @return Same as addTab(null, null, null, null)
	 */
	public Tab addTab() {
		return addTab(null, null, null, null);
	}

	/**
	 * Adds a tab that displays the given requirement in the given mode.
	 * 
	 * @param requirement The requirement to display
	 * @param mode The Mode to use
	 */
	public Tab addRequirementTab(Requirement requirement, Mode mode) {
		/*
		 * Since Requirement tabs are displayed on Janeway as "Requirement #1",
		 * get the id of the Requirement, and check if a tab with that title
		 * already exists.
		 * indexOfTab returns -1 if no tab with that title exists, or required tab index.
		 * Switch focus to that tab, or go ahead and create a new one.
		 */
		String requirementTitle = requirement.getTitle();
		int requirementId = requirement.getId();
		int checkTabIndex = view.indexOfTab("#" + requirementId + ": " + requirementTitle.substring(0, Math.min(10, requirementTitle.length())));
		if(checkTabIndex != -1){
			view.setSelectedIndex(checkTabIndex);
			return null;
		}
		else{
			Tab tab = addTab();
			RequirementView Rview = new RequirementView(requirement, mode, tab);
			if(reqViewHashMap.containsKey(requirementId)){
				reqViewHashMap.remove(requirementId);
			}
			reqViewHashMap.put(requirementId, Rview);
			tab.setComponent(Rview);
			Rview.requestFocus();
			view.setSelectedIndex(Rview.getTab().getThisIndex());
			return tab;
		}
	}

	/**
	 * Adds requirement tab.
	 * 
	 * @param requirement requirement to add
	 * @param parentView view to add requirement tab to
	 * @param mode mode of requirement
	 * @return tab
	 */
	public Tab addRequirementTab(Requirement requirement, RequirementView parentView, Mode mode) {
		return addRequirementTab(requirement, parentView, Mode.CHILD);
	}

	
	/**
	 * Make the Snake Tab!!!
	 * 
	 * @return
	 */
	public Tab addSnakeTab(){
		int checkTabIndex = view.indexOfTab("Snake");
		if(checkTabIndex != -1){
			view.setSelectedIndex(checkTabIndex);
			System.out.println("found tab already");
			return null;
		}
		else{
			//already brings focus to list tab if it was opened previously

			Tab tab = addTab();
			GamePanel panel = new GamePanel();
			panel.setTab(tab);
			tab.setComponent(panel);
			panel.requestFocus();
			return tab;
		}
	}
	
	
	/**
	 * Adds a tab that displays the list of all requirements.
	 * 
	 * @return the tab that has a the table of requirements on it
	 */
	public Tab addListRequirementTab() {
		int checkTabIndex = view.indexOfTab("Requirement List");
		if(checkTabIndex != -1){
			view.setSelectedIndex(checkTabIndex);
			return null;
		}
		else{
			//already brings focus to list tab if it was opened previously

			Tab tab = addTab();
			RequirementListPanel panel = view.getTableModel();
			panel.setTab(tab);
			tab.setComponent(panel);
			panel.requestFocus();
			return tab;
		}
	}

	/**
	 * Adds child requirement tab.
	 * 
	 * @param requirement requirement to add
	 * @param parentView view to add requirement to
	 * @return tab 
	 */
	public Tab addChildRequirementTab(Requirement requirement, RequirementView parentView) {
		Tab newTab = addRequirementTab(requirement, Mode.CHILD);
		((RequirementView) newTab.getComponent()).setParentView(parentView);

		return newTab;
	}

	/**
	 * Adds a tab that displays the given requirement.
	 * 
	 * @param requirement the requirement to display
	 * @return The created Tab 
	 */
	public Tab addEditRequirementTab(final Requirement requirement) {
		if(requirement.getParentRequirementId() != -1 && reqViewHashMap.containsKey(requirement.getParentRequirementId())){
			Tab newTab = addRequirementTab(requirement, Mode.EDIT);
			((RequirementView) newTab.getComponent()).setParentView(reqViewHashMap.get(requirement.getParentRequirementId()));
			return newTab;
		}
		else{
			return addRequirementTab(requirement, Mode.EDIT);
		}
	}

	/**
	 * Adds a tab that allows the user to create a new Requirement.
	 * 
	 * @return The created Tab
	 */
	public Tab addCreateRequirementTab() {
		return addRequirementTab(new Requirement(), Mode.CREATE);
	}

	/**
	 * Adds a tab that shows the bar chart.
	 * 
	 */
	public Tab addBarChartTab() {
		int checkTabIndex = view.indexOfTab("Statistics");
		if(checkTabIndex != -1){
			view.setSelectedIndex(checkTabIndex);

			return null;
		}
		else{
			Tab tab = addTab();
			BarPieChartView view = new BarPieChartView(tab);
			tab.setComponent(view);
			view.requestFocus();

			return tab;
		}
	}

	/**
	 * Add a change listener to the view this is controlling.
	 * 
	 * @param listener the ChangeListener that should receive ChangeEvents
	 */
	public void addChangeListener(ChangeListener listener) {
		view.addChangeListener(listener);
	}

	/**
	 * Changes the selected tab to the tab left of the current tab.
	 * 
	 */
	public void switchToLeftTab() {
		if (view.getSelectedIndex() > 0) {
			switchToTab(view.getSelectedIndex() - 1);
		}
	}

	/**
	 * Changes the selected tab to the tab right of the current tab.
	 */
	public void switchToRightTab() {
		switchToTab(view.getSelectedIndex() + 1);
	}

	/**
	 * Closes the currently active tab.
	 */
	public void closeCurrentTab() {
		try {
			view.removeTabAt(view.getSelectedIndex());
		}
		catch (IndexOutOfBoundsException e) {
			// do nothing, tried to close tab that does not exist
		}
	}

	/**
	 * Changes the selected tab to the tab with the given index.
	 * 
	 * @param tabIndex the index of the tab to select
	 */
	private void switchToTab(int tabIndex) {
		try {
			view.setSelectedIndex(tabIndex);
		}
		catch (IndexOutOfBoundsException e) {
			// an invalid tab was requested, do nothing
		}
	}

	/**
	 * Close tabs upon middle clicks.
	 * 
	 * @param event MouseEvent that happened on this.view
	 */
	private void onMouseClick(MouseEvent event) {
		// only want middle mouse button
		if(event.getButton() == MouseEvent.BUTTON2) {
			final int clickedIndex = view.indexAtLocation(event.getX(), event.getY());
			if(clickedIndex > -1) {
				view.removeTabAt(clickedIndex);
			}
		}
	}

	/**
	 * Adds new iteration tab.
	 * 
	 * @return Tab returns the new iteration tab
	 */
	public Tab addNewIterationTab() {
		Tab tab = addTab();
		IterationView view = new IterationView(new Iteration("", null, null), IterationPanel.Mode.CREATE, tab);
		tab.setComponent(view);
		view.requestFocus();
		return tab;
	}

	/**
	 * Adds a tab that displays the given iteration in the given mode.
	 * 
	 * @param iteration The iteration to display
	 * @param mode The Mode to use
	 */
	public Tab addIterationTab(Iteration iteration, IterationPanel.Mode mode) {
		/*
		 * Since Iterations tabs are displayed on Janeway as "Iteration #1",
		 * get the id of the Iteration, and check if a tab with that title
		 * already exists.
		 * indexOfTab returns -1 if no tab with that title exists, or required tab index.
		 * Switch focus to that tab, or go ahead and create a new one.
		 */
		String iterationTitle = iteration.getIterationName();
		int iterationId = iteration.getId();
		int checkTabIndex = view.indexOfTab("Iteration #" + iterationId + " - " + iterationTitle);
		if(checkTabIndex != -1){
			view.setSelectedIndex(checkTabIndex);

			return null;
		}
		else{
			Tab tab = addTab();
			IterationView view = new IterationView(iteration, mode, tab);
			tab.setComponent(view);
			view.requestFocus();

			return tab;
		}
	}

	/**
	 * Adds a tab that displays the list of all requirements.
	 * 
	 * @return the tab that has a the table of requirements on it
	 */
	public Tab addListIterationTab() {
		int checkTabIndex = view.indexOfTab("Iteration List");
		if(checkTabIndex != -1){
			view.setSelectedIndex(checkTabIndex);
			return null;
		}
		else{
			//already brings focus to list tab if it was opened previously
			Tab tab = addTab();
			IterationListPanel panel = view.getIterationTableModel();
			panel.setTab(tab);
			tab.setComponent(panel);
			panel.requestFocus();
			return tab;
		}
	}

	/**
	 * Adds a tab that displays the given requirement.
	 * 
	 * @param iteration the iteration to display
	 * @return The created Tab 
	 */
	public Tab addEditIterationTab(Iteration iteration) {
		return addIterationTab(iteration, IterationPanel.Mode.EDIT);
	}

	/**
	 * Adds a tab that allows the user to create a new Iteration.
	 * 
	 * @return The created Tab
	 */
	public Tab addCreateIterationTab() {
		return addNewIterationTab();
	}


	/**
	 * Adds Edit Users Permissions tab.
	 * 
	 * @return Tab returns the edit user tab
	 */
	public Tab addEditUserPermissionsTab() {
		String checkTab = "Edit User Permissions";
		int checkTabIndex = view.indexOfTab(checkTab);
		if(checkTabIndex != -1){
			view.setSelectedIndex(checkTabIndex);
			return null;
		} else {
			Tab tab = addTab();
			UserPermissionView view = new UserPermissionView(tab);
			tab.setComponent(view);
			view.requestFocus();
			return tab;
		}
	}
}