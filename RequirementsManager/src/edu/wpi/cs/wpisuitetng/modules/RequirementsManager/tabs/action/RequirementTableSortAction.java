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
 *  "Michael Perrone"
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.action;


import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.controller.RequirementTableSortController;

/**
 * Insert Description Here
 *
 * @author "Michael Perrone"
 *
 * @version Apr 14, 2013
 *
 */
public class RequirementTableSortAction extends MouseAdapter {

	private RequirementTableSortController rtsController;
	
	public RequirementTableSortAction(RequirementTableSortController rtsController){
		this.rtsController = rtsController;
	}

	@Override
	public void mouseClicked(MouseEvent e){
		((RequirementTableSortController) rtsController).sortTable(e.getPoint());
	}

}
