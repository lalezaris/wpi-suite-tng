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

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;

/**
 * The jPanel for all requirement history
 * TODO: implementation
 *
 * @author Chris Dunkers
 * @author Sam Lalezari
 *
 * @version Mar 31, 2013
 *
 */
@SuppressWarnings("serial")
public class HistoryView extends JScrollPane {	

	/**
	 * HistoryView Constructor
	 * 
	 */
	public HistoryView() {
		
		int numObjects = 50; // NUMBER OF HistoryObjects to add
		
		JPanel historyListPanel = new JPanel();	
		JPanel historyListContainerPanel = new JPanel(); // Holds historyListPanel in the NORTH part of BorderLayout 
														 // so the HistoryObjects don't automatically fill the whole space
		this.setViewportView(historyListContainerPanel);
		historyListPanel.setLayout(new BoxLayout(historyListPanel, BoxLayout.Y_AXIS));
		historyListContainerPanel.setLayout(new BorderLayout());
		historyListContainerPanel.add(historyListPanel, BorderLayout.NORTH);
		
		for(int i = 0; i <numObjects; i++){
		HistoryObject obj = new HistoryObject();
		historyListPanel.add(obj);
		}
				
	}
}
