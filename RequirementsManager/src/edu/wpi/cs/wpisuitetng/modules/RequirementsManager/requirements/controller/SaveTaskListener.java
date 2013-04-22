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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Task;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.tabs.TasksView;

/**
 * The Class SaveTaskController
 *
 * @author Evan Polekoff
 */
public class SaveTaskListener implements ActionListener{
	
	int id;
	TasksView view;
	
	//Constructor
	public SaveTaskListener(int id, TasksView view){
		this.id = id;
		this.view = view;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Save Button Clicked!");
		//Get all of the fields we want.
		Task updated = new Task();
		updated.setId(id);
		
		//Grab what's in the boxes based on the spot in the array and use those as the updated values.
		int position = view.doesTaskExist(id);
		if(position != -1){
			updated.setName(view.getTaskPanelArray().get(position).getTxtName().getText() + " + Oh Hey, it worked!");
			updated.setDescription(view.getTaskPanelArray().get(position).getTxtDescription().getText() + " + Oh Hey, it worked!");
			updated.setAssigneeName(view.getTaskPanelArray().get(position).getTxtAssignee().getText() + " + Oh Hey, it worked!");
			updated.setEffort(Integer.parseInt(view.getTaskPanelArray().get(position).getTxtEffort().getText()) + 1000000);
			System.out.println("ID: "+ id);
			
			//Find the old task and replace it with the new one.
			view.replaceTask(updated);
		}
		else{
			//TODO Does this make sense/do anything?
			view.addTask(updated);
		}
		
		
		//Redisplay stuff so it shows up
		view.redisplay();
	}
	
}
