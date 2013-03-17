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

//import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.DashboardTab;

/**
 * This tabbed pane will appear as the main content of the Requirements Manager tab.
 * It starts out showing the single Dashboard tab.
 *
 * @author Tyler Stone, adapted from Defect Tracker
 *
 * @version Mar 17, 2013
 *
 */
@SuppressWarnings("serial")
public class MainTabView extends JTabbedPane {
	
	public MainTabView() {
		setTabPlacement(TOP);
		setTabLayoutPolicy(SCROLL_TAB_LAYOUT);
		setBorder(BorderFactory.createEmptyBorder(5, 3, 3, 3));
		addTab("Dashboard", new ImageIcon(), new DashboardTab(),
		       "Your Dashboard - notifications, etc.");
	}
	
	@Override
	public void insertTab(String title, Icon icon, Component component, String tip, int index) {
		super.insertTab(title, icon, component, tip, index);
		// the Dashboard tab cannot be closed
		if(!(component instanceof DashboardTab)) {
			setTabComponentAt(index, new ClosableTabComponent(this));
		}
	}
	
	@Override
	public void removeTabAt(int index) {
		// if a tab does not have the close button UI, it cannot be removed
		if(getTabComponentAt(index) instanceof ClosableTabComponent) {
			super.removeTabAt(index);
		}
	}
	
	@Override
	public void setComponentAt(int index, Component component) {
		super.setComponentAt(index, component);
		fireStateChanged(); // hack to make sure toolbar knows if component changes
	}
	
}
