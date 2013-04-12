/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Andrew Hurle
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.model;

import java.awt.Component;

import javax.swing.Icon;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.MainTabView;

/**
 * Holds values given to it, but doesn't actually change the given MainTabView.
 */
public class DummyTab extends Tab {

	private String title;
	private Icon icon;
	private String toolTipText;
	private Component component;

	/**
	 * Construct a DummyTab - arguments ignored.
	 *
	 * @param view ignored
	 * @param tabComponent ignored
	 */
	public DummyTab(MainTabView view, Component tabComponent) {
		super(null, null);
	}
	
	/**
	 * Same as DummyTab(null, null).
	 */
	public DummyTab() {
		this(null, null);
	}
	
	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.model.Tab#getTitle()
	 */
	@Override
	public String getTitle() {
		return title;
	}
	
	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.model.Tab#setTitle(java.lang.String)
	 */
	@Override
	public void setTitle(String title) {
		this.title = title;
	}
	
	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.model.Tab#getIcon()
	 */
	@Override
	public Icon getIcon() {
		return icon;
	}
	
	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.model.Tab#setIcon(javax.swing.Icon)
	 */
	@Override
	public void setIcon(Icon icon) {
		this.icon = icon;
	}
	
	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.model.Tab#getToolTipText()
	 */
	@Override
	public String getToolTipText() {
		return toolTipText;
	}
	
	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.model.Tab#setToolTipText(java.lang.String)
	 */
	@Override
	public void setToolTipText(String toolTipText) {
		this.toolTipText = toolTipText;
	}
	
	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.model.Tab#getComponent()
	 */
	public Component getComponent() {
		return component;
	}
	
	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.model.Tab#setComponent(java.awt.Component)
	 */
	public void setComponent(Component component) {
		this.component = component;
	}
}