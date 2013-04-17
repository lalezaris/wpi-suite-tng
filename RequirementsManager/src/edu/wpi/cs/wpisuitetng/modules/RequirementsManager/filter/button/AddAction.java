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
 *  Chris
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.filter.button;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Icon;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.filter.FilterController;

/**
 * The action to add a new rule to the filter
 *
 * @author Chris Hanna
 *
 * @version Apr 16, 2013
 *
 */
public class AddAction extends AbstractAction {

	FilterController controller;
	
	
	/**
	 * Make a new AddAction
	 * 
	 */
	public AddAction(FilterController controller) {
		super("Add Filter");
		this.controller = controller;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		this.controller.getPanel().addRule();
	}

}
