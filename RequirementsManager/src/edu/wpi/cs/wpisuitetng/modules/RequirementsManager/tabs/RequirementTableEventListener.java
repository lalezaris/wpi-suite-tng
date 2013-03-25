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
 *  Tyler
**************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs;

import java.awt.Point;
import java.awt.event.MouseAdapter;

import javax.swing.JTable;

/**
 * A mouse listener for the requirement table on the dashboard
 *
 * @author Tyler Stone
 *
 * @version Mar 24, 2013
 *
 */
public class RequirementTableEventListener extends MouseAdapter {
	RequirementListPanel requirementListPanel;
	
	public RequirementTableEventListener(RequirementListPanel requirementListPanel) { 
		this.requirementListPanel = requirementListPanel;
	}
	
	@Override
    public void mouseClicked(java.awt.event.MouseEvent evt) {
        int row = requirementListPanel.getTable().rowAtPoint(evt.getPoint());
   
        if (row >= 0) {
        	//reqID goes from object > string > int
            int reqID = Integer.parseInt( requirementListPanel.getTable().getValueAt(row, 0).toString() );
            //send back to the panel
            requirementListPanel.setSelectedRequirement(reqID);
        }
    }

}
