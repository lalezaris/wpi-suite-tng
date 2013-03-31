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
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.tabs;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * The jPanel for all requirement history
 * TODO: implementation
 *
 * @author Chris Dunkers
 *
 * @version Mar 25, 2013
 *
 */
public class HistoryView extends JPanel {
	public HistoryView() {
		
		JLabel historyLabel = new JLabel("History");
		this.add(historyLabel);
		
		HistoryObject obj = new HistoryObject();
		
		this.add(obj);
		
		
	}
}
