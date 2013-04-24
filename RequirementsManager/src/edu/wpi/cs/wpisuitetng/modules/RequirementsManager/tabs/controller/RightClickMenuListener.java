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
 * Tianyu Li
**************************************************/

package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.controller;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.RequirementListPanel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.model.RequirementTableModel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.model.RightClickMenu;

/**
 * The mouse listener for the right click menu.
 * 
 * @author Tianyu Li
 *
 */
public class RightClickMenuListener extends MouseAdapter {
	private RightClickMenu menu;
	private Requirement req;
	private RequirementListPanel panel;
	
	public RightClickMenuListener(RequirementListPanel panel) {
		this.panel = panel;
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		Point p = e.getPoint();
		int row = panel.getTable().rowAtPoint(p);
		this.req = panel.getModel().getRequirements().get(row);
		this.menu = new RightClickMenu(req);
		showMenu(e);
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		showMenu(e);
	}
	
	private void showMenu(MouseEvent e) {
		if (e.isPopupTrigger()) {
            menu.show(e.getComponent(), e.getX(), e.getY());
        }
	}
}
