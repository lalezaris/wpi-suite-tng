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
 *  CDUNKERS
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Toolbar.action;

import java.awt.event.ActionEvent;

import org.gpl.JSplitButton.action.SplitButtonActionListener;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.controller.MainTabController;

/**
 * Insert Description Here
 *
 * @author CDUNKERS
 *
 * @version Apr 20, 2013
 *
 */
public class ListSplitListener implements SplitButtonActionListener {

	private final MainTabController controller;
	
	public ListSplitListener(MainTabController controller){
		this.controller = controller;
	}
	
	/* (non-Javadoc)
	 * @see org.gpl.JSplitButton.action.SplitButtonActionListener#buttonClicked(java.awt.event.ActionEvent)
	 */
	@Override
	public void buttonClicked(ActionEvent arg0) {
		// TODO Auto-generated method stub
		controller.addListRequirementTab();
	}

	/* (non-Javadoc)
	 * @see org.gpl.JSplitButton.action.SplitButtonActionListener#splitButtonClicked(java.awt.event.ActionEvent)
	 */
	@Override
	public void splitButtonClicked(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}
	
}