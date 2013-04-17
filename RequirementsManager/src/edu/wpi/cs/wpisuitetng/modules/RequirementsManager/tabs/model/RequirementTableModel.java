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
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.models.enums.RequirementStatus;
import edu.wpi.cs.wpisuitetng.modules.RequirementsManager.rmpermissions.observers.CurrentUserPermissions;

/**
 * Model for the Requirement Table.
 *
 * @author Chris Hanna
 * @modified by Tianyu Li on Apr 9
 * @modified by Michael Perrone on April 16
 * @version Apr 9th, 2013
 */
@SuppressWarnings("serial")

public class RequirementTableModel extends AbstractTableModel {
	protected String[] columnNames = { "ID", "Name", "Description", "Status", "Priority", "Estimate","Iteration", "Assigned", "Parent"};
	protected List<Object[]> data = new ArrayList<Object[]>();
	protected List<Requirement> requirements = new ArrayList<Requirement>();

	private static final boolean DEBUG = false;


	public static final String ASCENDING_SUFFIX = "\u2193";//the character displayed at the end of a header
	//if the column has been sorted in ascending order (down arrow)

	public static final String DESCENDING_SUFFIX = "\u2191";//the character displayed at the end of a header
	//if the column has been sorted in descending order(up arrow)


	/* Gets column count
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	/* Gets row count
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	/* (non-Javadoc)
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
				column.setPreferredWidth(30); // ID
			} else if (i == 1) {
				column.setPreferredWidth(100); //NAME COLUMN
			} else if (i == 2) {
				column.setPreferredWidth(550); //DESC COLUMN
			} else if (i == 3) {
				column.setPreferredWidth(90); //DESC STATUS
			} else if (i == 4) {
				column.setPreferredWidth(90); //DESC PRIORITY
			} else if (i == 5) {
				column.setPreferredWidth(30); //DESC ESTIMATE
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

	/* Gets column name
	 * @param col column to get the name of 
	 * @see javax.swing.table.AbstractTableModel#getColumnName(int)
	 */
	/* (non-Javadoc)
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

	/* Gets the value at a row and column
	 * @param row row to get value at
	 * @param col column to get value at
	 * @return Object value of the object that the method is getting
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	/* (non-Javadoc)
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
		Object[] r = {
				req.getId() ,
				req.getTitle() ,
				req.getDescription() ,
				req.getStatus(),
				req.getPriority(),
				req.getEstimateEffort() ,
				req.getIteration(),
				req.getAssignee(),
				req.getParentRequirementId() == -1 ? "None" : req.getParentRequirementId()};
		addRow(r);
		requirements.add(req);
	}

//	public void setVisibleRequirements(Requirement[] in){
//		data.clear();
//		requirements.clear();
//		for (int i = 0 ; i < in.length; i ++)
//			this.addRow(in[i]);
//		super.get
//	}
	
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
		if (pLevel == RMPermissionsLevel.ADMIN) {
			if (col == 5) {
				if (data.get(row)[3].equals(RequirementStatus.INPROGRESS)
						|| data.get(row)[3].equals(RequirementStatus.COMPLETE)) {
					JFrame debugger = new JFrame("Input value error");
					JOptionPane.showMessageDialog(debugger, "Can not edit it since it is in progress or completed");
					return false;
				} else
					return true;
			} else
				return false;
		} else
			return false;
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.AbstractTableModel#setValueAt(java.lang.Object, int, int)
	 */
	public void setValueAt(Object value, int row, int col) {
		if (DEBUG) {
			System.out.println("Setting value at " + row + "," + col
					+ " to " + value
					+ " (an instance of "
					+ value.getClass() + ")");
		}
		JFrame debugger = new JFrame("Input value error");

		try {
			Integer.parseInt((String)value);
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(debugger, "The value of estimate is not valid.");
			return;
		}

		if (Integer.parseInt((String)value) < 0) {
			JOptionPane.showMessageDialog(debugger, "The value of estimate is not valid.");
			return;
		}
		
		requirements.get(row).setEstimateEffort(Integer.parseInt((String)value));

		Object[] element = data.get(row);
		element[5] = value;
		data.set(row, element);
		fireTableCellUpdated(row, col);  

		if (DEBUG) {
			System.out.println("New value of data:");
			printDebugData();
		}
	}

	/**
	 * Prints the debug data.
	 */
	private void printDebugData() {
		int numRows = getRowCount();
		int numCols = getColumnCount();

		for (int i=0; i < numRows; i++) {
			System.out.print("    row " + i + ":");
			for (int j=0; j < numCols; j++) {
				System.out.print("  " + getValueAt(i,j));
			}
			System.out.println();
		}
		System.out.println("--------------------------");
	}

		

	/**
	 * Clear requirements.
	 */
	public void clearRequirements() {
		requirements.clear();
		//allRequirements.clear();
	}

	/**
	 * Gets the requirements.
	 *
	 * @return the requirements
	 */
	public List<Requirement> getRequirements() {
		return requirements;
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
			if(dataArray[j][8].equals("None")){//pretty hacky to make sure it is sorted properly
				dataArray[j][8] = -1;
			}
		}
		
		Arrays.sort(dataArray , comparator);
		data = new ArrayList<Object[]>(Arrays.asList(dataArray));
		
		for(int j=0; j < dataArray.length; j++){//8 is the Parent column
			if(dataArray[j][8].equals(-1)){//see before the sort for why this happens
				dataArray[j][8] = "None";
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