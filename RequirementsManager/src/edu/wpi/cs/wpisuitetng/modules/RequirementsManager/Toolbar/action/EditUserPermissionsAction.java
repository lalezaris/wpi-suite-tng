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
 *  Chris Dunkers
 **************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Toolbar.action;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RMPermissionsLevel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.observers.CurrentUserPermissions;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.controller.MainTabController;

/**
 * Action to edit user permissions.
 * 
 * @author Chris Dunkers
 *
 * @version Apr 1, 2013
 *
 */
@SuppressWarnings("serial")
public class EditUserPermissionsAction extends AbstractAction {

	private final MainTabController controller;

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(CurrentUserPermissions.doesUserHavePermissionMaster(RMPermissionsLevel.ADMIN)){
			controller.addEditUserPermissionsTab();
			CurrentUserPermissions.updateCurrentUserPermissions();
		}
	}

	/**
	 * Default constructor.
	 * 
	 * @param controller The MainTabController
	 */
	public EditUserPermissionsAction(MainTabController controller) {
		super("Edit");
		this.controller = controller;
	}
}