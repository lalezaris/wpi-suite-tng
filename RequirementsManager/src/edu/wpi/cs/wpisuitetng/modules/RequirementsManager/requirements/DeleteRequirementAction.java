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
 *  CDUNKERS
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractAction;

import javax.swing.Action;

/**
 * Insert Description Here
 *
 * @author CDUNKERS
 *
 * @version Mar 27, 2013
 *
 */
public class DeleteRequirementAction extends AbstractAction {

	private final DeleteRequirementController controller;
	
	/**
	 * Create a SaveChangesAction
	 * @param controller When the action is performed, controller.save will be called
	 */
	public DeleteRequirementAction(DeleteRequirementController controller) {
		super("Delete");
		this.controller = controller;
		//putValue(MNEMONIC_KEY, KeyEvent.VK_S);
	}
	
	/* 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		controller.delete();
	}

}
