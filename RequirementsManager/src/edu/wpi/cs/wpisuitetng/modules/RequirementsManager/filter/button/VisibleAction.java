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
 **************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.filter.button;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.filter.FilterController;

/**
 * The action that happens when you toggle visibility of filters
 *
 * @author Chris Hanna
 *
 * @version Apr 17, 2013
 *
 */
@SuppressWarnings("serial")
public class VisibleAction extends AbstractAction{

	FilterController controller;

	/**
	 * Make a new visibility action.
	 *
	 * @param controller the controller
	 */
	public VisibleAction(FilterController controller) {
		super("Show Filters");
		this.controller = controller;
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		controller.getPanel().toggleVisibility();

	}

}
