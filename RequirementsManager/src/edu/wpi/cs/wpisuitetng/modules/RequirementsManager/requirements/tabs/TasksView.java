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
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.AcceptanceTest;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Task;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.SaveTaskListener;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tasks.TasksPanel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tasks.controller.RetrieveRequirementsTasksController;

/**
 * Tab panel for viewing and working with tasks.
 *
 * @author Evan Polekoff
 * @version Apr 18, 2013
 */
@SuppressWarnings("serial")
public class TasksView extends JPanel{
	
	protected RequirementView parent;
	
	private ArrayList<Task> list;
	private ArrayList<TasksPanel> taskPanelArray;
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
		list = new ArrayList<Task>();
		taskPanelArray = new ArrayList<TasksPanel>();
		
		
		//Get the ScrollPane going.
		listModel = new DefaultListModel<Task>();
		//list = new JList(listModel);
		//list.setLayoutOrientation(JList.VERTICAL);

		//list.setCellRenderer(new HistoryViewCellRenderer(350));
		JScrollPane listScrollPane = new JScrollPane();
		
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
		this.add(taskPanelArray.get(0));
		listScrollPane.add(taskPanelArray.get(0));
	}
	
	/**Add a task from the View.
	 * @param t The task to add
	 */
	public void addTask(Task t){
		boolean hasTask = false;
		int testLocation = doesTaskExist(t.getId());
		if(testLocation != -1)
			hasTask = true;
		if (!hasTask){
			list.add(t);
		}else{
			System.out.println("ERROR: Task " + list.get(testLocation).getName() + " already exists");
		}
		for(int i = 0; i < list.size(); i++){
			if(!listModel.contains(list.get(i))){
				listModel.add(i, list.get(i));}
		}
	}
	
	/**Used to update a given Task
	 * @param a
	 */
	public void replaceTask(Task t){
		boolean hasTask = false;
		int taskLocation = doesTaskExist(t.getId());
		if(taskLocation != -1)
			hasTask = true;
		//Update all of the fields.
		if (hasTask){
			list.get(taskLocation).setAssigneeName(t.getAssigneeName());
			list.get(taskLocation).setStatus(t.getStatus());
			list.get(taskLocation).setDescription(t.getDescription());
			list.get(taskLocation).setEffort(t.getEffort());
			list.get(taskLocation).setName(t.getName());
		}
		for(int i = 0; i < list.size(); i++){
			if(!listModel.contains(list.get(i))){
				listModel.add(i, list.get(i));}
		}
	}
	
	/**Look for the given task in the list.
	 * @param id The task to look for.
	 * @return The spot in the list that this task belongs to, if it does.
	 */
	private int doesTaskExist(int id) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getId() == id){
				return i;
			}
		}
		return -1;
	}

	/**
	 * Changes the history array list to the given array list.
	 * 
	 * @param history
	 */
	public void setList(ArrayList<Task> task) {
		this.list = task;
		for(int i = 0; i <list.size(); i++){
			if(!listModel.contains(list.get(i))){
				listModel.add(0, list.get(i));}
		}
		repaint();
		revalidate();
	}
}
