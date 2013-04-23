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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.tabs.TasksView;

/**
 * Listens on the feature boxes for tasks.
 * 
 * @author Evan Polekoff
 * 
 */
public class TaskFeatureListener implements ActionListener{

	TasksView view;
	
	public TaskFeatureListener(TasksView view){
		this.view = view;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//Hide Done or Accepted
		if(view.getHideBox().isSelected()){
			view.setHidden(true);
		}
		else{
			view.setHidden(false);
		}
		
		//Sort Alphabetically
		if(view.getSortBox().isSelected()){
			view.setSorted(true);
		}
		else{
			view.setSorted(false);
		}
		
		view.redisplay();
		
	}
	
}
