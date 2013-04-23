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

import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;


import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Task;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RMPermissionsLevel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.TaskStatus;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RequirementView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.SaveTaskListener;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.TaskFeatureListener;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.TaskFieldsListener;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.observers.CurrentUserPermissions;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tasks.TasksPanel;

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
	private JPanel featurePanel;
	private JPanel overallPanel;//One panel to hold them all.
	private JPanel scrollingPanel;
	private DefaultListModel<Task> listModel;
	private JList listDisplay;
	
	private JCheckBox sortBox;
	private JCheckBox hideBox;
	
	//State Variables
	private boolean hidden;
	private boolean sorted;
	
	/** The layout manager for this panel */
	protected GridBagLayout layout;

	//Permissions level
	protected RMPermissionsLevel pLevel;


	/**
	 * Instantiates a new tasks view.
	 *
	 * @param req the requirement holding the tasks
	 */
	public TasksView(RequirementView parent) {
		super(new BorderLayout());
		
		//Set initial variables
		if(list == null)//Only reset and initialize the list if it is new.
			list = new ArrayList<Task>();
		
		//Get the ScrollPane going.
		listModel = new DefaultListModel<Task>();
		
		//Get permissions
		this.pLevel = CurrentUserPermissions.getCurrentUserPermission();
		//Use a grid bag layout manager
		layout = new GridBagLayout();
		this.setLayout(layout);
		
		this.parent = parent;
		
		//Create the stuff for other features, like the sorting and hiding
		displayFeatures();
		
		//Create all of the panels(one per task) and put them in the array.
		createTasksPanels();

		//JList
		//listDisplay = new JList(listModel);
		//listDisplay.setLayoutOrientation(JList.VERTICAL);
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
			cTask.gridy = i*100;
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
			
			//Add listeners to all of the fields.
			tempPanel.getTxtName().addKeyListener(new TaskFieldsListener(tempPanel));
			tempPanel.getTxtDescription().addKeyListener(new TaskFieldsListener(tempPanel));
			tempPanel.getTxtAssignee().addKeyListener(new TaskFieldsListener(tempPanel));
			tempPanel.getTxtEffort().addKeyListener(new TaskFieldsListener(tempPanel));
		
			
			
			//Put it in the array and panel.\
			if(!hidden || i == list.size() || (hidden && (list.get(i).getStatus() != TaskStatus.ACCEPTED && list.get(i).getStatus() != TaskStatus.CLOSED))){//Don't show these if hidden is selected.
				taskPanelArray.add(tempPanel);
				overallPanel.add(tempPanel, cTask);//Put each one in the overallPanel to display them all at once.
			}
		}
		
		//Put the panels (overallPanel) into a scrollpane
		cScrolling.anchor = GridBagConstraints.FIRST_LINE_START; 
		cScrolling.fill = GridBagConstraints.HORIZONTAL;
		cScrolling.gridx = 1;
		cScrolling.gridy = 0;
		cScrolling.weightx = 0.5;
		cScrolling.weighty = 0.5;
		cScrolling.gridheight = 1;
		cScrolling.insets = new Insets(10,10,10,0); //top,left,bottom,right
		JScrollPane listScrollPane = new JScrollPane(overallPanel);
		scrollingPanel = new JPanel();
		scrollingPanel.setLayout(new GridBagLayout());
		scrollingPanel.add(listScrollPane);
		
		this.add(scrollingPanel, cScrolling);
	}
	
	/**Put in the boxes and other features that go along with the Tasks.
	 * 
	 */
	private void displayFeatures(){
		sortBox = new JCheckBox("Sort Alphabetically");
		hideBox = new JCheckBox("Hide Closed and Accepted");
		
		//Set boxes
		if(hidden)
			hideBox.setSelected(true);
		if(sorted)
			sortBox.setSelected(true);
		
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
		featurePanel.add(sortBox, cFeat);
		
		cFeat.anchor = GridBagConstraints.FIRST_LINE_START; 
		cFeat.fill = GridBagConstraints.HORIZONTAL;
		cFeat.gridx = 0;
		cFeat.gridy = 1;
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
		
		this.add(featurePanel, cOverall);
		
		//Add listeners to the features
		sortBox.addActionListener(new TaskFeatureListener(this));
		hideBox.addActionListener(new TaskFeatureListener(this));
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
		for(int i = 0; i < list.size(); i++){
			if(!listModel.contains(list.get(i))){
				listModel.add(i, list.get(i));}
		}
	}
	
	/**Used to update a given Task
	 * @param t A task with the same ID as the ne you want to replace but with new fields.
	 */
	public void replaceTask(Task t){
		System.out.println("Searching for "+ t.getId() + " with name \'" + t.getName() + "\'");
		System.out.println("List size: "+ list.size());
		boolean hasTask = false;
		int taskLocation = doesTaskExist(t.getId());
		if(taskLocation != -1)
			hasTask = true;
		//Update all of the fields.
		if (hasTask){
			System.out.println("Found "+ t.getId() + " with name /'" + t.getName() + "/'");
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

	
	/**Redisplay everything. Call after updating tasks.
	 * 
	 */
	public void redisplay(){
		this.removeAll();
		
		displayFeatures();
		createTasksPanels();
		repaint();
		revalidate();
	}

	/**
	 * @return the taskPanelArray
	 */
	public ArrayList<TasksPanel> getTaskPanelArray() {
		return taskPanelArray;
	}

	/**
	 * @return the sortBox
	 */
	public JCheckBox getSortBox() {
		return sortBox;
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
	 * @param sorted the sorted to set
	 */
	public void setSorted(boolean sorted) {
		this.sorted = sorted;
	}
	
}
