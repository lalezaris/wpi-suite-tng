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
 *  Lauren Kahn
 **************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Toolbar.action;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Iteration;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.controller.MainTabController;

/**
 * Action that calls {@link MainTabController#addEditIterationab()}, default mnemonic key is E.
 *
 * @author Lauren Kahn
 *
 * @version Apr 8, 2013
 *
 */
@SuppressWarnings("serial")
public class EditIterationsAction extends AbstractAction {
	private final MainTabController controller;
	private final Iteration iteration;
	/**
	 * Create a EditIterationAction.
	 * 
	 * @param controller When the action is performed, controller.addNewRequirementTab() is called
	 */
	public EditIterationsAction(MainTabController controller, Iteration iteration) {
		super("Edit");
		this.controller = controller;
		this.iteration = iteration;
		putValue(MNEMONIC_KEY, KeyEvent.VK_E);
	}

	/* 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		controller.addEditIterationTab(iteration);
	}
}