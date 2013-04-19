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
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.History.HistoricalChange;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Task;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tasks.TasksPanel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tasks.controller.RetrieveRequirementsTasksController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tasks.controller.SaveTaskListener;

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
	private ArrayList<TasksPanel> taskPanelArray;
	private JList<Task> list;
	private DefaultListModel<Task> listModel;
	
	/** The layout manager for this panel */
	protected GridBagLayout layout;
	
	private RetrieveRequirementsTasksController loadAllController;

	/**
	 * Instantiates a new tasks view.
	 *
	 * @param req the requirement holding the tasks
	 */
	public TasksView(RequirementView parent) {
		super(new BorderLayout());
		
		//Make the Controller to load all of the tasks.
		loadAllController = new RetrieveRequirementsTasksController(this);
		
		//Set initial variables
		taskArray = new ArrayList<Task>();
		taskPanelArray = new ArrayList<TasksPanel>();
		
		//Get the ScrollPane going.
		listModel = new DefaultListModel<Task>();
		list = new JList(listModel);
		list.setLayoutOrientation(JList.VERTICAL);

		list.setCellRenderer(new HistoryViewCellRenderer(350));
		JScrollPane listScrollPane = new JScrollPane(list);
		
		createTasksPanels();
		displayTasks(listScrollPane);
		
		add(listScrollPane, BorderLayout.CENTER);
	}
	
	/**Create the task panels to display.
	 * 
	 */
	private void createTasksPanels(){
		//TODO Make this a for loop for the length of the taskArray and add one for each. I am just doing 1 for testing.
		taskPanelArray.add(new TasksPanel());
		taskPanelArray.get(0).getSaveButton().addActionListener(new SaveTaskListener());
	}
	
	/**Go through the list and display them all.
	 * 
	 */
	private void displayTasks(JScrollPane listScrollPane){
		listScrollPane.add(taskPanelArray.get(0));
	}
	
	/**
	 * Changes the history array list to the given array list.
	 * 
	 * @param history
	 */
	public void setTasksList(ArrayList<Task> task) {
		this.taskArray = task;
		for(int i = 0; i <taskArray.size(); i++){
			if(!listModel.contains(taskArray.get(i))){
				listModel.add(0, taskArray.get(i));}
		}
		redisplay();
		repaint();
		revalidate();
		redisplay();
	}

	protected void redisplay(){

		listModel = new DefaultListModel<Task>();

		for(int i = 0; i <this.taskArray.size(); i++){
			if(!listModel.contains(taskArray.get(i))){
				listModel.add(0, taskArray.get(i));}
		}
		
		if (list!=null)
		this.remove(list);
		
		//Create the list and put it in a scroll pane.
		list = new JList<Task>(listModel);
		list.setLayoutOrientation(JList.VERTICAL);

		list.setCellRenderer(new HistoryViewCellRenderer(350));
		JScrollPane listScrollPane = new JScrollPane(list);
		displayTasks(listScrollPane);
		
		add(listScrollPane, BorderLayout.CENTER);
	}
	
	
	/**Set the array and repaint everything.
	 * @param tasks Array to set to.
	 */
	public void setTaskArray(Task[] tasks) {
		taskArray = new ArrayList<Task>(Arrays.asList(tasks));
		setTasksList(taskArray);
	}
}
