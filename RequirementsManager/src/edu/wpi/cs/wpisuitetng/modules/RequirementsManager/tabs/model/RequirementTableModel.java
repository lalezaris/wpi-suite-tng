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
 *  Chris Hanna
 *  Tushar Narayan
 *  Tianyu Li
 *  Michael Perrone
 **************************************************/
package edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RMPermissionsLevel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RequirementPriority;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RequirementStatus;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.observers.CurrentUserPermissions;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.RequirementListPanel;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.tabs.RequirementListView;

/**
 * Model for the Requirement Table.
 *
 * @author Chris Hanna
 * 
 * @version Apr 9th, 2013
 */
@SuppressWarnings("serial")

public class RequirementTableModel extends AbstractTableModel {
	protected String[] columnNames = { "ID", "Name", "Description", "Status", "Priority", "Estimate","Iteration", "Assigned", "Parent ID"};
	protected List<Object[]> data = new ArrayList<Object[]>();
	protected List<Requirement> requirements = new ArrayList<Requirement>();
	protected boolean isChange; 
	protected RequirementListPanel panel;

	private static final boolean DEBUG = false;


	public static final String ASCENDING_SUFFIX = "\u2193";//the character displayed at the end of a header
	//if the column has been sorted in ascending order (down arrow)

	public static final String DESCENDING_SUFFIX = "\u2191";//the character displayed at the end of a header
	//if the column has been sorted in descending order(up arrow)

	public RequirementTableModel(RequirementListPanel panel) {
		this.panel = panel;
		this.isChange = false;
	}
	/**
	 * Gets column count
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	/**
	 * Gets row count
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	@Override
	public int getRowCount() {
		return data.size();
	}

	/**
	 * Sets column widths.
	 * 
	 * @param table table to set the column widths
	 */
	public void setColumnWidths(JTable table){
		table.getTableHeader().setReorderingAllowed(false);
		for (int i = 0 ; i < 8 ; i ++){
			TableColumn column = table.getColumnModel().getColumn(i);

			if (i == 0) {
				column.setPreferredWidth(30); //ID
			} else if (i == 1) {
				column.setPreferredWidth(100); //NAME
			} else if (i == 2) {
				column.setPreferredWidth(550); //DESCRIPTION
			} else if (i == 3) {
				column.setPreferredWidth(90); //STATUS
			} else if (i == 4) {
				column.setPreferredWidth(90); //PRIORITY
			} else if (i == 5) {
				column.setPreferredWidth(30); //ESTIMATE
			} else if (i == 6) {
				column.setPreferredWidth(70); //ITERATION
			} else if (i == 7) {
				column.setPreferredWidth(100); //ASSIGNEE
			}
			else if (i == 8) {
				column.setPreferredWidth(30); //PARENT ID
			}
		}
	}

	/**
	 * Gets column name
	 * 
	 * @param col column to get the name of
	 * 
	 * 
	 * @see javax.swing.table.AbstractTableModel#getColumnName(int)
	 */
	public String getColumnName(int col) {
		return columnNames[col];
	}

	/**
	 * Adds row.
	 * 
	 * @param rowContent content to add to the row
	 */
	public void addRow(Object[] rowContent) {
		data.add(rowContent);
	}

	/**
	 * Gets the value at a row and column
	 * @param row row to get value at
	 * @param col column to get value at
	 * @return Object value of the object that the method is getting
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	@Override
	public Object getValueAt(int row, int col) {

		if (col < getColumnCount() && row < getRowCount() && col > -1
				&& row > -1) {
			return data.get(row)[col];
		} else
			return null;
	}

	/**
	 * Adds a row to a requirement.
	 * 
	 * @param req Requirement to add a row to
	 */
	public void addRow(Requirement req){
		String ass = req.getAssignee().toString();
		ass = ass.substring(1,ass.length()-1);
		Object[] r = {
				req.getId() ,
				req.getTitle() ,
				req.getDescription() ,
				req.getStatus(),
				req.getPriority(),
				req.getEstimateEffort() ,
				req.getIteration(),
				ass,
				req.getParentRequirementId() == -1 ? "" : req.getParentRequirementId()
		};
		addRow(r);
		requirements.add(req);
	}

	/**
	 * Removes row.
	 * 
	 * @param row row to remove
	 */
	public void removeRow(int row){
		data.remove(row);
	}

	/**
	 * Clears data.
	 * 
	 */
	public void clear(){
		data.clear();
	}

	/**
	 * Gets row ID.
	 * 
	 * @param row row to get ID of
	 * @return id of row
	 */
	public int getRowID(int row){
		return Integer.parseInt( getValueAt(row, 0).toString() );
	}

	/**
	 * Make the estimate editable.
	 *
	 * @param row the row
	 * @param col the col
	 * @return true, if is cell editable
	 * @see javax.swing.table.AbstractTableModel#isCellEditable(int, int)
	 */
	public boolean isCellEditable(int row, int col) {
		RMPermissionsLevel pLevel = CurrentUserPermissions
				.getCurrentUserPermission();

		if (pLevel != RMPermissionsLevel.ADMIN) {
			return false;
		}
		if (col == 0){ //ID should not be editable
			return false;
		}
		if (col > 5) { //Iteration, Assigned, and Parent should not be editable
			return false;
		}
		if (requirements.get(row).getStatus().equals(RequirementStatus.COMPLETE) ||
				requirements.get(row).getStatus().equals(RequirementStatus.INPROGRESS)) {
			if (col == 5) {
				return false;
			}
		}
		if (requirements.get(row).getStatus().equals(RequirementStatus.DELETED)) {
			if (col != 3) {
				return false;
			}
		}
		return true;
	}

	/**
	 * @see javax.swing.table.AbstractTableModel#setValueAt(java.lang.Object, int, int)
	 */
	public void setValueAt(Object value, int row, int col) {

		Requirement temp = requirements.get(row);
		String title = this.getColumnName(col);

		if (!checkInput(value, temp, title)) {
			return;
		}
		
		if (title.equals("Parent ID")) {
			requirements.get(row).setId(Integer.parseInt((String)value));
		}
		if (title.equals("Name")) {
			requirements.get(row).setTitle((String)value);
		}
		if (title.equals("Description")) {
			requirements.get(row).setDescription((String)value);
		}
		if (title.equals("Status")) {
			requirements.get(row).setStatus((RequirementStatus)value);
		}
		if (title.equals("Priority")) {
			requirements.get(row).setPriority((RequirementPriority)value);
		}
		if (title.equals("Estimate")) {
			requirements.get(row).setEstimateEffort(Integer.parseInt((String)value));
		}		

		Object[] element = data.get(row);
		element[col] = value;
		data.set(row, element);
		fireTableCellUpdated(row, col); 
		isChange = true;
		panel.hideUpdateSuccessfully();

		if (DEBUG) {
			printDebugData();
		}
	}

	/**
	 * Method to check if the input value is legal
	 * 
	 * @param value the input value
	 * @param req the edited requirement
	 * @param title the edited part of requirement
	 * @return true if the input is legal, false otherwise
	 */
	private boolean checkInput(Object value, Requirement req, String title) {
		JFrame frame = new JFrame();
		int IDNumber;
		int estimate;
		
		if (title.equals("Parent ID")) {
			try {
				IDNumber = Integer.parseInt((String)value);
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(frame, "The ID must be integer.");
				return false;
			}
			if (IDNumber < 0) {
				JOptionPane.showMessageDialog(frame, "The ID must be non-negative.");
				return false;
			}
		}

		if (title.equals("Estimate")) {
			try {
				estimate = Integer.parseInt((String)value);
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(frame, "The estimate must be integer.");
				return false;
			}
			if (estimate < 0) {
				JOptionPane.showMessageDialog(frame, "The estimate must be non-negative.");
				return false;
			}
		}

		return true;
	}

	/**
	 * Prints the debug data.
	 */
	private void printDebugData() {
		int numRows = getRowCount();
		int numCols = getColumnCount();


	}

	/**
	 * Clear requirements.
	 */
	public void clearRequirements() {
		requirements.clear();
	}

	/**
	 * Gets the requirements.
	 *
	 * @return the requirements
	 */
	public List<Requirement> getRequirements() {
		return requirements;
	}

	public boolean getIsChange() {
		return isChange;
	}

	public void setIsChange(boolean value) {
		isChange = value;
	}

	/** Sorts the table in ascending order bases on the column given. If it was already 
	 * sorted in ascending order, it will be sorted in descending order (and vice versa).
	 * It will also append a suffix to the header of the column being sorted indicating
	 * the order of the sort.
	 * 
	 *  @param col the column by which the table is sorted.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void sortTable(final int col, TableColumnModel cm){
		if(!(this.getValueAt(1,col) instanceof Comparable)){//can't sort if this column has no notion of an order
			return;
		}

		String currentHeader = cm.getColumn(col).getHeaderValue().toString();
		final boolean wasJustAscending = currentHeader.equals(columnNames[col]+ASCENDING_SUFFIX);//true if the header is the original
		//false if it has something appended to it

		Comparator<Object[]> comparator = new Comparator<Object[]>(){

			@Override
			public int compare(Object[] a, Object[] b){
				return ((Comparable<Comparable>) a[col]).compareTo((Comparable<Comparable>)b[col])*(wasJustAscending ? -1 : 1);
			}
			@Override
			public boolean equals(Object o){
				return false;
			}
		};
		Object[][] dataArray = new Object[data.size()][data.get(0).length];
		for(int i=0; i<data.size(); i++){
			dataArray[i] = data.get(i);
		}

		for(int j=0; j < dataArray.length; j++){//8 is the Parent column
			if(dataArray[j][8].equals("")){
				dataArray[j][8] = -1;
			}
		}

		Arrays.sort(dataArray , comparator);
		data = new ArrayList<Object[]>(Arrays.asList(dataArray));

		for(int j=0; j < dataArray.length; j++){//8 is the Parent column
			if(dataArray[j][8].equals(-1)){
				dataArray[j][8] = "";
			}
		}

		//reset the headers
		for(int i=0; i<cm.getColumnCount(); i++){
			cm.getColumn(i).setHeaderValue(columnNames[i]);
		}

		//set new header for ascending or descending
		currentHeader = cm.getColumn(col).getHeaderValue().toString();
		cm.getColumn(col).setHeaderValue(wasJustAscending ? currentHeader+DESCENDING_SUFFIX : currentHeader+ASCENDING_SUFFIX );
	}
}