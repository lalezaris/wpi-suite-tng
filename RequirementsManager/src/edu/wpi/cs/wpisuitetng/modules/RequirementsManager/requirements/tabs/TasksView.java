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
 *  Spicola
 *  Evan Polekoff
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.tabs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.border.Border;


import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Task;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RMPermissionsLevel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RequirementStatus;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.TaskStatus;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.SaveTaskListener;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.TaskDropdownListener;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.TaskFeatureListener;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.TaskFieldsListener;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.TaskSearchListener;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.observers.CurrentUserPermissions;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tasks.TasksPanel;

/**
 * Tab panel for viewing and working with tasks.
 *
 * @author Evan Polekoff
 * @author Spicola
 *
 * @version Apr 24, 2013
 *
 */
@SuppressWarnings({"serial"})
public class TasksView extends JPanel {
	/*layout manager for this panel*/
	protected BorderLayout layout;
	
protected RequirementView parent;
	
	private ArrayList<Task> list;
	private ArrayList<Task> originalList;

	private ArrayList<TasksPanel> taskPanelArray;
	private JPanel featurePanel;
	private JPanel overallPanel;//One panel to hold them all.

	private JScrollPane listScrollPane;
	private JSplitPane splitPane;
	private TasksPanel tempTaskPanel;
	
	private GridBagLayout layoutTasks;
	private GridBagLayout layoutDisplay;
	
	private JTextField containsField;
	private JCheckBox hideBox;
	
	//State Variables
	private boolean changed;
	private boolean hidden;
	private String contains = "";
	
	//Loaded data
	String[] users = {"None", "Yet"};
	
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
		list = new ArrayList<Task>();
		originalList = new ArrayList<Task>();
		
		//Get permissions
		this.pLevel = CurrentUserPermissions.getCurrentUserPermission();
		
		this.parent = parent;
		
		//Use a grid bag layout manager
		this.layout = new BorderLayout();
		this.setLayout(layout);
		
		redisplay();
	}
	
		/**
		 * Redisplays the task view.
		 */
		public void redisplay() {
		this.removeAll();
		repaint();
		revalidate();
		//setChanged(false);
		
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				displayFeatures(), createTasksPanels());
		this.add(splitPane,BorderLayout.CENTER);
	}
	
	/**
	 * Create the task panels to display.
	 * @return scroll pane
	 */
	private JScrollPane createTasksPanels() {
		
		//construct panels
		overallPanel = new JPanel();
		
		//Clear the array so you can refill it.
		taskPanelArray = new ArrayList<TasksPanel>();
		
		//Go through for all tasks in the list, + 1 for new tasks.
		for(int i = 0; i < list.size() + 1; i ++){
			//create constraint variables
			GridBagConstraints cTask = new GridBagConstraints();
			
			//Constraints
			cTask.anchor = GridBagConstraints.LINE_START; 
			cTask.gridx = 0;
			cTask.gridy = i;
			cTask.weightx = 0.5;
			cTask.weighty = 0.5;
			cTask.insets = new Insets(5,10,5,0); //top,left,bottom,right
		
			tempTaskPanel = new TasksPanel(users);
			
			if(i < list.size()){//For the tasks that already exist, put them here.
				
				tempTaskPanel.getTxtName().setText(list.get(i).getName());
				tempTaskPanel.getTxtDescription().setText(list.get(i).getDescription());
				tempTaskPanel.getCmbAssignee().setSelectedItem(list.get(i).getAssigneeName());
				tempTaskPanel.getTxtEffort().setText(Integer.toString(list.get(i).getEffort()));
				tempTaskPanel.getCmbStatus().setSelectedItem(list.get(i).getStatus());
				tempTaskPanel.getSaveButton().addActionListener(new SaveTaskListener(list.get(i).getId(), this));
				
				placeBorder(i);
			}
			else{
				if(list.size() > 0)
					tempTaskPanel.getSaveButton().addActionListener(new SaveTaskListener(list.get(list.size()-1).getId()+1, this));//Make the id 1 higher
				else
					tempTaskPanel.getSaveButton().addActionListener(new SaveTaskListener(1, this));//No tasks, start at ID 1.
			}
			
			//Default the save button and status to disabled
			if(tempTaskPanel.getTxtName().getText().equals("") || tempTaskPanel.getTxtDescription().getText().equals("")){
				tempTaskPanel.getSaveButton().setEnabled(false);//Disable the save if the name is blank.
			}
			if(tempTaskPanel.getTxtEffort().getText().equals("") || tempTaskPanel.getTxtEffort().getText().equals("0")){
				tempTaskPanel.getCmbStatus().setEnabled(false);//Disable the status if the effort is not set.
			}
			
			
			//Deal with permissions
			if(pLevel == RMPermissionsLevel.NONE || pLevel == RMPermissionsLevel.UPDATE){
				//Gray all of the fields.
				tempTaskPanel.getTxtName().setEditable(false);
				tempTaskPanel.getTxtDescription().setEditable(false);
				tempTaskPanel.getCmbAssignee().setEditable(false);
				tempTaskPanel.getTxtEffort().setEditable(false);
				tempTaskPanel.getCmbStatus().setEditable(false);
				tempTaskPanel.getSaveButton().setEnabled(false);
			}
			
			//Add listeners to all of the fields.
			tempTaskPanel.getTxtName().addKeyListener(new TaskFieldsListener(tempTaskPanel, this));
			tempTaskPanel.getTxtDescription().addKeyListener(new TaskFieldsListener(tempTaskPanel, this));
			tempTaskPanel.getCmbAssignee().addActionListener(new TaskDropdownListener(tempTaskPanel, this));
			tempTaskPanel.getTxtEffort().addKeyListener(new TaskFieldsListener(tempTaskPanel, this));
			tempTaskPanel.getCmbStatus().addActionListener(new TaskDropdownListener(tempTaskPanel, this));

		
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
				layoutTasks = new GridBagLayout();
				overallPanel.setLayout(layoutTasks);
				overallPanel.add(tempTaskPanel, cTask);//Put each one in the overallPanel to display them all at once.
			}
			taskPanelArray.add(tempTaskPanel);
		}
		
		//put overall into a scrollpane
		listScrollPane = new JScrollPane(overallPanel);
		
		return listScrollPane;
	}
	
	/**Put in the boxes and other features that go along with the Tasks.
	 * @return 
	 * 
	 */
	private JPanel displayFeatures(){
		//constraints
		GridBagConstraints cFeat = new GridBagConstraints();
		
		//panels
		featurePanel = new JPanel();
		
		JLabel containsLabel = new JLabel("Search for tasks whose names contain: ", JLabel.TRAILING);
		containsField = new JTextField(20);
		hideBox = new JCheckBox("Hide Closed and Accepted");
		
		//Set boxes
		if(hidden)
			hideBox.setSelected(true);
		if(!contains.equals(""))
			containsField.setText(contains);
		
		//individual panel -------------------
		layoutDisplay = new GridBagLayout();
		featurePanel.setLayout(layoutDisplay);
		
		cFeat.anchor = GridBagConstraints.LINE_START; 
		cFeat.gridx = 0;
		cFeat.gridy = 0;
		cFeat.weightx = 0.5;
		cFeat.weighty = 0.5;
		cFeat.insets = new Insets(5,10,5,0); //top,left,bottom,right
		featurePanel.add(containsLabel, cFeat);
		
		cFeat.anchor = GridBagConstraints.LINE_START; 
		cFeat.gridx = 1;
		cFeat.gridy = 0;
		cFeat.weightx = 0.5;
		cFeat.weighty = 0.5;
		cFeat.insets = new Insets(5,10,5,0); //top,left,bottom,right
		featurePanel.add(containsField, cFeat);
		
		cFeat.anchor = GridBagConstraints.LINE_START; 
		cFeat.gridx = 0;
		cFeat.gridy = 1;
		cFeat.weightx = 0.5;
		cFeat.weighty = 0.5;
		cFeat.insets = new Insets(5,10,5,0); //top,left,bottom,right
		featurePanel.add(hideBox, cFeat);
		
		//Add listeners to the features
		containsField.addKeyListener(new TaskSearchListener(this));
		hideBox.addActionListener(new TaskFeatureListener(this));
				
		return featurePanel;
	}
	
	
	/**Place the border if this is edited.
	 * @param position The task to add it to.
	 */
	public void placeBorder(int position){
		
		//If something was changed, put the border on
		if(position < originalList.size() && 
				list.get(position).getName().equals(originalList.get(position).getName()) &&
				list.get(position).getDescription().equals(originalList.get(position).getDescription()) &&
				list.get(position).getAssigneeName().equals(originalList.get(position).getAssigneeName()) &&
				list.get(position).getEffort() == originalList.get(position).getEffort() &&
				list.get(position).getStatus().equals(originalList.get(position).getStatus())){
				//Remove the border
				tempTaskPanel.setBorder(null);
		}
		//Set the border to show it is different.
		else{
			Border compound = BorderFactory.createCompoundBorder(
					BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder());
			Border yellowline = BorderFactory.createLineBorder(new Color(255, 252, 132));
			//Make it yellow
			compound = BorderFactory.createCompoundBorder(
					yellowline, compound);
			//Add a 3rd line
			compound = BorderFactory.createCompoundBorder(
					yellowline, compound);
			//Draw the border on the panel that was edited.
			tempTaskPanel.setBorder(compound);
		}
		
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
	
	/**
	 * Find a task based on the id you give it.
	 *
	 * @param id The id of the task you want to find.
	 * @return the task
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
	public void setList(ArrayList<Task> tasks) {
		this.list = tasks;
		makeOriginalList(tasks);//Keep track of the tasks in the list.
		redisplay();
	}
	
	/**Copy the list element by element so they are not shallow copies.
	 * @param list The list to copy
	 */
	private void makeOriginalList(ArrayList<Task> tasks){
		originalList.clear();
		for(int i = 0; i < tasks.size(); i++){
			originalList.add(new Task(tasks.get(i).getName(), tasks.get(i).getDescription(), tasks.get(i).getAssigneeName(), tasks.get(i).getEffort(), tasks.get(i).getId()));
			originalList.get(originalList.size()-1).setStatus(tasks.get(i).getStatus());
		}
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
		}
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
