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

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.tabs.TasksView;

/**
 * Listen for strings to search for
 * 
 * @author Evan Polekoff
 * 
 */
public class TaskSearchListener implements KeyListener{
	TasksView view;
	
	public TaskSearchListener(TasksView view){
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
		view.setContains(view.getContainsField().getText());
		view.redisplay();
		view.getContainsField().requestFocusInWindow();//Keep typing in the same field.
	}
	
}