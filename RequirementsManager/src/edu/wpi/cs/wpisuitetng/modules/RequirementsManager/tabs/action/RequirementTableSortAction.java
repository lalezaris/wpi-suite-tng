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

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.controller.RequirementTableSortController;

/**
 * Action to sort requirements table
 *
 * @author Michael Perrone
 * @version Apr 14, 2013
 */
public class RequirementTableSortAction extends MouseAdapter {

	private RequirementTableSortController rtsController;

	/**
	 * Instantiates a new requirement table sort action.
	 *
	 * @param rtsController the requirement table sort controller
	 */
	public RequirementTableSortAction(RequirementTableSortController rtsController){
		this.rtsController = rtsController;
	}

	/**
	 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent e){
		(rtsController).sortTable(e.getPoint());
	}

}
