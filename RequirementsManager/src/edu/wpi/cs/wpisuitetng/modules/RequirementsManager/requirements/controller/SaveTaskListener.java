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
 * The Class SaveTaskController
 *
 * @author Evan Polekoff
 */
public class SaveTaskListener implements ActionListener{
	
	int id;
	TasksView view;
	
	/**
	 * Instantiates a new save task listener.
	 *
	 * @param id the id
	 * @param view the view
	 */
	public SaveTaskListener(int id, TasksView view){
		this.id = id;
		this.view = view;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		int position = view.doesTaskExist(id);
		boolean changeMade = false;
		//Grab what's in the boxes based on the spot in the array and use those as the updated values.
		if(position != -1){
			//Find the old task and replace it with the new one.
			if(readyToSave(view.getTaskPanelArray().get(position))){
				view.replaceTask(makeTask(view.getTaskPanelArray().get(position)));
				
				view.placeBorder(position);
				view.getListScrollPane().repaint();
				view.getListScrollPane().revalidate();
			}
		}
		//New Task
		else{//If the task does not exist, make a new one.
			System.out.println("ERROR: This situaion should not happen!");
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
