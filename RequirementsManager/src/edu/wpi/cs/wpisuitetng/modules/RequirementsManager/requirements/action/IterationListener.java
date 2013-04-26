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
 *  Ned Shelton
 **************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

/**
 * Listen for an action performed within the iteration combo box.
 * 
 * @author Ned Shelton
 *
 */
public class IterationListener implements ActionListener {

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void actionPerformed(ActionEvent iterations) {
		JComboBox cb = (JComboBox)iterations.getSource();
	}
}
