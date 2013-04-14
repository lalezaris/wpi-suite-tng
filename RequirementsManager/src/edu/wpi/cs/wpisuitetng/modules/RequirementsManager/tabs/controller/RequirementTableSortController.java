package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.controller;

import java.awt.Point;

import javax.swing.JTable;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.model.RequirementTableModel;

public class RequirementTableSortController {
	JTable table;
	public RequirementTableSortController(JTable table){
		this.table = table;
	}
	
	public void sortTable(Point p){
		((RequirementTableModel)table.getModel()).sortTable(table.columnAtPoint(p));
		table.repaint();
		table.updateUI();
	}
}