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

import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Iteration;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.IterationStatus;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RequirementPriority;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RequirementStatus;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.requirements.action.Refresher;

/**
 * The JTable for requirement list, used for override
 * 
 * @author Tianyu Li
 *
 */
public class RequirementListTable extends JTable {
	RequirementListPanel panel;
	
	/**
	 * Default constructor.
	 *
	 * @param panel the panel
	 */
	public RequirementListTable(RequirementListPanel panel) {
		super();
		this.panel = panel;
	}

	/**
	 * 
	 */
	public RequirementListTable() {
		super();
	}

	/**
	 * @param dm
	 */
	public RequirementListTable(TableModel dm) {
		super(dm);
		
	}

	/**
	 * @param dm
	 * @param cm
	 */
	public RequirementListTable(TableModel dm, TableColumnModel cm) {
		super(dm, cm);
		
	}

	/**
	 * @param numRows
	 * @param numColumns
	 */
	public RequirementListTable(int numRows, int numColumns) {
		super(numRows, numColumns);
		
	}

	/**
	 * @param rowData
	 * @param columnNames
	 */
	public RequirementListTable(Vector rowData, Vector columnNames) {
		super(rowData, columnNames);
		
	}

	/**
	 * @param rowData
	 * @param columnNames
	 */
	public RequirementListTable(Object[][] rowData, Object[] columnNames) {
		super(rowData, columnNames);
		
	}

	/**
	 * @param dm
	 * @param cm
	 * @param sm
	 */
	public RequirementListTable(TableModel dm, TableColumnModel cm,
			ListSelectionModel sm) {
		super(dm, cm, sm);
		
	}
	
	@Override
	public TableCellEditor getCellEditor(int row, int col) {

		//RequirementStatus status = panel.getModel().getRequirements().get(row).getStatus();
		Iteration[] iterations = Refresher.getInstance().getInstantIterations();
		Object parent = panel.getModel().getUnsavedRequirements().get(row)[8];
		if(parent instanceof String){
			iterations = checkIterations(iterations,(Iteration) this.getValueAt(row, 6), -1);
		} else
			iterations = checkIterations(iterations,(Iteration) this.getValueAt(row, 6), (Integer) parent);

		
		Requirement r = null;
		RequirementStatus s = (RequirementStatus) panel.getModel().getUnsavedRequirements().get(row)[3];
		RequirementStatus status = s;//panel.getModel().getUnsavedRequirements().get(row).getStatus();
		System.out.println("Status is " + status); //TODO

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
		} else if (col == 6) {
			JComboBox<Iteration> comboBox = new JComboBox<Iteration>();
			for (int i = 0 ; i < iterations.length; i++) {
				if (iterations[i].isInList()) {
					comboBox.addItem(iterations[i]);
				}
			}
			return new DefaultCellEditor(comboBox);
		}
		return new DefaultCellEditor(new JTextField());
	}

	/**
	 * Check if the iterations are closed and remove the closed ones.
	 * 
	 * @param iterations the iterations before check
	 * @return iterations after check
	 */
	private Iteration[] checkIterations(Iteration[] iterations, Iteration reqIteration, Integer parentId) {
//		Date currentDate = new Date();
//		for (int i = 0; i < iterations.length; i++) {
//			if (iterations[i].getEndDate().after(currentDate)) {
//				iterations[i].setInList(true);
//			}
//		}
//		return iterations;
		Calendar cStart = Calendar.getInstance();
		Calendar cEnd = Calendar.getInstance();
		int parentRequirement = -1; 
		int id = parentId;
		if(!(id < 0)){
			for(int j = 0; j < this.getRowCount(); j++){
				if(id == (Integer) this.getValueAt(j, 0)){
					parentRequirement = j;
					break;
				}
					
			}
		}
		for (int i = 0; i < iterations.length ;i++){
			cStart = Calendar.getInstance();
			cStart.setTime(iterations[i].getStartDate());
			cEnd = Calendar.getInstance();
			cEnd.setTime(iterations[i].getEndDate());
			cEnd.add(Calendar.DATE, 1);
			if (!(parentRequirement < 0)) {
				if (((Iteration) panel.getModel().getUnsavedRequirements().get(parentRequirement)[6]).getId() == Iteration.getBacklog().getId()) {
					if (cEnd.compareTo(Calendar.getInstance()) >= 0 || iterations[i] == Iteration.getBacklog() || iterations[i].getId() == reqIteration.getId()){
						iterations[i].setInList(true);
					}
				} else {
					Calendar cTwoEnd = Calendar.getInstance();
					cTwoEnd.setTime(((Iteration) panel.getModel().getUnsavedRequirements().get(parentRequirement)[6]).getEndDate());
					cTwoEnd.add(Calendar.DATE, 1);
					if ((cEnd.compareTo(cTwoEnd) <= 0 &&
							cEnd.compareTo(Calendar.getInstance()) >= 0) || iterations[i] == Iteration.getBacklog()) {
						iterations[i].setInList(true);
					}
				} 
			} else {
				if (cEnd.compareTo(Calendar.getInstance()) >= 0 || iterations[i] == Iteration.getBacklog() || iterations[i].getId() == reqIteration.getId()){
					iterations[i].setInList(true);
				}
			}
		}
		return iterations;
	}

}
