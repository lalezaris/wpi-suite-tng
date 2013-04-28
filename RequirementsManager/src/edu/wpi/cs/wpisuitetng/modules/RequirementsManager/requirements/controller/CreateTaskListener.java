/**
 * This file was developed for CS3733: Software Engineering
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Evan Polekoff
 */
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Task;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.TaskStatus;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.tabs.TasksView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tasks.TasksPanel;

/**
 * Creates a new Task.
 *
 * @author Evan Polekoff
 */
public class CreateTaskListener implements ActionListener{

	int id;
	TasksView view;
	
	/**
	 * @param id The id of the task to create.
	 * @param view 
	 */
	public CreateTaskListener(int id, TasksView view){
		this.id = id;
		this.view = view;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//New Task
			
		if(readyToSave(view.getNewTaskPanel())){
			view.addTask(makeTask(view.getNewTaskPanel()));
			view.placeBorder(view.getTaskPanelArray().size() -1);//Place border on this new one.
			view.createNewTaskPanel();
			view.redisplay();
			view.revalidate();
		}
		
	}
	
	/**Make a check of the fields to see if it is ok to save. Some fields are required.
	 * @param position Where to look for the fields (which panel)
	 * @return Whether you can save or not (based on Name, Description or other required fields)
	 */
	private boolean readyToSave(TasksPanel oldPanel){
		boolean ready = true;
		
		//Check the Name field
		if(oldPanel.getTxtName().getText().equals("")){
			ready = false;
			oldPanel.getTxtName().setBackground(new Color(255, 155, 157));//Red
		}
		else{//Set color back
			oldPanel.getTxtName().setBackground(Color.white);
		}
		
		//Check Description Field
		if(oldPanel.getTxtDescription().getText().equals("")){
			ready = false;
			oldPanel.getTxtDescription().setBackground(new Color(255, 155, 157));//Red
		}
		else{//Set color back
			oldPanel.getTxtDescription().setBackground(Color.white);
		}
		return ready;
	}
	
	/**A helper to get the fields from the panels for tasks.
	 * @param position Where to look for the fields (which panel)
	 * @return The new task made from the fields.
	 */
	private Task makeTask(TasksPanel oldPanel){
		Task updated = new Task();
		updated.setId(id);
		updated.setName(oldPanel.getTxtName().getText());
		updated.setDescription(oldPanel.getTxtDescription().getText());
		updated.setAssigneeName(oldPanel.getCmbAssignee().getSelectedItem().toString());
		String tempEffort = oldPanel.getTxtEffort().getText();
		if(!tempEffort.equals(""))
			updated.setEffort(Integer.parseInt(tempEffort));
		updated.setStatus((TaskStatus)oldPanel.getCmbStatus().getSelectedItem());	

		return updated;
	}
	
}
