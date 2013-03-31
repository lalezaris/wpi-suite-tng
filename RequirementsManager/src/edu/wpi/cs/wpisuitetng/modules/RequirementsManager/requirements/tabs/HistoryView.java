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

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * The jPanel for all requirement history
 * TODO: implementation
 *
 * @author Chris Dunkers
 *
 * @version Mar 25, 2013
 *
 */
@SuppressWarnings("serial")
public class HistoryView extends JPanel {
	public HistoryView() {

		JPanel historyList = new JPanel();		
		historyList.setLayout(new BoxLayout(historyList, BoxLayout.PAGE_AXIS));
		
		JLabel historyLabel = new JLabel("History");
		historyList.add(historyLabel);
		
		for(int i = 0; i < 5; i++){
		HistoryObject obj = new HistoryObject();
		historyList.add(obj);
		}
		

		JScrollPane scrollPane = new JScrollPane(historyList);
		this.add(scrollPane);
		
		
		
	}
}
