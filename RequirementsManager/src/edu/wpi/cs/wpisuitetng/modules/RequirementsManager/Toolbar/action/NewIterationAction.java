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
 *  Tyler Stone
**************************************************/

package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Toolbar.action;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RMPermissionsLevel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.observers.CurrentUserPermissions;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.controller.MainTabController;

/**
 * Action that calls {@link MainTabController#addNewRequirementTab()}, default mnemonic key is I.
 * Adapted from CreateDefectAction in the project Defect Tracker.
 * 
 * @author Lauren Kahn
 * 
 * @version April 8, 2013
 */
@SuppressWarnings("serial")
public class NewIterationAction extends AbstractAction {

	private final MainTabController controller;
	
	/**
	 * Create a NewIterationAction.
	 * 
	 * @param controller When the action is performed, controller.addNewIterationTab() is called
	 */
	public NewIterationAction(MainTabController controller) {
		super("Create Iteration");
		this.controller = controller;
		putValue(MNEMONIC_KEY, KeyEvent.VK_I);
	}
	
	/* 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (CurrentUserPermissions.doesUserHavePermissionLocal(RMPermissionsLevel.ADMIN)){
			controller.addNewIterationTab(); //null, IterationPanel.Mode.CREATE
		}
	}
}