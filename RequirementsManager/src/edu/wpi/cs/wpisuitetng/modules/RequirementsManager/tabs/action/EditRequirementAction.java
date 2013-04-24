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

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RMPermissionsLevel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.observers.CurrentUserPermissions;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.controller.MainTabController;

/**
 * @author Tianyu Li
 *
 */
public class EditRequirementAction extends AbstractAction {
	private MainTabController controller;
	private Requirement req;
	
	public EditRequirementAction(MainTabController controller, Requirement req) {
		this.controller = controller;
		this.req = req;
		putValue(MNEMONIC_KEY, KeyEvent.VK_R);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (CurrentUserPermissions.doesUserHavePermissionLocal(RMPermissionsLevel.ADMIN)){
			controller.addEditRequirementTab(req);
		}
	}

}
