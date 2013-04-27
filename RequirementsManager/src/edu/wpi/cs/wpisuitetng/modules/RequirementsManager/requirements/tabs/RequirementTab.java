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
 *  "Michael Perrone"
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.tabs;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * Interface for a tab to be added to the Requirements Panel
 *
 * @author "Michael Perrone"
 *
 * @version Apr 21, 2013
 *
 */
@SuppressWarnings("serial")
public abstract class RequirementTab extends JPanel{
	
	/**
	 * @return the title that will be displayed in the tab 
	 */
	public abstract String getTabTitle();
	
	
	/**
	 * @return the ImageIcon to be displayed in the tab 
	 */
	public abstract ImageIcon getImageIcon();
	
	
	/**
	 * @return the text to be displayed when the tab is being hovered over
	 */
	public abstract String getTooltipText();
}
