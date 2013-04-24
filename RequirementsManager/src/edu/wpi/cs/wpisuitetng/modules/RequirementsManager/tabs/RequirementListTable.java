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
 *  Tianyu Li
 **************************************************/

package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs;

import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RequirementPriority;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RequirementStatus;

/**
 * The JTable for requirement list, used for override
 * 
 * @author Tianyu Li
 *
 */
public class RequirementListTable extends JTable {
	RequirementListPanel panel;
	
	/**
	 * Default constructor
	 */
	public RequirementListTable(RequirementListPanel panel) {
		this.panel = panel;
	}

	/**
	 * 
	 */
	public RequirementListTable() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param dm
	 */
	public RequirementListTable(TableModel dm) {
		super(dm);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param dm
	 * @param cm
	 */
	public RequirementListTable(TableModel dm, TableColumnModel cm) {
		super(dm, cm);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param numRows
	 * @param numColumns
	 */
	public RequirementListTable(int numRows, int numColumns) {
		super(numRows, numColumns);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param rowData
	 * @param columnNames
	 */
	public RequirementListTable(Vector rowData, Vector columnNames) {
		super(rowData, columnNames);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param rowData
	 * @param columnNames
	 */
	public RequirementListTable(Object[][] rowData, Object[] columnNames) {
		super(rowData, columnNames);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param dm
	 * @param cm
	 * @param sm
	 */
	public RequirementListTable(TableModel dm, TableColumnModel cm,
			ListSelectionModel sm) {
		super(dm, cm, sm);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public TableCellEditor getCellEditor(int row, int col) {
		RequirementStatus status = panel.getModel().getRequirements().get(row).getStatus();
		if (col == 3) //STATUS 
		{
			JComboBox<RequirementStatus> comboBox = new JComboBox<RequirementStatus>();
			if (status == RequirementStatus.NEW) {
				comboBox.addItem(RequirementStatus.NEW);
				comboBox.addItem(RequirementStatus.INPROGRESS);
				comboBox.addItem(RequirementStatus.DELETED);
			}
			if (status == RequirementStatus.OPEN) {
				comboBox.addItem(RequirementStatus.OPEN);
				comboBox.addItem(RequirementStatus.INPROGRESS);
				comboBox.addItem(RequirementStatus.DELETED);
			}
			if (status == RequirementStatus.INPROGRESS) {
				comboBox.addItem(RequirementStatus.OPEN);
				comboBox.addItem(RequirementStatus.INPROGRESS);
				comboBox.addItem(RequirementStatus.COMPLETE);
				comboBox.addItem(RequirementStatus.DELETED);
			}
			if (status == RequirementStatus.COMPLETE) {
				comboBox.addItem(RequirementStatus.OPEN);
				comboBox.addItem(RequirementStatus.COMPLETE);
				comboBox.addItem(RequirementStatus.DELETED);
			}
			if (status == RequirementStatus.DELETED) {
				comboBox.addItem(RequirementStatus.OPEN);
				comboBox.addItem(RequirementStatus.DELETED);
			}
			return new DefaultCellEditor(comboBox);
		} else if (col == 4) //PRIORITY
		{
			JComboBox<RequirementPriority> comboBox = new JComboBox<RequirementPriority>();
			//if (status == RequirementStatus.DELETED) {
				comboBox.addItem(RequirementPriority.LOW);
				comboBox.addItem(RequirementPriority.MEDIUM);
				comboBox.addItem(RequirementPriority.HIGH);
				comboBox.addItem(RequirementPriority.BLANK);
			//}
			return new DefaultCellEditor(comboBox);
		}
		return new DefaultCellEditor(new JTextField());
	}

}
