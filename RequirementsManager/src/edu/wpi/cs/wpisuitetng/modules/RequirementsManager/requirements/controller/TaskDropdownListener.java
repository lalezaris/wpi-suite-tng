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

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.tabs.TasksView;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tasks.TasksPanel;

/**
 * Listen on the dropdowns in the task (Assignee, Status)
 *
 * @author Evan Polekoff
 */
public class TaskDropdownListener implements ActionListener{

	TasksView view;
	TasksPanel panel;
	
	/**
	 * Instantiates a new task dropdown listener.
	 *
	 * @param panel the panel
	 * @param view the view
	 */
	public TaskDropdownListener(TasksPanel panel, TasksView view){
		this.panel = panel;
		this.view = view;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(!panel.equals(view.getNewTaskPanel())){
			if(e.getSource().equals(panel.getCmbStatus())){
				panel.getCmbStatus().setBackground(new Color(255, 252, 132));
			}
			
			if(e.getSource().equals(panel.getCmbAssignee())){
				panel.getCmbAssignee().setBackground(new Color(255, 252, 132));
			}
		}
	}
	
}
