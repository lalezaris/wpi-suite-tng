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
 *  Lauren Kahn
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Toolbar.action;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Iteration;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RMPermissionsLevel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.action.Refresher;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.observers.CurrentUserPermissions;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.controller.MainTabController;

/**
 * The action that the "List Iterations" button is registered to. 
 * 
 * @author Chris Hanna
 */
@SuppressWarnings("serial")
public class ListIterationAction extends AbstractAction {
	
	private final MainTabController controller;

	/**
	 * Constructor for ListIterationAction
	 * 
	 * @param n The button name
	 */
	public ListIterationAction(MainTabController controller){
		super("List Iterations");
		this.controller = controller;
		putValue(MNEMONIC_KEY, KeyEvent.VK_I);
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Iteration[] i = new Iteration[0];
		i = Refresher.getInstance().getInstantIterations(); 
		if (CurrentUserPermissions.doesUserHavePermissionLocal(RMPermissionsLevel.ADMIN)){
			controller.addListIterationTab(); //null, IterationPanel.Mode.CREATE
		}
	}
}
