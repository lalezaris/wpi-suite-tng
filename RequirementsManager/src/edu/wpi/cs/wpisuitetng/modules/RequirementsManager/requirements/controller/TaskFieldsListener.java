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

import javax.swing.Timer;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.tabs.TasksView;

/**
 * The class that listens on all of the fields and validates the task based on what is in them.
 * 
 * @author Evan Polekoff
 * 
 */
public class TaskFieldsListener extends Timer implements ActionListener{

	TasksView view;
	
	public TaskFieldsListener(TasksView view){
		super(100, null);
		
		this.view = view;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//Iterate over every panel.
		System.out.println("Tick...");
		for(int i = 0; i < view.getTaskPanelArray().size(); i ++){
			//Check Name Field
			if(view.getTaskPanelArray().get(i).getTxtName().equals("")){
				view.getTaskPanelArray().get(i).getSaveButton().setEnabled(false);//Disable the save if the name is blank.
			}
			//Check Description Field
			if(view.getTaskPanelArray().get(i).getTxtDescription().equals("")){
				view.getTaskPanelArray().get(i).getSaveButton().setEnabled(false);//Disable the save if the name is blank.
			}
			//Check the effort
			//Check Name Field
			if(view.getTaskPanelArray().get(i).getTxtEffort().equals("") || view.getTaskPanelArray().get(i).getTxtEffort().equals("0")){
				view.getTaskPanelArray().get(i).getCmbStatus().setEnabled(false);//Disable the status if the effort is not set.
			}
		}
		
	}
	
}
