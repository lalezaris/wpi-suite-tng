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

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * The basic dashboard for the Requirements Manager Tab.
 *
 * @author Tyler Stone - adapted from Defect Tracker
 *
 * @version Mar 17, 2013
 *
 */
public class DashboardTab extends JPanel{
	
	
	public DashboardTab() {
		JLabel testLabel = new JLabel("This is the dashboard panel.");
		this.add(testLabel);
	}
}
