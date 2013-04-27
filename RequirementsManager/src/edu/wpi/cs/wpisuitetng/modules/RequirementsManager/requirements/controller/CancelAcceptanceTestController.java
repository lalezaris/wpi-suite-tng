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
 *  mwfrench
 *  Joe Spicola
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.tabs.AcceptanceTestsView;

/**
 * The Class AddAcceptanceTestController.
 * 
 * @author Joe Spicola
 */
public class CancelAcceptanceTestController implements ActionListener {
	
	private final AcceptanceTestsView view;

	/**
	 * Default constructor
	 * 
	 * @param view
	 */
	public CancelAcceptanceTestController(AcceptanceTestsView view) {
		this.view = view;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		view.clearTitleTxt();
		view.toggleTitleEnabled(true);
		view.clearBodyTxt();
		view.clearStatusCmb();
		view.refreshBackgrounds();
	}
	
}
