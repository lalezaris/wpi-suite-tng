package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.controller;

import java.awt.Point;

import javax.swing.JTable;
import javax.swing.table.TableColumnModel;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.model.RequirementTableModel;

public class RequirementTableSortController {
	JTable table;
	public RequirementTableSortController(JTable table){
		this.table = table;
	}

	public void sortTable(Point p){
		TableColumnModel cm = table.getTableHeader().getColumnModel();
		((RequirementTableModel)table.getModel()).sortTable(table.columnAtPoint(p), cm);
		table.repaint();
		table.getTableHeader().repaint();//because for some reason the line before it doesn't do this...
	}
}