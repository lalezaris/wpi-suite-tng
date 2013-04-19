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
 *  Evan Polekoff
 **************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.tabs;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.History.HistoricalChange;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Task;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementView;

/**
 * Tab panel for viewing and working with tasks.
 *
 * @author Evan Polekof
 * @version Apr 18, 2013
 */
@SuppressWarnings("serial")
public class TasksView extends JPanel{
	
	protected RequirementView parent;
	
	private ArrayList<Task> taskArray;
	private JList<Task> list;
	private DefaultListModel<Task> listModel;
	
	/** The layout manager for this panel */
	protected GridBagLayout layout;

	/**
	 * Instantiates a new tasks view.
	 *
	 * @param req the requirement holding the tasks
	 */
	public TasksView(RequirementView parent) {
		super(new BorderLayout());
		
		//Set initial variables
		taskArray = new ArrayList<Task>();
		
		//Get the ScrollPane going.
		listModel = new DefaultListModel<Task>();
		list = new JList(listModel);
		list.setLayoutOrientation(JList.VERTICAL);

		//list.setCellRenderer(new HistoryViewCellRenderer(350));
		JScrollPane listScrollPane = new JScrollPane(list);

		add(listScrollPane, BorderLayout.CENTER);
	}
	

	/**Setter
	 * @param tasks Array to set to.
	 */
	public void setTaskArray(Task[] tasks) {
		taskArray = new ArrayList<Task>(Arrays.asList(tasks));
	}
}
