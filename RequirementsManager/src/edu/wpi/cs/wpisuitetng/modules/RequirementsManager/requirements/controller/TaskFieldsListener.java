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
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller;


import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.tabs.TasksView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tasks.TasksPanel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.view.MainView;

/**
 * The class that listens on all of the fields and validates the task based on what is in them.
 * 
 * @author Evan Polekoff
 * 
 */
public class TaskFieldsListener implements KeyListener{

	TasksPanel panel;
	TasksView view;
	
	/**
	 * Instantiates a new task fields listener.
	 *
	 * @param panel the panel
	 * @param view the view
	 */
	public TaskFieldsListener(TasksPanel panel, TasksView view){
		this.panel = panel;
		this.view = view;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		//Check Name and Description Fields
		if(panel.getTxtName().getText().equals("") || panel.getTxtDescription().getText().equals("")){
			panel.getSaveButton().setEnabled(false);//Disable the save if the name is blank.
		}
		else{
			panel.getSaveButton().setEnabled(true);
		}
		//Check the effort
		//Check Name Field
		if(panel.getTxtEffort().getText().equals("") || panel.getTxtEffort().getText().equals("0")){
			panel.getCmbStatus().setEnabled(false);//Disable the status if the effort is not set.
			panel.getCmbStatus().setSelectedIndex(0);//Default back to blank.
		}
		else{
			panel.getCmbStatus().setEnabled(true);
		}
		
		//Yellowing
		if(!panel.equals(view.getNewTaskPanel())){
			if(!e.getComponent().equals(panel.getCmbAssignee()) && !e.getComponent().equals(panel.getCmbStatus())){
				e.getComponent().setBackground(MainView.getChangedColor());
				view.setChanged(true);
			}
		}
	}
	
}
