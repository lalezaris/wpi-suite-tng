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
 *  
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.Toolbar.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Iteration;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.action.Refresher;

/**
 * The action that the "List Iterations" button is registered to. 
 * 
 * @author Chris Hanna
 */
public class ListIterationAction extends AbstractAction {
	/**
	 * Constructor for ListIterationAction
	 * 
	 * @param n The button name
	 */
	public ListIterationAction(String n){
		super(n);
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Iteration[] i = new Iteration[0];
		i = Refresher.getInstance().getInstantIterations(); 
	}
}
