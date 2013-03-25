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
 *  Chris Dunkers
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Insert Description Here
 *
 * @author Chris Dunkers
 *
 * @version Mar 25, 2013
 *
 */
public class NotesView extends JPanel {
	public NotesView() {
		JLabel notesLabel = new JLabel("Notes                                                                                               ");
		this.add(notesLabel);
	}
}
