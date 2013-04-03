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
 *  Arica Liu
 *  Sam Lalezari
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.tabs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.History.HistoricalChange;

/**
 * The jPanel for all requirement history
 * TODO: implementation
 *
 * @author Arica Liu
 * @author Sam Lalezari
 *
 * @version April 2nd, 2013
 *
 */
@SuppressWarnings("serial")
public class HistoryView extends JPanel {	
	private JList<HistoricalChange> list;
    private DefaultListModel<HistoricalChange> listModel;

	/**
	 * HistoryView Constructor
	 * 
	 */
	public HistoryView(ArrayList<HistoricalChange> history) {
		super(new BorderLayout());
		
		int numObjects = history.size(); // NUMBER OF HistoryObjects to add
		
		listModel = new DefaultListModel<HistoricalChange>();		
		
		
		for(int i = 0; i <numObjects; i++){
//		HistoricalChange obj = history.get(i);
		listModel.add(i, history.get(i));
		}

		//Create the list and put it in a scroll pane.
        list = new JList<HistoricalChange>(listModel);
//		list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
//		list.setMaximumSize(new Dimension(200,0));
        list.setVisibleRowCount(5);
        JScrollPane listScrollPane = new JScrollPane(list);
        
        add(listScrollPane, BorderLayout.CENTER);
	}
}
