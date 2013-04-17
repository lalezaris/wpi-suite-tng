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
 *  Tianyu Li
 *  Mike Perrone
 *  Chris Hanna
 *  
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Toolbar.action;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.controller.MainTabController;

/**
 * The action that the "List" button is registered to. 
 * This switches the current tab to the list of all requirements tab
 *
 * @author Tianyu Li
 *
 * @version Mar 21, 2013
 *
 */
@SuppressWarnings("serial")
public class ListAction extends AbstractAction {
	private final MainTabController controller;
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		controller.addListRequirementTab();
	}

	/**
	 * Default constructor.
	 * 
	 * @param controller
	 */
	public ListAction(MainTabController controller) {
		super("List Requirements");
		
		System.out.println("Entered ListAction");
		this.controller = controller;
		
		putValue(MNEMONIC_KEY, KeyEvent.VK_N);
	}
}