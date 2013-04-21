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
 *  Michael Perrone
 **************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.action;


import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.controller.IterationTableSortController;

/**
 * Action to sort the iteration table
 *
 * @author Michael Perrone
 *
 * @version Apr 16, 2013
 *
 */
public class IterationTableSortAction extends MouseAdapter {

	private IterationTableSortController itsController;

	/**
	 * Instantiates a new iteration table sort action.
	 *
	 * @param itsController the iteration table sort controller
	 */
	public IterationTableSortAction(IterationTableSortController itsController){
		this.itsController = itsController;
	}

	/**
	 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent e){
		((IterationTableSortController) itsController).sortTable(e.getPoint());
	}

}
