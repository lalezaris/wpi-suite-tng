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
 *  Chris Hanna
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.model;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.MainTabView;

/**
 * A wrapper class for MainTabView that can be given to components within that view
 * in order to allow them to easily change their titles and icons.
 * 
 * Adapted from DummyTab in the project DefectTracker
 * 
 * @author Tyler Stone 
 * @version Mar 17, 2013
 *
 */
public class Tab {

	private final MainTabView view;
	private final Component tabComponent;

	/**
	 * Create a Tab identified by the given MainTabView and tabComponent.
	 * 
	 * @param view The MainTabView this Tab belongs to
	 * @param tabComponent The tabComponent for this Tab
	 */
	public Tab(MainTabView view, Component tabComponent) {
		this.view = view;
		this.tabComponent = tabComponent;
	}
	
	/**
	 * Gets the index
	 * 
	 * @return the index
	 */
	private int getIndex() {
		return view.indexOfTabComponent(tabComponent);
	}
	
	/**
	 * Gets the title
	 * 
	 * @return the title at an index
	 */
	public String getTitle() {
		return view.getTitleAt(getIndex());
	}
	
	/**
	 * Sets the title
	 * 
	 * @param title Set the title of the Tab to this String
	 */
	public void setTitle(String title) {
		
		if (getIndex() > -1){
			view.setTitleAt(getIndex(), title);
			tabComponent.invalidate(); // needed to make tab shrink with smaller title
		} else System.out.println("Blank ID. title");
	}
	
	/**
	 * Gets the icon
	 * 
	 * @return the icon at an index
	 */
	public Icon getIcon() {
		return view.getIconAt(getIndex());
	}
	
	/**
	 * Sets the icon of the tab
	 * 
	 * @param icon Set the icon of the Tab to this Icon
	 */
	public void setIcon(Icon icon) {
		view.setIconAt(getIndex(), icon);
	}
	
	/**
	 * Gets the Tool Tip Text
	 * 
	 * @return gets the tool tip text at an index
	 */
	public String getToolTipText() {
		return view.getToolTipTextAt(getIndex());
	}
	
	/**
	 * Sets the Tool Tip Text
	 * 
	 * @param toolTipText Set the tooltip of the Tab to this String
	 */
	public void setToolTipText(String toolTipText) {
		if (getIndex() > -1){
			view.setToolTipTextAt(getIndex(), toolTipText);
		} else System.out.println("Blank ID. Tooltip");
	}
	
	/**
	 * Get a Component
	 * 
	 * @return Get the Component at an index
	 */
	public Component getComponent() {
		return view.getComponentAt(getIndex());
	}
	
	/**
	 * Sets the Component
	 * 
	 * @param component Set the component contained by this Tab to this Component
	 */
	public void setComponent(Component component) {
		view.setComponentAt(getIndex(), component);
	}
	
	/**
	 * Gets this index
	 * 
	 * @return the index
	 */
	public int getThisIndex() {
		return view.indexOfTabComponent(tabComponent);
	}
	
	/**
	 * Gets view
	 * 
	 * @return view
	 */
	public MainTabView getView() {
		return view;
	}
	
}
