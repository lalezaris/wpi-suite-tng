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
		int position = view.doesTaskExist(id);
		boolean changeMade = false;
		//Grab what's in the boxes based on the spot in the array and use those as the updated values.
		if(position != -1){
			//Find the old task and replace it with the new one.
			if(readyToSave(position)){
				view.replaceTask(makeTask(position));
				changeMade = true;//Let them know it is OK to refresh.
			}
		}
		else{//If the task does not exist, make a new one.
			position = view.getTaskPanelArray().size()-1;//Take fields from the last panel.
			
			if(position >= 0 && readyToSave(position)){
				view.addTask(makeTask(position));
				changeMade = true;//Let them know it is OK to refresh.
			}
		}
		//Redisplay stuff so it shows up
		if(changeMade){//Only refresh if something was changed. Otherwise, it would kill the fields.
			view.redisplay();
			for(int i = 0; i < view.getTaskPanelArray().size(); i++){
				view.getTaskPanelArray().get(i).setOpaque(true);
				view.getTaskPanelArray().get(i).setForeground(new Color(255, 155, 157));//Make the whole panel yellow instead.
				view.getTaskPanelArray().get(i).setBackground(new Color(255, 155, 157));//Make the whole panel yellow instead.
				view.getTaskPanelArray().get(i).repaint();
				view.getTaskPanelArray().get(i).revalidate();
			}
		}
	}
	
	/**Make a check of the fields to see if it is ok to save. Some fields are required.
	 * @param position Where to look for the fields (which panel)
	 * @return Whether you can save or not (based on Name, Description or other required fields)
	 */
	private boolean readyToSave(int position){
		boolean ready = true;
		
		//Check the Name field
		if(view.getTaskPanelArray().get(position).getTxtName().getText().equals("")){
			ready = false;
			view.getTaskPanelArray().get(position).getTxtName().setBackground(new Color(255, 155, 157));
		}
		else{//Set color back
			view.getTaskPanelArray().get(position).getTxtName().setBackground(Color.white);
		}
		
		//Check Description Field
		if(view.getTaskPanelArray().get(position).getTxtDescription().getText().equals("")){
			ready = false;
			view.getTaskPanelArray().get(position).getTxtDescription().setBackground(new Color(255, 155, 157));
		}
		else{//Set color back
			view.getTaskPanelArray().get(position).getTxtDescription().setBackground(Color.white);
		}
		return ready;
	}
	
	/**A helper to get the fields from the panels for tasks.
	 * @param position Where to look for the fields (which panel)
	 * @return The new task made from the fields.
	 */
	private Task makeTask(int position){
		Task updated = new Task();
		updated.setId(id);
		updated.setName(view.getTaskPanelArray().get(position).getTxtName().getText());
		updated.setDescription(view.getTaskPanelArray().get(position).getTxtDescription().getText());
		updated.setAssigneeName(view.getTaskPanelArray().get(position).getTxtAssignee().getText());
		String tempEffort = view.getTaskPanelArray().get(position).getTxtEffort().getText();
		if(!tempEffort.equals(""))
			updated.setEffort(Integer.parseInt(tempEffort));
		updated.setStatus((TaskStatus)view.getTaskPanelArray().get(position).getCmbStatus().getSelectedItem());	
		return updated;
	}
	
}
