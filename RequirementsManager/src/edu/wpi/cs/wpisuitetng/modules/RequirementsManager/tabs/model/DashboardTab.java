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

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * The basic dashboard for the Requirements Manager Tab.
 * Adapted from Defect Tracker
 *
 * @author Tyler Stone 
 * @version Mar 17, 2013
 *
 */
@SuppressWarnings("serial")
public class DashboardTab extends JPanel{
	/**
	 * Class Constructor for Dashboard.
	 * 
	 */
	public DashboardTab() {
		JLabel testLabel = new JLabel("This is the dashboard panel.");
		this.add(testLabel);
	}
}