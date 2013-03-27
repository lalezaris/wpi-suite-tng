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

import java.awt.GridLayout;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementView;

/**
 * The basic dashboard for the Requirements Manager Tab.
 * Adapted from Defect Tracker
 *
 * @author Tyler Stone 
 * @modified by Chris H on Mar 24
 * @version Mar 17, 2013
 *
 */
public class DashboardTab extends JPanel{
	
	
	/**
	 * Class Constructor
	 * 
	 */
	public DashboardTab() {
		//super (new GridLayout(1,1));
		JLabel testLabel = new JLabel("This is the dashboard panel.");
		this.add(testLabel);
		
		
	}
	
	
	
	
}
