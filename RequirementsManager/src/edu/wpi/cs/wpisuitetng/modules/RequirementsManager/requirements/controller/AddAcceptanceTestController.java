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
 *  Michael French
 **************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.AcceptanceTest;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.tabs.AcceptanceTestsView;

/**
 * The Class AddAcceptanceTestController.
 * 
 * @author Michael French
 */
public class AddAcceptanceTestController implements ActionListener {

	private final AcceptanceTestsView view;

	/**
	 * Default constructor
	 * 
	 * @param view the view for acceptance tests
	 */
	public AddAcceptanceTestController(AcceptanceTestsView view) {
		this.view = view;
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(!view.notReady()){
			AcceptanceTest a = new AcceptanceTest(view.getTitleTxt(), view.getBodyTxt());
			view.addTestToList(a);
			view.updateList();
			view.clearTitleTxt();
			view.clearBodyTxt();
			view.refreshBackgrounds();
		}		
	}

}
