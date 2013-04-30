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
 *  Tianyu Li
 **************************************************/

package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.action;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.action.RefresherMode;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller.RetrieveAllRequirementsController;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.controller.UpdateAllRequirementsController;

/**
 * Action for update all modified estimate in list view.
 * 
 * @author Tianyu Li
 * 
 * @version Apr 10, 2013
 * 
 */
@SuppressWarnings("serial")
public class UpdateAllRequirementAction extends AbstractAction {

	private final UpdateAllRequirementsController controller;
	private final RetrieveAllRequirementsController refreshController;

	/**
	 * Create a SaveChangesAction.
	 * 
	 * @param controller
	 *            When the action is performed, controller.save will be called
	 */
	public UpdateAllRequirementAction(UpdateAllRequirementsController controller) {
		super("Update");
		this.controller = controller;
		refreshController = new RetrieveAllRequirementsController(
				RefresherMode.TREE);
		putValue(MNEMONIC_KEY, KeyEvent.VK_U);
	}

	/**
	 * Calls the controller to save the action.
	 * 
	 * @param arg0
	 *            The action event to perform
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		controller.update();
	}
}