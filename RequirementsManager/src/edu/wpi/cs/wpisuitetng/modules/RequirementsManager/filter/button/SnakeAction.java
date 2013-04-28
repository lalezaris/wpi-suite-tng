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
 *  Chris Hanna
 **************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.filter.button;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.controller.MainTabController;


/**
 * The Class SnakeAction.
 */
public class SnakeAction extends AbstractAction{

	/**
	 * Instantiates a new snake action.
	 */
	public SnakeAction() {
		super("Om Nom Nom");
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		MainTabController.getController().addSnakeTab();
	}

}
