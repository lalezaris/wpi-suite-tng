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
 *  Mike Perrone
 *  Chris Hanna
 *  
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.RetrieveAllRequirementsController;

/**
 * The action that is called by the refresh button that sends a message 
 * to the RetrieveAllRequirementsController.
 *
 * @author Tianyu Li
 *
 * @version Mar 21, 2013
 *
 */
public class RefreshAction extends AbstractAction {

	private final RetrieveAllRequirementsController controller;
	
	public RefreshAction(RetrieveAllRequirementsController controller){
		super("Refresh");
		this.controller = controller;
		putValue(MNEMONIC_KEY, KeyEvent.VK_S);
	}
	
	/* Action performed by controller to refresh data
	 * @param e ActionEvent to perform refresh of data
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		controller.refreshData();
	}

}
