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
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

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
	private JList<HistoryObject> list;
    private DefaultListModel<HistoryObject> listModel;

	/**
	 * HistoryView Constructor
	 * 
	 */
	public HistoryView() {
		super(new BorderLayout());
		
		int numObjects = 100; // NUMBER OF HistoryObjects to add
		
		listModel = new DefaultListModel<HistoryObject>();		
		
		for(int i = 0; i <numObjects; i++){
		HistoryObject obj = new HistoryObject();
		listModel.add(i, obj);
		}

		//Create the list and put it in a scroll pane.
        list = new JList<HistoryObject>(listModel);
        list.setVisibleRowCount(5);
        JScrollPane listScrollPane = new JScrollPane(list);
        
        add(listScrollPane, BorderLayout.CENTER);
	}
}
