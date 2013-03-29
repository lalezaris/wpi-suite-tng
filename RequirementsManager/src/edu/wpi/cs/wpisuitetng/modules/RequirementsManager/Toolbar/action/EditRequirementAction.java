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
 *  Arica Liu
**************************************************/

package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Toolbar.action;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.controller.MainTabController;

/**
 * Action that calls {@link MainTabController#addEditRequirementTab()}, default mnemonic key is E.
 * 
 * Adapted from SearchDefectsAction form the project DefectTracker
 * @author Arica liu
 * 
 * @version March 18, 2013
 */
@SuppressWarnings("serial")
public class EditRequirementAction extends AbstractAction {

	private final MainTabController controller;
	private final Requirement requirement;
	/**
	 * Create a EditRequirementAction
	 * @param controller When the action is performed, controller.addNewRequirementTab() is called
	 */
	public EditRequirementAction(MainTabController controller, Requirement requirement) {
		super("Edit");
		this.controller = controller;
		this.requirement = requirement;
		putValue(MNEMONIC_KEY, KeyEvent.VK_E);
	}
	
	/* 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		controller.addEditRequirementTab(requirement);
	}

}
