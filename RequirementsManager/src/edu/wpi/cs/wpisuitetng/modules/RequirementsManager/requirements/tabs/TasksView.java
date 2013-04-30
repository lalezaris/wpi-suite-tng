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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.BorderFactory;
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
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.CreateTaskListener;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.SaveTaskListener;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.TaskDropdownListener;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.TaskFeatureListener;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.TaskFieldsListener;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.TaskSearchListener;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.observers.CurrentUserPermissions;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tasks.TasksPanel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.view.MainView;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;

/**
 * Tab panel for viewing and working with tasks.
 *
 * @author Evan Polekoff
 * @author Spicola
 *
 * @version Apr 24, 2013
 *
 */
@SuppressWarnings("serial")
public class TasksView extends RequirementTab implements FocusListener{
	/*layout manager for this panel*/
	protected BorderLayout layout;

	protected RequirementView parent;

	private ArrayList<Task> list;//Assume that every task in list (i.e, list[i]) will always line up with its matching panel (i.e, taskPanelArray[i])
	private ArrayList<Task> originalList;

	private ArrayList<TasksPanel> taskPanelArray;//Assume that every task in list (i.e, list[i]) will always line up with its matching panel (i.e, taskPanelArray[i])
	private TasksPanel newTaskPanel;
	private JPanel tempPanel;
	private JPanel featurePanel;
	private JPanel overallPanel;//One panel to hold them all.

	private JScrollPane listScrollPane;
	private JScrollPane featScrollPane;

	private JSplitPane splitPane;

	private GridBagLayout layoutTasks;
	private GridBagLayout layoutDisplay;
	private GridBagLayout layoutDisplayTwo;

	private JTextField containsField;
	private JCheckBox hideBox;
	
	//State Variables
	private boolean changed;
	private boolean hidden;
	private String contains = "";

	//Loaded data
	User[] users;
	String[] userNames = {""};

	//Permissions level
	protected RMPermissionsLevel pLevel;

	/**
	 * Instantiates a new tasks view.
	 *
	 * @param parent the requirement holding the tasks
	 */
	public TasksView(RequirementView parent) {
		setLayout(new BorderLayout());
		//Set initial variables
		list = new ArrayList<Task>();
		originalList = new ArrayList<Task>();
		taskPanelArray = new ArrayList<TasksPanel>();

		//Initialize the assignee list
		users = CurrentUserPermissions.getProjectUsers();
		if(users != null){
			userNames = new String[users.length + 1];
			userNames[0] = "";
			for(int i=0;i<users.length;i++){
				userNames[i+1] = users[i].getUsername();
			}
		}

		//Get permissions
		pLevel = CurrentUserPermissions.getCurrentUserPermission();

		this.parent = parent;

		//Use a grid bag layout manager
		layout = new BorderLayout();
		this.setLayout(layout);

		//Create some Feature stuff
		createNewTaskPanel();
		
		redisplay();
	}

	/**
	 * Redisplays the task view.
	 */
	public void redisplay() {
		this.removeAll();

		//Make the big two panels.
		listScrollPane = displayTasksPanels();
		featScrollPane = displayFeatures();

		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				featScrollPane, listScrollPane);
		
		splitPane.getComponent(0).setMinimumSize(splitPane.getComponent(0).getPreferredSize());
		this.add(splitPane,BorderLayout.CENTER);
	}
	
	
	/**Create task panels out of the tasks.
	 * @param newTask The task to make the panel out of.
	 * @return The resulting TaskPanel
	 */
	private TasksPanel createTaskPanel(Task newTask){
		//Temporary panel to do work on before adding
		TasksPanel tempTaskPanel = new TasksPanel(userNames);
		

		tempTaskPanel.getTxtName().setText(newTask.getName());
		tempTaskPanel.getTxtDescription().setText(newTask.getDescription());
		tempTaskPanel.getCmbAssignee().setSelectedItem(newTask.getAssigneeName());
		tempTaskPanel.getTxtEffort().setText(Integer.toString(newTask.getEffort()));
		tempTaskPanel.getCmbStatus().setSelectedItem(newTask.getStatus());
		tempTaskPanel.getSaveButton().addActionListener(new SaveTaskListener(newTask.getId(), this));
		
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
		
		return tempTaskPanel;
	}
	
	/**Display the task panels if they are meant to be displayed.
	 * @return The pane holding all of the task panels.
	 */
	private JScrollPane displayTasksPanels(){
		//construct panels
		overallPanel = new JPanel();

		layoutTasks = new GridBagLayout();
		overallPanel.setLayout(layoutTasks);
		
		//create constraint variables
		GridBagConstraints cTask = new GridBagConstraints();

		//Put them in the panel to display
		for(int i = 0; i < taskPanelArray.size(); i ++){
			//Constraints
			cTask.anchor = GridBagConstraints.LINE_START; 
			cTask.gridx = 0;
			cTask.gridy = i;
			cTask.weightx = 0.5;
			cTask.weighty = 0.5;
			cTask.insets = new Insets(5,10,5,0); //top,left,bottom,right
			
			//Hide Closed and Accepted
			taskPanelArray.get(i).setCanDisplay(true);
			
			if(hidden && (list.get(i).getStatus() == TaskStatus.ACCEPTED || list.get(i).getStatus() == TaskStatus.CLOSED)){
				taskPanelArray.get(i).setCanDisplay(false);
			}
			//Hide non-matching names
			if(!contains.equals("") && !list.get(i).getName().contains(contains)){
				taskPanelArray.get(i).setCanDisplay(false);
			}
			//Add the ones that can display.
			if(taskPanelArray.get(i).isCanDisplay()){
				overallPanel.add(taskPanelArray.get(i), cTask);//Put each one in the overallPanel to display them all at once.
			}
		}
		overallPanel.setMaximumSize(getPreferredSize());

		//put overall into a scrollpane
		listScrollPane = new JScrollPane(overallPanel);

		return listScrollPane;
	}

	/**Put in the boxes and other features that go along with the Tasks.
	 * @return 
	 * 
	 */
	private JScrollPane displayFeatures(){
		//constraints
		GridBagConstraints cFeat = new GridBagConstraints();
		GridBagConstraints cTemp = new GridBagConstraints();

		//panels
		featurePanel = new JPanel();
		tempPanel = new JPanel();
		JLabel createLabel = new JLabel("Create a new Task: ", JLabel.TRAILING);
		JLabel containsLabel = new JLabel("Filter by names containing: ", JLabel.TRAILING);
		containsField = new JTextField(20);
		containsField.setMinimumSize(getPreferredSize());
		containsField.setMaximumSize(getPreferredSize());
		containsField.setToolTipText("This search is case-sensitive");
		hideBox = new JCheckBox("Hide Closed and Accepted");

		//Set boxes
		if(hidden)
			hideBox.setSelected(true);
		if(!contains.equals(""))
			containsField.setText(contains);

		//individual panel -------------------
		layoutDisplay = new GridBagLayout();
		tempPanel.setLayout(layoutDisplay);

		cTemp.anchor = GridBagConstraints.LINE_START; 
		cTemp.gridx = 0;
		cTemp.gridy = 0;
		cTemp.weightx = 0.5;
		cTemp.weighty = 0.5;
		cTemp.insets = new Insets(5,10,5,0); //top,left,bottom,right
		tempPanel.add(containsLabel, cTemp);

		cTemp.anchor = GridBagConstraints.LINE_START; 
		cTemp.gridx = 0;
		cTemp.gridy = 1;
		cTemp.weightx = 0.5;
		cTemp.weighty = 0.5;
		cTemp.insets = new Insets(0,10,5,0); //top,left,bottom,right
		tempPanel.add(containsField, cTemp);

		cTemp.anchor = GridBagConstraints.LINE_START; 
		cTemp.gridx = 0;
		cTemp.gridy = 2;
		cTemp.weightx = 0.5;
		cTemp.weighty = 0.5;
		cTemp.insets = new Insets(5,8,5,0); //top,left,bottom,right
		tempPanel.add(hideBox, cTemp);
		
		tempPanel.setMinimumSize(getPreferredSize());

		//Add listeners to the features
		containsField.addKeyListener(new TaskSearchListener(this));
		hideBox.addActionListener(new TaskFeatureListener(this));


		//Add the New Task panel (the panel that allows you to make new tasks).
		layoutDisplayTwo = new GridBagLayout();
		featurePanel.setLayout(layoutDisplayTwo);

		cFeat.anchor = GridBagConstraints.LINE_START;
		cFeat.gridx = 0;
		cFeat.gridy = 0;
		cFeat.weightx = 0.5;
		cFeat.weighty = 0.5;
		cFeat.insets = new Insets(5,0,5,0); //top,left,bottom,right
		featurePanel.add(tempPanel, cFeat);
		
		cFeat.anchor = GridBagConstraints.LINE_START;
		cFeat.gridx = 0;
		cFeat.gridy = 1;
		cFeat.weightx = 0.5;
		cFeat.weighty = 0.5;
		cFeat.insets = new Insets(5,5,0,0); //top,left,bottom,right
		featurePanel.add(createLabel, cFeat);

		cFeat.anchor = GridBagConstraints.LINE_START; 
		cFeat.gridx = 0;
		cFeat.gridy = 2;
		cFeat.weightx = 0.5;
		cFeat.weighty = 0.5;
		cFeat.insets = new Insets(5,5,5,0); //top,left,bottom,right
		featurePanel.add(newTaskPanel, cFeat);//Put each one in the overallPanel to display them all at once.
		
		featScrollPane = new JScrollPane(featurePanel); 
		return featScrollPane;
	}
	
	/**Create the New Task panel on the left side (so we can create new tasks).
	 * 
	 */
	public void createNewTaskPanel(){
		newTaskPanel = new TasksPanel(userNames);
		
		//Make the save button create a new task.
		if(list.size() > 0)
			newTaskPanel.getSaveButton().addActionListener(new CreateTaskListener(list.get(list.size()-1).getId()+1, this));//Make the id 1 higher
		else
			newTaskPanel.getSaveButton().addActionListener(new CreateTaskListener(1, this));//No tasks, start at ID 1.
		
		//Default the save button and status to disabled
		if(newTaskPanel.getTxtName().getText().equals("") || newTaskPanel.getTxtDescription().getText().equals("")){
			newTaskPanel.getSaveButton().setEnabled(false);//Disable the save if the name is blank.
		}
		if(newTaskPanel.getTxtEffort().getText().equals("") || newTaskPanel.getTxtEffort().getText().equals("0")){
			newTaskPanel.getCmbStatus().setEnabled(false);//Disable the status if the effort is not set.
		}


		//Deal with permissions
		if(pLevel == RMPermissionsLevel.NONE || pLevel == RMPermissionsLevel.UPDATE){
			//Gray all of the fields.
			newTaskPanel.getTxtName().setEditable(false);
			newTaskPanel.getTxtDescription().setEditable(false);
			newTaskPanel.getCmbAssignee().setEditable(false);
			newTaskPanel.getTxtEffort().setEditable(false);
			newTaskPanel.getCmbStatus().setEditable(false);
			newTaskPanel.getSaveButton().setEnabled(false);
		}

		//Add listeners to all of the fields.
		newTaskPanel.getTxtName().addKeyListener(new TaskFieldsListener(newTaskPanel, this));
		newTaskPanel.getTxtDescription().addKeyListener(new TaskFieldsListener(newTaskPanel, this));
		newTaskPanel.getCmbAssignee().addActionListener(new TaskDropdownListener(newTaskPanel, this));
		newTaskPanel.getTxtEffort().addKeyListener(new TaskFieldsListener(newTaskPanel, this));
		newTaskPanel.getCmbStatus().addActionListener(new TaskDropdownListener(newTaskPanel, this));

		newTaskPanel.getTxtName().addFocusListener(this);
		newTaskPanel.getTxtDescription().addFocusListener(this);
		newTaskPanel.getCmbAssignee().addFocusListener(this);
		newTaskPanel.getTxtEffort().addFocusListener(this);
		newTaskPanel.getCmbStatus().addFocusListener(this);
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
			taskPanelArray.get(position).setBorder(null);
		}
		//Set the border to show it is different.
		else{
			//Draw the border on the panel that was edited.
			taskPanelArray.get(position).setBorder(BorderFactory.createLineBorder(MainView.getChangedColor(), 5));
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
			taskPanelArray.add(createTaskPanel(t));
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
		list = tasks;
		makeOriginalList(tasks);//Keep track of the tasks in the list.
		//Make the task array panel
		taskPanelArray = new ArrayList<TasksPanel>();
		for(int i = 0; i < list.size(); i ++){
			taskPanelArray.add(createTaskPanel(list.get(i)));
		}
		createNewTaskPanel();
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

	@Override
	public String getTabTitle() {
		return "Tasks";
	}

	@Override
	public ImageIcon getImageIcon() {
		return new ImageIcon();
	}

	@Override
	public String getTooltipText() {
		return "Edit and view tasks";
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

	/**
	 * @return the newTaskPanel
	 */
	public TasksPanel getNewTaskPanel() {
		return newTaskPanel;
	}

	/**
	 * @return the featurePanel
	 */
	public JPanel getFeaturePanel() {
		return featurePanel;
	}

	/**
	 * @return the listScrollPane
	 */
	public JScrollPane getListScrollPane() {
		return listScrollPane;
	}
	
	/**
	 * Refresh backgrounds.
	 */
	public void refreshBackgrounds() {
		parent.getReqModel().updateBackgrounds();
	}

	/* (non-Javadoc)
	 * @see java.awt.event.FocusListener#focusGained(java.awt.event.FocusEvent)
	 */
	@Override
	public void focusGained(FocusEvent e) {
		this.refreshBackgrounds();
	}

	/* (non-Javadoc)
	 * @see java.awt.event.FocusListener#focusLost(java.awt.event.FocusEvent)
	 */
	@Override
	public void focusLost(FocusEvent e) {
		this.refreshBackgrounds();
	}

}
