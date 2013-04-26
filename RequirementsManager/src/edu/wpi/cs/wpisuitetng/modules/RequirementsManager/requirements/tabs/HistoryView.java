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
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.History.HistoricalChange;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementView;

/**
 * The JPanel for all requirement history.
 *
 * @author Arica Liu
 * @author Sam Lalezari
 *
 * @version April 2nd, 2013
 *
 */
@SuppressWarnings({ "serial" })
public class HistoryView extends JPanel {	
	private JList<HistoricalChange> list;
	private DefaultListModel<HistoricalChange> listModel;

	protected ArrayList<HistoricalChange> historyAL;
	JScrollPane listScrollPane;

	/**
	 * HistoryView Constructor.
	 * 
	 * @param parent Requirement view to view the history 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public HistoryView(RequirementView parent) {
		super(new BorderLayout());

		listModel = new DefaultListModel<HistoricalChange>();

		list = new JList(listModel);
		list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		list.setLayoutOrientation(JList.VERTICAL);
//		list.setFixedCellWidth(500);

		list.setCellRenderer(new HistoryViewCellRenderer(350));
		listScrollPane = new JScrollPane(list);

		add(listScrollPane, BorderLayout.CENTER);
	}

	/**
	 * Changes the history array list to the given array list.
	 * 
	 * @param history
	 */
	public void setHistoryList(ArrayList<HistoricalChange> history) {
		this.historyAL = history;
		for(int i = 0; i <historyAL.size(); i++){
			if(!listModel.contains(historyAL.get(i))){
				listModel.add(0, historyAL.get(i));}
		}
		//redisplay();
		repaint();
		revalidate();
		//redisplay();
	}

	/**
	 * Redisplays the history view.
	 */
	protected void redisplay(){

		listModel = new DefaultListModel<HistoricalChange>();

		for(int i = 0; i <this.historyAL.size(); i++){
			if(!listModel.contains(historyAL.get(i))){
				listModel.add(0, historyAL.get(i));}
		}

		if (list!=null)
		this.remove(listScrollPane);

		//Create the list and put it in a scroll pane.
		list = new JList<HistoricalChange>(listModel);
		list.setLayoutOrientation(JList.VERTICAL);

		list.setCellRenderer(new HistoryViewCellRenderer(350));
		JScrollPane listScrollPane = new JScrollPane(list);

		add(list, BorderLayout.CENTER);
	}


	/**
	 * Gets history list.
	 * 
	 * @return list of history
	 */
	public ArrayList<HistoricalChange> getHistoryList() {
		return this.historyAL;
	}
}