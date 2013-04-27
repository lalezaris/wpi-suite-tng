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

// TODO: Auto-generated Javadoc
/**
 * Listen for strings to search for.
 *
 * @author Evan Polekoff
 */
public class TaskSearchListener implements KeyListener{
	TasksView view;
	
	/**
	 * Instantiates a new task search listener.
	 *
	 * @param view the view
	 */
	public TaskSearchListener(TasksView view){
		this.view = view;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		view.redisplayRight();
		view.getContainsField().requestFocusInWindow();//Keep typing in the same field.
	}
	
}