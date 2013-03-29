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
**************************************************/

package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs;


import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;

/**
 * This tabbed pane will appear as the main content of the Requirements Manager tab.
 * It starts out showing the single Dashboard tab.
 * Adapted from MainTabView in the project Defect Tracker
 *
 * @author Tyler Stone 
 *
 * @version Mar 17, 2013
 *
 */
@SuppressWarnings("serial")
public class MainTabView extends JTabbedPane {
	
	private RequirementListPanel table;
	
	public MainTabView() {
		setTabPlacement(TOP);
		setTabLayoutPolicy(SCROLL_TAB_LAYOUT);
		setBorder(BorderFactory.createEmptyBorder(5, 3, 3, 3));
		addTab("Dashboard", new ImageIcon(), new DashboardTab(),
		       "Your Dashboard - notifications, etc.");
	}
	
	public void createTableModel(MainTabController controller){
		this.table = new RequirementListPanel(controller);
	}
	
	public RequirementListPanel getTableModel(){
		return this.table;
	}
	
	/* 
	 * Inserts tab
	 * @param title title of tab
	 * @param icon icon for tab
	 * @param component component for tab
	 * @param tip tooltip for tab
	 * @param index index for tab
	 * 
	 * @see javax.swing.JTabbedPane#insertTab(java.lang.String, javax.swing.Icon, java.awt.Component, java.lang.String, int)
	 */
	@Override
	public void insertTab(String title, Icon icon, Component component, String tip, int index) {
		super.insertTab(title, icon, component, tip, index);
		// the Dashboard tab cannot be closed
		if(!(component instanceof DashboardTab)) {
			setTabComponentAt(index, new ClosableTabComponent(this));
		}
	}
	
	/* 
	 * Removes tab at index
	 * @param index index of tab to remove
	 * @see javax.swing.JTabbedPane#removeTabAt(int)
	 */
	@Override
	public void removeTabAt(int index) {
		// if a tab does not have the close button UI, it cannot be removed
		if(getTabComponentAt(index) instanceof ClosableTabComponent) {
			super.removeTabAt(index);
		}
	}
	
	/* 
	 * Sets component  to an index
	 * @param index index of component
	 * @param component component to set
	 * @see javax.swing.JTabbedPane#setComponentAt(int, java.awt.Component)
	 */
	@Override
	public void setComponentAt(int index, Component component) {
		super.setComponentAt(index, component);
		fireStateChanged(); 
		//TODO: improve functionality for making sure toolbar knows if component changes
	}
	
}
