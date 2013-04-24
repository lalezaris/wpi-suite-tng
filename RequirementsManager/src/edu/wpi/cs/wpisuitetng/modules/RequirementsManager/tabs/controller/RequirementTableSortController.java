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
 * Michael Perrone
 **************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.controller;

import java.awt.Point;

import javax.swing.JTable;
import javax.swing.table.TableColumnModel;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.model.RequirementTableModel;

/**
 * The Class RequirementTableSortController.
 * Controller for sorting the requirement table.
 * @author Michael Perrone
 */
public class RequirementTableSortController {
	JTable table;

	/**
	 * Instantiates a new requirement table sort controller.
	 *
	 * @param table the table
	 */
	public RequirementTableSortController(JTable table){
		this.table = table;
	}

	/**
	 * Sort table.
	 *
	 * @param p the p
	 */
	public void sortTable(Point p){
		TableColumnModel cm = table.getTableHeader().getColumnModel();
		((RequirementTableModel)table.getModel()).sortTable(table.columnAtPoint(p), cm);
		table.repaint();
		table.getTableHeader().repaint();//because for some reason the line before it doesn't do this...
	}
}