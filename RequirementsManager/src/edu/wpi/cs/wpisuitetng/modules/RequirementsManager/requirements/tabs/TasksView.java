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
import java.awt.Insets;

import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;


import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Task;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RMPermissionsLevel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RequirementStatus;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.TaskStatus;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.SaveTaskListener;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.TaskFeatureListener;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.TaskFieldsListener;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.TaskSearchListener;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.observers.CurrentUserPermissions;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tasks.TasksPanel;

/**
 * Tab panel for viewing and working with tasks.
 *
 * @author Evan Polekoff
 * @version Apr 18, 2013
 */
@SuppressWarnings({"serial"})
public class TasksView extends JPanel{

	protected RequirementView parent;
	
	private ArrayList<Task> list;

	private ArrayList<TasksPanel> taskPanelArray;
	private JPanel featurePanel;
	private JPanel overallPanel;//One panel to hold them all.

	private JScrollPane listScrollPane;
	private JSplitPane splitPane;
	
	private JTextField containsField;
	private JCheckBox hideBox;
	
	//State Variables
	private boolean changed;
	private boolean hidden;
	private String contains = "";
	
	/** The layout manager for this panel */
	protected GridBagLayout layout;

	//Permissions level
	protected RMPermissionsLevel pLevel;


	/**
	 * Instantiates a new tasks view.
	 *
	 * @param parent the requirement holding the tasks
	 */
	public TasksView(RequirementView parent) {
		super(new BorderLayout());
		
		//Set initial variables
		if(list == null)//Only reset and initialize the list if it is new.
			list = new ArrayList<Task>();
		
		//Get permissions
		this.pLevel = CurrentUserPermissions.getCurrentUserPermission();
		//Use a grid bag layout manager
		layout = new GridBagLayout();
		this.setLayout(layout);
		
		this.parent = parent;
		
		//Create all of the panels(one per task) and put them in the array. Also put features there.
		redisplay();
	}

	/**Create the task panels to display.
	 * 
	 */
	private void createTasksPanels(){
		
		overallPanel = new JPanel();
		overallPanel.setLayout(new GridBagLayout());
		
		GridBagConstraints cTask = new GridBagConstraints();
		GridBagConstraints cScrolling = new GridBagConstraints();
		
		//Clear the array so you can refill it.
		taskPanelArray = new ArrayList<TasksPanel>();
		
		//Go through for all tasks in the list, + 1 for new tasks.
		for(int i = 0; i < list.size() + 1; i ++){
			//Constraints
			cTask.anchor = GridBagConstraints.FIRST_LINE_START; 
			cTask.fill = GridBagConstraints.HORIZONTAL;
			cTask.gridx = 0;
			cTask.gridy = i;
			cTask.weightx = 0.5;
			cTask.weighty = 0.5;
			cTask.gridheight = 1;
			cTask.insets = new Insets(10,10,10,0); //top,left,bottom,right
			
			//Make the panel
			TasksPanel tempPanel = new TasksPanel();
			tempPanel.setLayout(new GridBagLayout());
			
			//Fill its boxes
			if(i < list.size()){//For the tasks that already exist, put them here.
				
				tempPanel.getTxtName().setText(list.get(i).getName());
				tempPanel.getTxtDescription().setText(list.get(i).getDescription());
				tempPanel.getTxtAssignee().setText(list.get(i).getAssigneeName());
				tempPanel.getTxtEffort().setText(Integer.toString(list.get(i).getEffort()));
				tempPanel.getCmbStatus().setSelectedItem(list.get(i).getStatus());
				tempPanel.getSaveButton().addActionListener(new SaveTaskListener(list.get(i).getId(), this));
			}
			else{
				if(list.size() > 0)
					tempPanel.getSaveButton().addActionListener(new SaveTaskListener(list.get(list.size()-1).getId()+1, this));//Make the id 1 higher
				else
					tempPanel.getSaveButton().addActionListener(new SaveTaskListener(1, this));//No tasks, start at ID 1.
			}
			//Default the save button and status to disabled
			if(tempPanel.getTxtName().getText().equals("") || tempPanel.getTxtDescription().getText().equals("")){
				tempPanel.getSaveButton().setEnabled(false);//Disable the save if the name is blank.
			}
			if(tempPanel.getTxtEffort().getText().equals("") || tempPanel.getTxtEffort().getText().equals("0")){
				tempPanel.getCmbStatus().setEnabled(false);//Disable the status if the effort is not set.
			}
			
			//Deal with permissions
			if(pLevel == RMPermissionsLevel.NONE || pLevel == RMPermissionsLevel.UPDATE){
				//Gray all of the fields.
				tempPanel.getTxtName().setEditable(false);
				tempPanel.getTxtDescription().setEditable(false);
				tempPanel.getTxtAssignee().setEditable(false);
				tempPanel.getTxtEffort().setEditable(false);
				tempPanel.getCmbStatus().setEditable(false);
				tempPanel.getSaveButton().setEnabled(false);
			}
				
			
			//Add listeners to all of the fields.
			tempPanel.getTxtName().addKeyListener(new TaskFieldsListener(tempPanel, this));
			tempPanel.getTxtDescription().addKeyListener(new TaskFieldsListener(tempPanel, this));
			tempPanel.getTxtAssignee().addKeyListener(new TaskFieldsListener(tempPanel, this));
			tempPanel.getTxtEffort().addKeyListener(new TaskFieldsListener(tempPanel, this));
		
			
			
			//Put it in the array and panel.
			boolean canDisplay = true;
			
			if(i != list.size()){//Always display the last panel, since it is how new tasks are added.
				//Hide Closed and Accepted
				if(hidden && (list.get(i).getStatus() == TaskStatus.ACCEPTED || list.get(i).getStatus() == TaskStatus.CLOSED)){
					canDisplay = false;
				}
				//Hide non-matching names
				if(!contains.equals("") && !list.get(i).getName().contains(contains)){
					canDisplay = false;
				}
			}
			if(canDisplay){
				overallPanel.add(tempPanel, cTask);//Put each one in the overallPanel to display them all at once.
			}
			taskPanelArray.add(tempPanel);
		}
		
		//Put the panels (overallPanel) into a scrollpane.
		cScrolling.anchor = GridBagConstraints.FIRST_LINE_START;
		cScrolling.fill = GridBagConstraints.HORIZONTAL;
		cScrolling.gridx = 1;
		cScrolling.gridy = 0;
		cScrolling.weightx = 0.5;
		cScrolling.weighty = 0.5;
		cScrolling.insets = new Insets(10,10,10,0); //top,left,bottom,right
		
		//Add to pane
		listScrollPane = new JScrollPane(overallPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		//this.add(listScrollPane, cScrolling);
	}
	
	/**Put in the boxes and other features that go along with the Tasks.
	 * 
	 */
	private void displayFeatures(){
		JLabel containsLabel = new JLabel("Search for tasks whose names contain: ", JLabel.TRAILING);
		containsField = new JTextField("", 20);
		hideBox = new JCheckBox("Hide Closed and Accepted");
		
		//Set boxes
		if(hidden)
			hideBox.setSelected(true);
		if(!contains.equals(""))
			containsField.setText(contains);
		
		featurePanel = new JPanel();
		featurePanel.setLayout(new GridBagLayout());
		
		GridBagConstraints cFeat = new GridBagConstraints();
		GridBagConstraints cOverall = new GridBagConstraints();
		
		//Constraints
		cFeat.anchor = GridBagConstraints.FIRST_LINE_START; 
		cFeat.fill = GridBagConstraints.HORIZONTAL;
		cFeat.gridx = 0;
		cFeat.gridy = 0;
		cFeat.weightx = 0.5;
		cFeat.weighty = 0.5;
		cFeat.gridheight = 1;
		cFeat.insets = new Insets(10,10,10,0); //top,left,bottom,right
		featurePanel.add(containsLabel, cFeat);
		
		cFeat.anchor = GridBagConstraints.FIRST_LINE_START; 
		cFeat.fill = GridBagConstraints.HORIZONTAL;
		cFeat.gridx = 1;
		cFeat.gridy = 0;
		cFeat.weightx = 0.5;
		cFeat.weighty = 0.5;
		cFeat.gridheight = 1;
		cFeat.insets = new Insets(10,10,10,0); //top,left,bottom,right
		featurePanel.add(containsField, cFeat);
		
		cFeat.anchor = GridBagConstraints.FIRST_LINE_START; 
		cFeat.fill = GridBagConstraints.HORIZONTAL;
		cFeat.gridx = 0;
		cFeat.gridy = 2;
		cFeat.weightx = 0.5;
		cFeat.weighty = 0.5;
		cFeat.gridheight = 1;
		cFeat.insets = new Insets(10,10,10,0); //top,left,bottom,right
		featurePanel.add(hideBox, cFeat);
		
		
		cOverall.anchor = GridBagConstraints.FIRST_LINE_START; 
		cOverall.fill = GridBagConstraints.HORIZONTAL;
		cOverall.gridx = 0;
		cOverall.gridy = 0;
		cOverall.weightx = 0.5;
		cOverall.weighty = 0.5;
		cOverall.gridheight = 1;
		cOverall.insets = new Insets(10,10,10,0); //top,left,bottom,right
		//Add listeners to the features
		containsField.addKeyListener(new TaskSearchListener(this));
		hideBox.addActionListener(new TaskFeatureListener(this));
		
		//this.add(featurePanel, cOverall);
		
		
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
			System.out.println("ERROR: Task " + list.get(testLocation).getName() + " already exists! (ID: " + t.getId() + ")");
		}
	}
	
	/**Used to update a given Task
	 * @param t A task with the same ID as the ne you want to replace but with new fields.
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
	}
	
	/**Find a task based on the id you give it.
	 * @param id The id of the task you want to find.
	 */
	public Task retrieveTask(int id){
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getId() == id){	
				return list.get(i);
			}
		}
		return new Task();
	}
	
	/**Look for the given task in the list.
	 * @param id The task to look for.
	 * @return The spot in the list that this task belongs to, if it does.
	 */
	public int doesTaskExist(int id) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getId() == id){
				return i;
			}
		}
		return -1;
	}

	/**
	 * Changes the tasks array list to the given array list.
	 * 
	 * @param task the list of tasks
	 */
	public void setList(ArrayList<Task> task) {
		this.list = task;
		redisplay();
	}
	
	/**If the saved requirement is closed, close every task that belongs to it.
	 * @param status The status of the requirement to check.
	 */
	public void closeIfClosed(RequirementStatus status) {
		if(status == RequirementStatus.COMPLETE){
			//Cycle through and close all tasks.
			for(int i = 0; i < list.size(); i++){
				list.get(i).setStatus(TaskStatus.CLOSED);
			}
			for(int i = 0; i < taskPanelArray.size(); i++){
				taskPanelArray.get(i).getCmbStatus().setEnabled(false);
			}
		}
		else{
			for(int i = 0; i < taskPanelArray.size(); i++){
				taskPanelArray.get(i).getCmbStatus().setEnabled(true);
			}
		}
	}

	/**
	 * Redisplay everything. Call after updating tasks.
	 */
	public void redisplay(){
		this.removeAll();
		
		displayFeatures();
		createTasksPanels();
		repaint();
		revalidate();
		setChanged(false);//Let it be saved again.
		
		/*
		GridBagConstraints splitConstraints = new GridBagConstraints();
		splitConstraints.fill = GridBagConstraints.BOTH;
		*/
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, featurePanel, listScrollPane);
		//splitPane.setMinimumSize(getPreferredSize());
		this.add(splitPane/*,splitConstraints*/);
	}

	/**
	 * @return the taskPanelArray
	 */
	public ArrayList<TasksPanel> getTaskPanelArray() {
		return taskPanelArray;
	}

	
	/**Get the tasks from the view.
	 * @return The list
	 */
	public ArrayList<Task> getTasks(){
		return list;
	}
	
	/**
	 * @return the sortBox
	 */
	public JTextField getContainsField() {
		return containsField;
	}

	/**
	 * @return the hideBox
	 */
	public JCheckBox getHideBox() {
		return hideBox;
	}

	/**
	 * @param hidden the hidden to set
	 */
	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	/**
	 * @param contains the string to look for
	 */
	public void setContains(String contains) {
		this.contains = contains;
	}

	/**
	 * @param changed the changed to set
	 */
	public void setChanged(boolean changed) {
		this.changed = changed;
	}

	/**
	 * @return the changed
	 */
	public boolean isChanged() {
		return changed;
	}
	
	
}
