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
	Tyler Stone
**************************************************/

package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.SaveRequirementController;

/**
 * Adapted from JPage
 * Action that calls {@link SaveRequirementController#save}, default mnemonic key is S.
 *	
 * @author Tyler Stone
 *
 * @version Mar 20, 2013
 *
 */
@SuppressWarnings("serial")
public class SaveChangesAction extends AbstractAction {

	private final SaveRequirementController controller;
	
	/**
	 * Create a SaveChangesAction
	 * @param controller When the action is performed, controller.save will be called
	 */
	public SaveChangesAction(SaveRequirementController controller) {
		super("Save Changes");
		this.controller = controller;
		putValue(MNEMONIC_KEY, KeyEvent.VK_S);
	}
	
	/* 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		controller.save();
	}

}
